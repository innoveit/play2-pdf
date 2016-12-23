package controllers;

import it.innove.play.pdf.PdfGenerator;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import org.asynchttpclient.util.Base64;
import play.*;
import play.mvc.*;
import views.html.*;

import java.util.Arrays;
import okhttp3.Request;
import okhttp3.Response;

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

	public Result pdfbase64() {
		Request request = new Request.Builder()
				.url(routes.Assets.at("pdf.css").absoluteURL(request()))
				.build();
		Request requestImage = new Request.Builder()
				.url("https://www.google.pt/images/srpr/logo11w.png")
				.build();
		OkHttpClient client = new OkHttpClient();

		String css = "";
		String base64String = "";
		try {
			Response response = client.newCall(request).execute();
			Response responseImage = client.newCall(requestImage).execute();
			byte[] imageBytes = responseImage.body().bytes();
			base64String = Base64.encode(imageBytes);
			css = response.body().string();


		} catch (Exception ex) {
			Logger.debug("Error",ex);
		}
		pdfGenerator.loadTemporaryFonts(Arrays.asList(new String[]{"fonts/OpenSans-Regular.ttf", "fonts/OpenSans-Bold.ttf"}));
		String image = "data:image/png;base64, " + base64String;
		return pdfGenerator.ok(base64.render(css, image), Configuration.root().getString("application.host"));
	}

	public Result utf() {
		return ok(utf.render("Hello world"));
	}

}
