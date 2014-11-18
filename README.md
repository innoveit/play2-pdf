Play 2.3.x PDF module
===================

This module helps generating PDF documents dynamically from your Play! web application.
It simply renders your HTML- and CSS-based templates to PDF.
It is based on the Flying Saucer library, which in turn uses iText for PDF generation.
This project is a fork of https://github.com/benjohnde/play20-pdf .

Usage
-----

You can use a standard Play! scala template like this one:
``` html
@(message: String)

@main("Welcome to Play 2.0") {
    Image: <img src="/public/images/favicon.png"/><br/>
    Hello world! <br/>
    @message <br/>
}
```

Then this template, after having imported ```it.innove.PdfGenerator```, can simply be rendered as:
``` java
	public static Result document() {
		return PdfGenerator.ok(document.render("Your new application is ready."));
	}
```  
where ```PdfGenerator.ok``` is a simple shorthand notation for:
``` java
	ok(PdfGenerator.toBytes(document.render("Your new application is ready."))).as("application/pdf")
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

```PdfGenerator.loadLocalFonts(new String[]{"fonts/FreeSans.ttf"})```

Installation
------------

Currently, the module is hosted at Maven Central Repository.
Therefore, including the following lines in your ```build.scala``` will resolve it:
```
libraryDependencies ++= Seq(
  ...
      "it.innove" % "play2-pdf" % "1.0.0",
)
```
After the next restart of Play!, the module is available.
If you are using an IDE like Eclipse, remember to re-generate your project files. 


License
-------

Released under the MIT license; see the file LICENSE.

Releases
------------

<table>
	<tr>
		<td>1.0.0</td>
		<td>12/11/2014</td>
		<td>Play 2.3</td>
	</tr>
</table>
