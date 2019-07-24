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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * @author ooxi
 */
@Mojo(name = "generate-sources", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public final class GenerateSourcesMojo extends AbstractMojo {

	@Parameter(property = "project", readonly = true, required = true)
	private MavenProject project;

	@Parameter(alias = "exceptionDirectory", required = true)
	private File exceptionDirectory;

	@Parameter(alias = "sourceDirectory", required = true)
	private File sourceDirectory;

	@Parameter(alias = "outputGeneratedDate", required = false)
	private boolean outputGeneratedDate = false;



	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		/* Skip plugin execution without throwing an error if source
		 * directory does not exist
		 */
		if (!exceptionDirectory.exists() || !exceptionDirectory.isDirectory() || !exceptionDirectory.canRead()) {
			getLog().debug("Exception directory `"+ exceptionDirectory.getAbsolutePath() +"' does not exist, therefore no action will be performed.");
			return;
		}


		/* Automaticall create target directory if it does not exist
		 */
		if (!sourceDirectory.exists()) {
			if (!sourceDirectory.mkdirs()) {
				getLog().error("Cannot create source directory `"+ sourceDirectory.getAbsolutePath() +"'");
				return;
			}
		}
		if (!sourceDirectory.isDirectory() || !sourceDirectory.canWrite()) {
			getLog().error("Cannot write into source directory `"+ sourceDirectory.getAbsolutePath() +"'");
			return;
		}


		/* Apply source code generation configuration from Maven
		 * properties
		 */
		final Configuration config = Configuration.builder()
			.outputGeneratedDate(outputGeneratedDate)
		.build();


		/* Transform XML exception specifications into their Java
		 * equivalent
		 */
		try {
			final Path exceptionPath = exceptionDirectory.toPath();
			final ExceptionSourceGenerator generator = new ExceptionSourceGenerator(config);

			Files	.walk(exceptionPath)
				.filter(new IsExceptionDeclaration())
				.map(new PathToExceptionDeclaration(exceptionPath))
			.forEach(generator);

			generator.build(sourceDirectory);

			/* Add directory containing generated exception source
			 * code to Maven project
			 */
			project.addCompileSourceRoot(sourceDirectory.getAbsolutePath());

		/* Forward all exceptions using a commong exception base class
		 */
		} catch (IOException | RuntimeException e) {
			throw new MojoExecutionException("Failed transforming exceptions from `"+ exceptionDirectory.getAbsolutePath() +"' to `"+ sourceDirectory.getAbsolutePath() +"'", e);
		}
	}

}
