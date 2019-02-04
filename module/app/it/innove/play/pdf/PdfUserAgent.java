package it.innove.play.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.itextpdf.text.Image;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import org.xhtmlrenderer.resource.CSSResource;
import org.xhtmlrenderer.resource.ImageResource;
import org.xhtmlrenderer.resource.XMLResource;

import play.Logger;
import play.Environment;
import scala.Option;

public class PdfUserAgent extends ITextUserAgent {
	private static final Logger.ALogger LOG = Logger.of(PdfUserAgent.class);

	Environment environment;

	public PdfUserAgent(ITextOutputDevice outputDevice, Environment environment) {
		super(outputDevice);

		this.environment = environment;
	}

	@Override
	public ImageResource getImageResource(String uri) {
		Option<InputStream> option = environment.asScala().resourceAsStream(uri);
		if (option.isDefined()) {
			InputStream stream = option.get();
			try {
				Image image = Image.getInstance(getData(stream));
				scaleToOutputResolution(image);
				return new ImageResource(uri, new ITextFSImage(image));
			} catch (Exception e) {
				LOG.error("fetching image " + uri, e);
				throw new RuntimeException(e);
			}
		} else {
			return super.getImageResource(uri);
		}
	}

	@Override
	public CSSResource getCSSResource(String uri) {
		Option<InputStream> option = environment.asScala().resourceAsStream(uri);
		if (option.isDefined())
			return new CSSResource(option.get());
		try {
			// uri is in fact a complete URL
			new URL(uri).getPath();
			return super.getCSSResource(uri);
		} catch (MalformedURLException e) {
			LOG.error("fetching css " + uri, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] getBinaryResource(String uri) {
		Option<InputStream> option = environment.asScala().resourceAsStream(uri);
		if (option.isDefined()) {
			InputStream stream = option.get();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				copy(stream, baos);
			} catch (IOException e) {
				LOG.error("fetching binary " + uri, e);
				throw new RuntimeException(e);
			}
			return baos.toByteArray();
		} else {
			return super.getBinaryResource(uri);
		}
	}

	@Override
	public XMLResource getXMLResource(String uri) {
		Option<InputStream> option = environment.asScala().resourceAsStream(uri);
		if (option.isDefined()) {
			return XMLResource.load(option.get());
		} else {
			return super.getXMLResource(uri);
		}
	}

	private void scaleToOutputResolution(Image image) {
		float factor = getSharedContext().getDotsPerPixel();
		image.scaleAbsolute(image.getPlainWidth() * factor, image.getPlainHeight() * factor);
	}

	private byte[] getData(InputStream stream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copy(stream, baos);
		return baos.toByteArray();
	}

	private void copy(InputStream stream, OutputStream os) throws IOException {
		byte[] buffer = new byte[1024];
		while (true) {
			int len = stream.read(buffer);
			os.write(buffer, 0, len);
			if (len < buffer.length)
				break;
		}
	}
}
