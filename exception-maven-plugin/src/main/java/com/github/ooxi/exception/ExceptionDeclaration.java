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

/**
 * @author ooxi
 */
@AutoValue
abstract class ExceptionDeclaration {


	public static ExceptionDeclaration of(
				Visibility visibility,
				boolean isFinal,
				String packageName,
				String simpleExceptionClassName,
				String canonicalSuperClassName,
				String javaDoc,
				boolean useExceptionChaining,
				String xml
			) {

		return new AutoValue_ExceptionDeclaration(
			visibility,
			isFinal,
			packageName,
			simpleExceptionClassName,
			canonicalSuperClassName,
			javaDoc,
			useExceptionChaining,
			xml
		);
	}



	/**
	 * @return Visibility of generated class
	 */
	public abstract Visibility getVisibility();

	/**
	 * @return true iff the generated class should be final
	 */
	public abstract boolean isFinal();

	/**
	 * @return Absolute package name in which the class should be generated
	 *     (empty means default package)
	 */
	public abstract String getPackageName();

	/**
	 * @return Simple class name of generated class
	 */
	public abstract String getSimpleExceptionClassName();

	/**
	 * @return Fully qualified class name of super class
	 */
	public abstract String getCanonicalSuperClassName();

	/**
	 * @return Human readable class documentation (empty means no additional
	 *     documentation should be generated)
	 */
	public abstract String getJavaDoc();

	/**
	 * @return true iff exception chaining should be used
	 *
	 * Exception chaining has to be used with old super classes which do not
	 * expose a separate constructor accepting a {@link Throwable} cause.
	 */
	public abstract boolean useExceptionChaining();

	/**
	 * @return XML exception specification, should only be used for
	 *     documentation purposes
	 */
	public abstract String getXml();



	ExceptionDeclaration() {
	}
}
