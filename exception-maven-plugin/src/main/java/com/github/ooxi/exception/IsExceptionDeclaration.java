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

import com.google.common.annotations.VisibleForTesting;
import java.io.File;
import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * Accepts only files looking like they might contain exception specifications.
 *
 * @author ooxi
 */
final class IsExceptionDeclaration implements Predicate<Path> {

	@Override
	public boolean test(Path path) {
		final File file = path.toFile();

		if (!file.isFile()) {
			return false;
		}
		if (file.getName().endsWith(".xml")) {
			return true;
		}

		/* The exception specification directory must not contain any
		 * files not looking like exception specifications. This
		 * prevents hard to debug errors where an exception is not
		 * generated because it was skipped by this predicate
		 */
		throw new ExceptionDirectoryContainsUnexpectedFileException("Exception directory contains file `"+ file.getAbsolutePath() +"' which does not look like an exception declaration");
	}





	@VisibleForTesting static final class ExceptionDirectoryContainsUnexpectedFileException extends RuntimeException {
		public ExceptionDirectoryContainsUnexpectedFileException(String msg) {
			super(msg);
		}
	}
}
