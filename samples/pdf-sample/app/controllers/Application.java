package controllers;

import it.innove.play.pdf.PdfGenerator;

import javax.inject.Inject;

import play.*;
import play.mvc.*;
import views.html.*;

import java.util.Arrays;

public class Application extends Controller {
	
	@Inject
	public PdfGenerator pdfGenerator;

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result pdf() {
		pdfGenerator.loadTemporaryFonts(Arrays.asList(new String[]{"fonts/OpenSans-Regular.ttf", "fonts/OpenSans-Bold.ttf"}));
		return pdfGenerator.ok(utf.render("Hello world"), Configuration.root().getString("application.host"));
	}

	public Result utf() {
		return ok(utf.render("Hello world"));
	}
}
