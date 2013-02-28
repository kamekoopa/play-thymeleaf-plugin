package net.kamekoopa.play.plugins.thymeleaf;

import java.util.Map;

import org.thymeleaf.Arguments;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.messageresolver.AbstractMessageResolver;
import org.thymeleaf.messageresolver.MessageResolution;

import play.api.templates.Html;
import play.i18n.Messages;

public class PlayTemplateEngine extends TemplateEngine{

	public PlayTemplateEngine() {
		super();

		this.setMessageResolver(new AbstractMessageResolver() {

			@Override
			public MessageResolution resolveMessage(Arguments arguments, String key, Object[] messageParameters) {

				String resolved = Messages.get(key, messageParameters);
				return new MessageResolution(resolved);
			}
		});
	}

	public Html process(final String templatePath, final Map<String, Object> variables){
		Context ctx = new Context();
		ctx.setVariables(variables);

		return Html.apply(this.process(templatePath, ctx));
	}
}