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
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.joor.Reflect;
import org.junit.Test;

/**
 * @author ooxi
 */
public class GenerateSourcesMojoTest {


	@Test
	public void testNoActionPerformedIfExceptionDirectoryDoesNotExist() throws MojoExecutionException, MojoFailureException {
		GenerateSourcesMojo mojo = new GenerateSourcesMojo();


		/* Set a non existing source directory
		 */
		File exceptionDirectory = new File(Files.createTempDir(), "this-file-does-not-exist");
		Reflect.on(mojo).set("exceptionDirectory", exceptionDirectory);


		/* Expect debug output from Maven plugin
		 */
		final AtomicBoolean debugInvoked = new AtomicBoolean(false);

		mojo.setLog(new MockLog() {

			@Override
			public void debug(CharSequence cs) {
				assert_().withMessage("`debug(CharSequence)' must be invoked exactly once")
					.that(debugInvoked.compareAndSet(false, true)).isTrue();

				assert_().withMessage("Unexpected debug message")
					.that(cs.toString()).contains("no action will be performed");
			}
		});


		/* Execute Mojo
		 */
		mojo.execute();


		assert_().withMessage("It seams like no action was performed but also no debug message was emitted")
			.that(debugInvoked.get()).isTrue();
	}
}
