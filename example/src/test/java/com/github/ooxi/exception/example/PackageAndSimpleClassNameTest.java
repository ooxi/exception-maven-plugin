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
package com.github.ooxi.exception.example;

import static com.google.common.truth.Truth.assert_;
import org.junit.Test;

/**
 * @author ooxi
 */
public class PackageAndSimpleClassNameTest {


	/**
	 * Unfortunately it's not possible to distinguish whether reason for an
	 * error is the package, the simple class name or both. Therefore we are
	 * testing package and simple class name at once.
	 *
	 * @throws java.lang.ClassNotFoundException iff the class was generated
	 *     in the wrong package or with the wrong simple class name
	 */
	@Test
	public void testPackageAndSimpleClassName() throws ClassNotFoundException {
		Class<?> clazz = Class.forName("com.github.ooxi.exception.example.PackageAndSimpleClassNameException");

		assert_().withMessage("Generated exception `"+ clazz.getName() +"' does not extend `"+ Throwable.class.getName() +"'")
			.that(Throwable.class.isAssignableFrom(clazz)).isTrue();
	}
}
