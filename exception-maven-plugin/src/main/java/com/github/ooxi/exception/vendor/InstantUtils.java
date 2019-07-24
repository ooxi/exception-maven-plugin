/**
 * Copyright (c) 2014 - 2019 ooxi
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
package com.github.ooxi.exception.vendor;

import com.google.common.annotations.VisibleForTesting;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Since RFC 2822 is insufficient for displaying date time, we are using JSR310.
 *
 * @author ooxi
 */
public final class InstantUtils {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_INSTANT;





	public static Instant now() {
		return Instant.now();
	}





	public static String format(Instant instant) throws FormatInstantException {
		try {
			return FORMAT.format(instant);
		} catch (DateTimeException e) {
			throw new FormatInstantException("Failed formatting `"+ instant +"'", e);
		}
	}



	public static Instant parse(CharSequence s) throws ParseInstantException {
		try {
			return FORMAT.parse(s, Instant::from);
		} catch (DateTimeParseException e) {
			throw new ParseInstantException("Cannot parse `"+ s +"'", e);
		}
	}





	@VisibleForTesting static final class FormatInstantException extends RuntimeException {
		public FormatInstantException(String msg, Throwable cause) {
			super(msg, cause);
		}
	}



	@VisibleForTesting static final class ParseInstantException extends RuntimeException {
		public ParseInstantException(String msg, Throwable cause) {
			super(msg, cause);
		}
	}





	private InstantUtils() {
	}
}
