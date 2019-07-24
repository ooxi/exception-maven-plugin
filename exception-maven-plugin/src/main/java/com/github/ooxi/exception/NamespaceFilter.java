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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * Replaces all XML namespaces with a known namespace.
 *
 * @author Alec Holmes
 */
final class NamespaceFilter extends XMLFilterImpl {

	private final String requiredNamespace = "https://github.com/ooxi/exception-maven-plugin";

	public NamespaceFilter(XMLReader parent) {
		super(parent);
	}



	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if (!requiredNamespace.equals(uri)) {
			uri = requiredNamespace;
		}
		super.startElement(uri, localName, qName, atts);
	}
}
