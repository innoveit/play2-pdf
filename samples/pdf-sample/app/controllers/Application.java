package controllers;

import it.innove.play.pdf.PdfGenerator;

import javax.inject.Inject;

import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	@Inject
	public PdfGenerator pdfGenerator;

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result pdf() {
		return pdfGenerator.ok(utf.render("Hello world"), Configuration.root().getString("application.host"));
	}
}
