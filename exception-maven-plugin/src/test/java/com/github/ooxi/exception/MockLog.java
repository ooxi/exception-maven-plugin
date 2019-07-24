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

import org.apache.maven.plugin.logging.Log;

/**
 * Throws an exception as soon as any unexpected method is called. Test should
 * extend this class with methods expecting invocations.
 *
 * @author ooxi
 */
public class MockLog implements Log {

	@Override
	public boolean isDebugEnabled() {
		throw new AssertionError("`isDebugEnabled' must not be invoked");
	}

	@Override
	public void debug(CharSequence cs) {
		throw new AssertionError("`debug(CharSequence)' must not be invoked");
	}

	@Override
	public void debug(CharSequence cs, Throwable thrwbl) {
		throw new AssertionError("`debug(CharSequence, Throwable)' must not be invoked");
	}

	@Override
	public void debug(Throwable thrwbl) {
		throw new AssertionError("`debug(Throwable)' must not be invoked");
	}

	@Override
	public boolean isInfoEnabled() {
		throw new AssertionError("`isInfoEnabled' must not be invoked");
	}

	@Override
	public void info(CharSequence cs) {
		throw new AssertionError("`info(CharSequence)' must not be invoked");
	}

	@Override
	public void info(CharSequence cs, Throwable thrwbl) {
		throw new AssertionError("`info(CharSequence, Throwable)' must not be invoked");
	}

	@Override
	public void info(Throwable thrwbl) {
		throw new AssertionError("`info(Throwable)' must not be invoked");
	}

	@Override
	public boolean isWarnEnabled() {
		throw new AssertionError("`isWarnEnabled' must not be invoked");
	}

	@Override
	public void warn(CharSequence cs) {
		throw new AssertionError("`warn(CharSequence)' must not be invoked");
	}

	@Override
	public void warn(CharSequence cs, Throwable thrwbl) {
		throw new AssertionError("`warn(CharSequence, Throwable)' must not be invoked");
	}

	@Override
	public void warn(Throwable thrwbl) {
		throw new AssertionError("`warn(Throwable)' must not be invoked");
	}

	@Override
	public boolean isErrorEnabled() {
		throw new AssertionError("`isErrorEnabled' must not be invoked");
	}

	@Override
	public void error(CharSequence cs) {
		throw new AssertionError("`error(CharSequence)' must not be invoked");
	}

	@Override
	public void error(CharSequence cs, Throwable thrwbl) {
		throw new AssertionError("`error(CharSequence, Throwable)' must not be invoked");
	}

	@Override
	public void error(Throwable thrwbl) {
		throw new AssertionError("`error(Throwable)' must not be invoked");
	}

}
