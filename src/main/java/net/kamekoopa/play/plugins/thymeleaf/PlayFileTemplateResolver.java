package net.kamekoopa.play.plugins.thymeleaf;

import org.thymeleaf.exceptions.ConfigurationException;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

public class PlayFileTemplateResolver extends TemplateResolver {

	public PlayFileTemplateResolver(final String viewLookupPath){
		super();
		super.setResourceResolver(new PlayFileResourceResolver(viewLookupPath));
	}

	@Override
    public void setResourceResolver(final IResourceResolver resourceResolver) {
		throw new ConfigurationException(
			"Cannot set a resource resolver on " + this.getClass().getName() + ". If " +
			"you want to set your own resource resolver, use " + TemplateResolver.class.getName() +
			"instead"
		);
	}
}