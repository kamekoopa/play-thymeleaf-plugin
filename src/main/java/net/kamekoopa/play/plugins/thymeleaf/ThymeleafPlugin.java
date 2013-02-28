package net.kamekoopa.play.plugins.thymeleaf;

import java.util.Map;

import nz.net.ultraq.web.thymeleaf.LayoutDialect;
import play.Application;
import play.Configuration;
import play.Logger;
import play.Play;
import play.Plugin;
import play.api.templates.Html;

public class ThymeleafPlugin extends Plugin {

	private final Configuration config;

	private PlayTemplateEngine engine;

	public ThymeleafPlugin(Application app) {
		Logger.info("Tymeleaf Plugin is constructing");
		this.config = app.configuration().getConfig("thymeleaf");
	}

	@Override
	public boolean enabled() {
		if(this.config == null){

			Logger.error("couldn't get configure of thymeleaf. \"thymeleaf\" key must be defined in application.conf.");
			return false;

		}else if(this.config.getBoolean("enable", Boolean.FALSE).booleanValue() == false){

			Logger.warn("thymeleaf plugin is disabled. \"thymeleaf.enable\" key is undefined, or configured to false.");
			return false;

		}else{

			String viewLookupPath = this.config.getString("views")     == null ? "app/views/" : this.config.getString("views");
			String suffix         = this.config.getString("suffix")    == null ? ".html"      : this.config.getString("suffix");
			String mode           = this.config.getString("mode")      == null ? "HTML5"      : this.config.getString("mode");
			Long   ttl            = this.config.getMilliseconds("ttl") == null ? 1*60*1000    : this.config.getMilliseconds("ttl");

			Logger.info(String.format("thymeleaf.views  : %s", viewLookupPath));
			Logger.info(String.format("thymeleaf.suffix : %s", suffix));
			Logger.info(String.format("thymeleaf.mode   : %s", mode));
			Logger.info(String.format("thymeleaf.ttl    : %d", ttl));

			PlayFileTemplateResolver resolver = new PlayFileTemplateResolver(viewLookupPath);
			resolver.setTemplateMode(mode);
			resolver.setSuffix(suffix);
			resolver.setCacheTTLMs(ttl);

			PlayTemplateEngine engine = new PlayTemplateEngine();
			engine.setTemplateResolver(resolver);
			engine.addDialect(new LayoutDialect());

			this.engine = engine;

			return true;
		}
	}

	@Override
	public void onStart() {
		Logger.info("Tymeleaf Plugin Initialized");
	}

	@Override
	public void onStop() {
		Logger.info("Tymeleaf Plugin was shutdown");
	}

	public static ThymeleafResult generateResult(final String templatePath, final Map<String, Object> variables){
		return new ThymeleafResult(generateHtml(templatePath, variables));
	}

	static Html generateHtml(final String templatePath, final Map<String, Object> variables){
		return Play.application().plugin(ThymeleafPlugin.class).engine.process(templatePath, variables);
	}
}
