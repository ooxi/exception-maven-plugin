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
package com.github.ooxi.exception.vendor;

import com.sun.codemodel.writer.FileCodeWriter;
import java.io.File;
import java.io.IOException;

/**
 * Behaves just like {@link FileCodeWriter} but ensures that all resources will
 * be written using UTF-8 charset.
 *
 * @author ooxi
 */
public class Utf8FileCodeWriter extends FileCodeWriter {

	public Utf8FileCodeWriter(File target) throws IOException {
		super(target, "UTF-8");
	}

}
