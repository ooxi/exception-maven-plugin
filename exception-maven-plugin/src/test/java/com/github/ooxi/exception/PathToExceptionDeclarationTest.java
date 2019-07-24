/**
 * Copyright (c) 2015 - 2019 ooxi
 *     https://github.com/ooxi/exception-maven-plugin
 *     violetland@mail.ru
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *  1. The origin of this software must not be misrepresented; you must not
 *     claim that you wrote the original software. If you use this software in a
 *     product, an acknowledgment in the product documentation would be
 *     appreciated but is not required.
 *
 *  2. Altered source versions must be plainly marked as such, and must not be
 *     misrepresented as being the original software.
 *
 *  3. This notice may not be removed or altered from any source distribution.
 */
package com.github.ooxi.exception;

import com.google.auto.value.AutoValue;
import static com.google.common.base.Throwables.getRootCause;
import com.google.common.io.Files;
import static com.google.common.truth.Truth.assert_;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import org.junit.Test;
import org.xml.sax.SAXParseException;

/**
 * @author ooxi
 */
public class PathToExceptionDeclarationTest {


	@Test
	public void testMinimal() throws IOException {
		final String pkgName = "com.github.ooxi.exception";
		final String simpleName = "TestException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception />");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Wrong package name")
			.that(declaration.getPackageName()).isEqualTo(pkgName);
		assert_().withMessage("Wrong simple name")
			.that(declaration.getSimpleExceptionClassName()).isEqualTo(simpleName);
	}



	@Test
	public void testDefaultPackage() throws IOException {
		final String pkgName = "";
		final String simpleName = "DefaultPackageException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception />");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Wrong package name")
			.that(declaration.getPackageName()).isEqualTo(pkgName);
		assert_().withMessage("Wrong simple name")
			.that(declaration.getSimpleExceptionClassName()).isEqualTo(simpleName);
	}





	@Test
	public void testDefaultFinal() throws IOException {
		final String pkgName = "";
		final String simpleName = "DefaultFinalException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception />");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Wrong default final value")
			.that(declaration.isFinal()).isTrue();
	}



	@Test
	public void testTrueFinal() throws IOException {
		final String pkgName = "";
		final String simpleName = "TrueFinalException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception final=\"true\" />");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Exception should be final")
			.that(declaration.isFinal()).isTrue();
	}



	@Test
	public void testFalseFinal() throws IOException {
		final String pkgName = "";
		final String simpleName = "FalseFinalException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception final=\"false\" />");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Exception should not be final")
			.that(declaration.isFinal()).isFalse();
	}





	@Test
	public void testDefaultVisibility() throws IOException {
		final String pkgName = "";
		final String simpleName = "DefaultVisibilityException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception />");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Exception should be publically visible")
			.that(declaration.getVisibility()).isEqualTo(Visibility.PUBLIC);
	}



	@Test
	public void testPublicVisibility() throws IOException {
		final String pkgName = "";
		final String simpleName = "PublicVisibilityException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception visibility=\"PUBLIC\" />");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Exception should be publically visible")
			.that(declaration.getVisibility()).isEqualTo(Visibility.PUBLIC);
	}



	@Test
	public void testVisibleForTestingVisibility() throws IOException {
		final String pkgName = "";
		final String simpleName = "VisibleForTestingVisibilityException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception visibility=\"VISIBLE_FOR_TESTING\" />");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Exception should be visible for testing")
			.that(declaration.getVisibility()).isEqualTo(Visibility.VISIBLE_FOR_TESTING);
	}





	@Test
	public void testDefaultSuper() throws IOException {
		final String pkgName = "";
		final String simpleName = "DefaultSuperException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception />");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Wrong default super")
			.that(declaration.getCanonicalSuperClassName()).isEqualTo("java.lang.Exception");
	}



	@Test
	public void testRuntimeExceptionSuper() throws IOException {
		final String pkgName = "";
		final String simpleName = "RuntimeExceptionSuperException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception super=\"java.lang.RuntimeException\" />");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Wrong super")
			.that(declaration.getCanonicalSuperClassName()).isEqualTo("java.lang.RuntimeException");
	}





	@Test
	public void testDefaultJavaDoc() throws IOException {
		final String pkgName = "";
		final String simpleName = "DefaultJavaDocException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception />");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Wrong JavaDoc")
			.that(declaration.getJavaDoc()).isEmpty();
	}



	@Test
	public void testSomeJavaDoc() throws IOException {
		final String pkgName = "";
		final String simpleName = "SomeJavaDocException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception>My Döcumentation</Exception>");
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Wrong JavaDoc")
			.that(declaration.getJavaDoc()).isEqualTo("My Döcumentation");
	}



	@Test
	public void testTimeWhitespaceJavaDoc() throws IOException {
		final String pkgName = "";
		final String simpleName = "SomeJavaDocException";

		TestContext ctx = newTest(pkgName, simpleName, new StringBuilder()
			.append("<Exception>\n")
			.append("\tTrim \n")
			.append("\tWhitespace\n")
			.append("</Exception>\n")
		.toString());
		ExceptionDeclaration declaration = ctx.test();

		assert_().withMessage("Failed loading declaration")
			.that(declaration).isNotNull();
		assert_().withMessage("Wrong JavaDoc")
			.that(declaration.getJavaDoc()).isEqualTo("Trim \n\tWhitespace");
	}





	@Test
	public void testInvalidXml() throws IOException {
		final String pkgName = "";
		final String simpleName = "InvalidXmlException";

		TestContext ctx = newTest(pkgName, simpleName, "<Exception this-property-does-not-exist=\"\" />");

		try {
			ctx.test();
			assert_().fail("Expected `"+ SAXParseException.class.getName() +"' to be thrown");
		} catch (RuntimeException e) {
			Throwable rootCause = getRootCause(e);

			assert_().withMessage("Expected root cause of `"+ e +"' to be an instance of `"+ SAXParseException.class.getName() +"'")
				.that(rootCause).isInstanceOf(SAXParseException.class);
		}
	}





	private TestContext newTest(String pkgName, String simpleName, String content) throws IOException {

		/* Create root directory
		 */
		File root = Files.createTempDir();

		/* Create package directory
		 */
		File pkg;

		if (!pkgName.isEmpty()) {
			pkg = new File(root, pkgName.replace('.', File.separatorChar));
			assert_().that(pkg.mkdirs()).isTrue();
		} else {
			pkg = root;
		}

		/* Write content of test file
		 */
		File test = new File(pkg, simpleName +".xml");
		test.deleteOnExit();

		try (Writer w = new OutputStreamWriter(new FileOutputStream(test), "UTF-8")) {
			w.write(content);
		}

		/* Create {@link TestContext}
		 */
		return TestContext.of(root.toPath(), test.toPath());
	}



	@AutoValue
	static abstract class TestContext {

		public static TestContext of(Path root, Path test) {
			return new AutoValue_PathToExceptionDeclarationTest_TestContext(root, test);
		}


		public abstract Path getRoot();
		public abstract Path getTest();

		public final ExceptionDeclaration test() {
			return new PathToExceptionDeclaration(getRoot()).apply(getTest());
		}


		TestContext() {
		}
	}
}
