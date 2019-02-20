package it.innove.play.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import nu.validator.htmlparser.dom.HtmlDocumentBuilder;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;

import play.Environment;
import play.Logger;
import play.twirl.api.Html;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PdfGenerator {
	private static final Logger.ALogger LOG = Logger.of(PdfGenerator.class);

	private List<String> defaultFonts = null;

	final Environment environment;

	@Inject
	public PdfGenerator(final Environment environment) {
		this.environment = environment;
	}

	public void loadTemporaryFonts(List<String> fontsToLoad) {
		defaultFonts = new ArrayList<>();
		addTemporaryFonts(fontsToLoad);
	}

	public void addTemporaryFonts(List<String> fontsToLoad) {
		if (defaultFonts == null)
			defaultFonts = new ArrayList<>();
		for (String font : fontsToLoad) {
			try {
				InputStream fin = environment.resourceAsStream(font);
				final File tempFile = File.createTempFile("tmp_" + FilenameUtils.getBaseName(font), "." + FilenameUtils.getExtension(font));
				tempFile.deleteOnExit();
				FileOutputStream out = new FileOutputStream(tempFile);
				IOUtils.copy(fin, out);
				defaultFonts.add(tempFile.getAbsolutePath());
			} catch (Exception e) {
				LOG.error("Loading fonts", e);
			}
		}
	}

	public void loadLocalFonts(List<String> fontsToLoad) {
		defaultFonts = new ArrayList<>();
		addLocalFonts(fontsToLoad);
	}

	public void addLocalFonts(List<String> fontsToLoad) {
		if (defaultFonts == null)
			defaultFonts = new ArrayList<>();
		for (String font : fontsToLoad)
			defaultFonts.add(font);
	}

	public Result ok(Html html, String documentBaseURL) {
		byte[] pdf = toBytes(html.body(), documentBaseURL);
		return Results.ok(pdf).as("application/pdf");
	}

	public Result ok(Html html, String documentBaseURL, List<String> fonts) {
		byte[] pdf = toBytes(html.body(), documentBaseURL, fonts);
		return Results.ok(pdf).as("application/pdf");
	}

	public byte[] toBytes(Html html, String documentBaseURL) {
		byte[] pdf = toBytes(html.body(), documentBaseURL);
		return pdf;
	}

	public byte[] toBytes(Html html, String documentBaseURL, List<String> fonts) {
		byte[] pdf = toBytes(html.body(), documentBaseURL, fonts);
		return pdf;
	}

	public byte[] toBytes(String string, String documentBaseURL) {
		return toBytes(string, documentBaseURL, defaultFonts);
	}

	public byte[] toBytes(String string, String documentBaseURL, List<String> fonts) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		toStream(string, os, documentBaseURL, fonts);
		return os.toByteArray();
	}

	public void toStream(String string, OutputStream os, String documentBaseURL) {
		toStream(string, os, documentBaseURL, defaultFonts);
	}

	public void toStream(String string, OutputStream os, String documentBaseURL, List<String> fonts) {
		try {
			InputStream input = new ByteArrayInputStream(string.getBytes("UTF-8"));
			ITextRenderer renderer = new ITextRenderer();
			if (fonts != null)
				for (String font : fonts) {
					renderer.getFontResolver().addFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				}
			PdfUserAgent myUserAgent = new PdfUserAgent(renderer.getOutputDevice(), environment);
			myUserAgent.setSharedContext(renderer.getSharedContext());
			renderer.getSharedContext().setUserAgentCallback(myUserAgent);
			Document document = new HtmlDocumentBuilder().parse(input);
			renderer.setDocument(document, documentBaseURL);
			renderer.layout();
			renderer.createPDF(os);
		} catch (Exception e) {
			LOG.error("Error creating document from template", e);
		}
	}
}
