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
import java.lang.reflect.Modifier;
import org.junit.Test;

/**
 * @author ooxi
 */
public class FinalTest {


	@Test
	public void testDefault() {
		Class<FinalDefaultException> clazz = FinalDefaultException.class;

		assert_().withMessage("Exception should be final by default")
			.that(Modifier.isFinal(clazz.getModifiers())).isTrue();
	}


	@Test
	public void testTrue() {
		Class<FinalTrueException> clazz = FinalTrueException.class;

		assert_().withMessage("Exception should be final")
			.that(Modifier.isFinal(clazz.getModifiers())).isTrue();
	}


	@Test
	public void testFalse() {
		Class<FinalFalseException> clazz = FinalFalseException.class;

		assert_().withMessage("Exception should not be final by default")
			.that(Modifier.isFinal(clazz.getModifiers())).isFalse();
	}
}
