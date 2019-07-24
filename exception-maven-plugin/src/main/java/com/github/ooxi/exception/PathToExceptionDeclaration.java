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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import static com.google.common.collect.Lists.reverse;
import com.google.common.io.ByteStreams;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Transforms XML exception specifications to {@link ExceptionDeclaration}
 * instances.
 *
 * @author ooxi
 */
final class PathToExceptionDeclaration implements Function<Path, ExceptionDeclaration> {

	private static final String SCHEMA_RESOURCE = "Exception.xsd";
	private final Path root;

	public PathToExceptionDeclaration(Path root) {
		this.root = root;
	}



	@Override
	public ExceptionDeclaration apply(Path path) {
		try {
			return load(path);
		} catch (IOException | SAXException | XmlException e) {
			throw new RuntimeException("Failed transforming `"+ path +"'", e);
		}
	}



	public ExceptionDeclaration load(Path path) throws IOException, SAXException, XmlException {
		checkArgument(path.startsWith(root), "Path `%s' is outside of root `%s'", path, root);


		/* Load schema as well as document into an internal buffer
		 */
		final Charset CHARSET = Charset.forName("UTF-8");
		final byte[] schemaBuffer;
		final byte[] documentBuffer;

		try (	InputStream schemaStream = checkNotNull(PathToExceptionDeclaration.class.getResourceAsStream(SCHEMA_RESOURCE), "Cannot open schema resource `%s'", SCHEMA_RESOURCE);
			InputStream documentStream = checkNotNull(Files.newInputStream(path, StandardOpenOption.READ), "Cannot open exception xml declaration document `%s'", path.toAbsolutePath());
		) {
			schemaBuffer = ByteStreams.toByteArray(schemaStream);
			documentBuffer = ByteStreams.toByteArray(documentStream);
		}


		/* Validate XML document against an XSD
		 *
		 * @see http://stackoverflow.com/q/2991091/2534648
		 */
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new StreamSource(
			new ByteArrayInputStream(schemaBuffer), CHARSET.name()
		));
		Validator validator = schema.newValidator();

		Source source = new SAXSource(
			new NamespaceFilter(XMLReaderFactory.createXMLReader()),
			new InputSource(new ByteArrayInputStream(documentBuffer))
		);
		validator.validate(source, null);


		/* Load validated XML document
		 */
		ExceptionDocument document = ExceptionDocument.Factory.parse(new ByteArrayInputStream(documentBuffer), new XmlOptions()
			.setLoadSubstituteNamespaces(ImmutableMap.of("", "https://github.com/ooxi/exception-maven-plugin"))
		);
		ExceptionType exception = document.getException();


		return ExceptionDeclaration.of(
			getVisibility(exception),
			isFinal(exception),
			getPackageName(path),
			getSimpleExceptionClassName(path),
			getCanonicalSuperClassName(exception),
			getJavaDoc(exception),
			useExceptionChaining(exception),
			new String(documentBuffer, "UTF-8")
		);
	}





	/**
	 * Fully qualified class name can be derived from directory structure
	 */
	private String getPackageName(Path path) {
		Path absoluteRoot = root.toAbsolutePath();
		Path absolutePath = path.toAbsolutePath();

		List<Path> packageElements = new ArrayList<>();

		for (Path p = absolutePath.getParent(); p.startsWith(absoluteRoot) && !p.equals(absoluteRoot); p = p.getParent()) {
			packageElements.add(p.getFileName());
		}

		return reverse(packageElements).stream()
			.map(Path::toString)
			.collect(joining("."));
	}



	private String getSimpleExceptionClassName(Path path) {
		final String filename = path.getFileName().toString();
		return filename.substring(0, filename.length() - ".xml".length());
	}



	/**
	 * An exception is final unless explicitly specified otherwise
	 */
	private boolean isFinal(ExceptionType exception) {
		final boolean DEFAULT_FINAL = true;

		if (!exception.isSetFinal()) {
			return DEFAULT_FINAL;
		}
		return exception.getFinal();
	}



	/**
	 * An exception publicly visible unless specified otherwise
	 */
	private Visibility getVisibility(ExceptionType exception) {
		final Visibility DEFAULT_VISIBILITY = Visibility.PUBLIC;

		if (!exception.isSetVisibility()) {
			return DEFAULT_VISIBILITY;
		}
		return Visibility.valueOf(exception.getVisibility());
	}



	/**
	 * An exception extends {@link Exception} unless specified otherwise
	 */
	private String getCanonicalSuperClassName(ExceptionType exception) {
		final String DEFAULT_SUPER = Exception.class.getName();

		if (!exception.isSetSuper()) {
			return DEFAULT_SUPER;
		}
		return exception.getSuper();
	}



	/**
	 * Additional human readable documentation
	 */
	private String getJavaDoc(ExceptionType exception) {
		return exception.getStringValue().trim();
	}



	/**
	 * Some exceptions do not expose a constructor accepting a
	 * {@link Throwable} cause. If such an exception is specified as base
	 * class, explicit exception chaining has to be used.
	 */
	private boolean useExceptionChaining(ExceptionType exception) {
		final String canonicalSuperClassName = getCanonicalSuperClassName(exception);

		final ImmutableList<Class<?>> DO_NOT_USE_EXCEPTION_CHAINING_FOR_THESE_SUPER_CLASSES = ImmutableList.of(
			NoSuchElementException.class
		);

		return !DO_NOT_USE_EXCEPTION_CHAINING_FOR_THESE_SUPER_CLASSES.stream()
			.map(Class::getName)
			.collect(toList())
			.contains(canonicalSuperClassName);
	}
}
