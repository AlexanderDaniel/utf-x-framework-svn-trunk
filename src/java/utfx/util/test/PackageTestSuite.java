package utfx.util.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * utfx.util Test Suite.
 * 
 * <p>
 * Copyright &copy; 2007 UTF-X Development Team.
 * </p>
 * 
 * <p>
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the <a href="http://www.gnu.org/licenses/gpl.txt">GNU General
 * Public License v2 </a> as published by the Free Software Foundation.
 * </p>
 * 
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * </p>
 * 
 * <code>
 * $Source: $
 * </code>
 * 
 * @author Jacek Radajewski
 * @author Alex Daniel
 * @version $Revision$ $Date$ $Name: $
 */
public class PackageTestSuite extends TestCase {

    /**
     * Constructs a test suit with a specified name.
     */
    public PackageTestSuite(String name) {
        super(name);
    }

    /**
     * Assemble and return test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("utfx.util");

        suite.addTest(CanonicalFormTest.suite());
        suite.addTest(DOMWriterTest.suite());
        suite.addTest(SourceSerializerTest.suite());

        return suite;
    }
}

