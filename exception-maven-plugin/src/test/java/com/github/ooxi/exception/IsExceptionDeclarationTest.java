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

import com.google.common.io.Files;
import static com.google.common.truth.Truth.assert_;
import com.github.ooxi.exception.IsExceptionDeclaration.ExceptionDirectoryContainsUnexpectedFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;
import org.junit.Test;

/**
 * @author ooxi
 */
public class IsExceptionDeclarationTest {


	@Test
	public void testDirectory() {
		Predicate<Path> predicate = new IsExceptionDeclaration();
		Path directory = Files.createTempDir().toPath();

		assert_().that(predicate.test(directory)).isFalse();
	}


	@Test
	public void testValidFile() throws IOException {
		Predicate<Path> predicate = new IsExceptionDeclaration();
		Path validFile = File.createTempFile("test-valid-file-", ".xml").toPath();

		assert_().that(predicate.test(validFile)).isTrue();
	}


	@Test
	public void testInvalidFile() throws IOException {
		Predicate<Path> predicate = new IsExceptionDeclaration();
		Path invalidFile = File.createTempFile("test-invalid-file-", ".not-xml").toPath();

		try {
			predicate.test(invalidFile);
			assert_().fail("Execpted `"+ ExceptionDirectoryContainsUnexpectedFileException.class.getName() +"' to be thrown");
		} catch (ExceptionDirectoryContainsUnexpectedFileException e) {
			// Expected
		}
	}
}
