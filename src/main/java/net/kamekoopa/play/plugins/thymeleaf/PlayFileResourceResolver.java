package net.kamekoopa.play.plugins.thymeleaf;

import java.io.InputStream;

import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.FileResourceResolver;
import org.thymeleaf.resourceresolver.IResourceResolver;

class PlayFileResourceResolver implements IResourceResolver {

	private final FileResourceResolver fileResourceResolver;

	private final String viewLookupPath;

	public PlayFileResourceResolver(final String viewLookupPath){
		super();
		this.fileResourceResolver = new FileResourceResolver();
		this.viewLookupPath = viewLookupPath;
	}

	@Override
	public String getName() {
		return "PLAY_FILE";
	}

	@Override
	public InputStream getResourceAsStream (
		TemplateProcessingParameters templateProcessingParameters,
		String resourceName
	) {

		StringBuilder sb = new StringBuilder(this.viewLookupPath).append(resourceName);
		return this.fileResourceResolver.getResourceAsStream(templateProcessingParameters, sb.toString());
	}
}