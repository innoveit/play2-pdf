Play 2.7.x PDF module
===================
![Sonatype maven](https://img.shields.io/nexus/r/https/oss.sonatype.org/it.innove/play2-pdf.svg?style=flat)


This module helps generating PDF documents dynamically from your Play! web application.
It simply renders your HTML- and CSS-based templates to PDF.
It is based on the Flying Saucer library, which in turn uses iText for PDF generation.
This project is a fork of https://github.com/benjohnde/play20-pdf .

Usage
-----

You can use a standard Play! scala template like this one:
``` html
@(message: String)

@main("Welcome to Play 2") {
    Image: <img src="/public/images/favicon.png"/><br/>
    Hello world! <br/>
    @message <br/>
}
```

Then this template, after having imported ```it.innove.PdfGenerator```, can simply be rendered as:
``` java

	@Inject
	public PdfGenerator pdfGenerator;

	public Result document() {
		return pdfGenerator.ok(document.render("Your new application is ready."), "http://localhost:9000");
	}
```
where ```pdfGenerator.ok``` is a simple shorthand notation for:
``` java
	ok(pdfGenerator.toBytes(document.render("Your new application is ready."), "http://localhost:9000")).as("application/pdf")
```

Template rules
--------------

Templates must generate XHTML.

If the template is using an image, stylesheet, etc., it usually is loaded via an http call.
The PDF modules tries to optimize that resource loading:
If you specify the URI as a path into the classpath of your Play! app, the resource is loaded directly instead.
See the above sample template for an example.

Of course you can link to CSS files in your class path also, but be aware not to
use the ``` media="screen"```qualifier.

Fonts you use must be explicitely packaged with your app.
```
<html>
	<head>
		<style type="text/css"><!--
		body {
			...
			font-family: FreeSans;
		}
		--></style>
	</head>
	<body>
		...
	</body>
</html>
```
Since the FreeSans font is not available to the java VM, you are required to
add the corresponding font file, "FreeSans.ttf" to your Play! app.
You can add your fonts with ```PdfGenerator.loadLocalFonts``` method, for example if you put the font in the folder  ```/conf/fonts``` you load the font with:

```pdfGenerator.loadLocalFonts(new String[]{"fonts/FreeSans.ttf"})```

Installation
------------

Currently, the module is hosted at Maven Central Repository.
Therefore, including the following lines in your ```build.sbt``` will resolve it:
```
libraryDependencies ++= Seq(
  ...
      "it.innove" % "play2-pdf" % "x.x.x"
)
```
where:

| Play version  | play2-pdf vesion |
| :-----------: | :--------------: |
| 2.7           | 1.9.x            |
| 2.6           | 1.8.x            |

After the next restart of Play!, the module is available.
If you are using an IDE like Eclipse, remember to re-generate your project files.

Configuration
------------

If you need additional configuration, then modify your `ApplicationModule` accordingly:
``` java
public class ApplicationModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(PdfGenerator.class).toProvider(PdfGeneratorProvider.class).asEagerSingleton();
  }
  
  static class PdfGeneratorProvider implements Provider<PdfGenerator> {
    private final PdfGenerator pdfGenerator;

    @Inject
    public PdfGeneratorProvider(final Environment environment) {
      this.pdfGenerator = new PdfGenerator(environment);
      this.pdfGenerator.loadLocalFonts(asList("fonts/opensans-regular.ttf"));
    }

    @Override
    public PdfGenerator get() {
      return pdfGenerator;
    }
  }
}
```

License
-------

Released under the MIT license; see the file LICENSE.

Releases
------------

https://github.com/innoveit/play2-pdf/releases
