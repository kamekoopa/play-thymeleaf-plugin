package net.kamekoopa.play.plugins.thymeleaf;

import play.api.templates.Html;
import play.mvc.Result;
import play.mvc.Results;

class ThymeleafResult {

	public final Html html;

	ThymeleafResult(final Html html){
		this.html = html;
	}

	Result status(final int status){
		return Results.status(status, this.html);
	}

	Result ok(){
		return Results.ok(this.html);
	}

	Result created(){
		return Results.created(this.html);
	}

	Result badRequest(){
		return Results.badRequest(this.html);
	}

	Result unauthorized(){
		return Results.unauthorized(this.html);
	}

	Result forbidden(){
		return Results.forbidden(this.html);
	}

	Result notFound(){
		return Results.notFound(this.html);
	}

	Result conflict(){
		return this.status(409);
	}

	Result internalServerError(){
		return Results.internalServerError(this.html);
	}

	Result notImplemented(){
		return this.status(501);
	}

	Result serviceUnavailable(){
		return Results.status(503);
	}
}