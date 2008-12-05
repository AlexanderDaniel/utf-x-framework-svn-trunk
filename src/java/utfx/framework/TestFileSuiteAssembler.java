package utfx.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xml.resolver.tools.CatalogResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Builds XSLTTestFileSuite from an XML test definition document. The tests
 * created are compatible with the JUnit testing framework.
 * <p>
 * Copyright &copy; 2004 - 2005 USQ and others.
 * </p>
 * <p>
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the <a href="http://www.gnu.org/licenses/gpl.txt">GNU General
 * Public License v2 </a> as published by the Free Software Foundation.
 * </p>
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * </p>
 * <code>
 * $Source: /cvs/utf-x/framework/src/java/utfx/framework/TestFileSuiteAssembler.java,v $
 * </code>
 * 
 * @author Jacek Radajewski
 * @author Oliver Lucido
 * @author Sally MacFarlane
 * @author Alex Daniel
 * @version $Revision$ $Date$ $Name:  $
 */
public class TestFileSuiteAssembler {

    /** Filename of this test definition document (test file suite) */
    private String filename;

    /** test file suite we are going to assemble */
    private XSLTTestFileSuite suite;

    /** XPath factory */
    private XPathFactory xpf;

    /** Xpath */
    private XPath xpath;

    /** XSLT transformer factory */
    private TransformerFactory tf;

    /** DOM Document builder factory */
    private DocumentBuilderFactory dbf;

    /** DOM Document builder */
    // private DocumentBuilderImpl db;
    private DocumentBuilder db;

    //    private DOMImplementationRegistry domRegistry;
    //
    //    private DOMImplementationLS domImplLS;
    //
    //    private LSParser lsParser;
    //
    //    private DOMConfiguration domConfig;

    /**
     * Construct a new TestFileSuiteAssembler.
     * 
     * @param filename the UTF-X test definition file to use for assembling the
     *        test suite.
     * @throws ParserConfigurationException if this constructor cannot create a
     *         document builder or its factory..
     */
    public TestFileSuiteAssembler(String filename)
        throws ParserConfigurationException {
        this.filename = filename;
        // DOMParser xercesDomParser;
        xpf = XPathFactory.newInstance();
        xpath = xpf.newXPath();
        xpath.setNamespaceContext(new UTFXNamespaceContext());
        tf = TransformerFactory.newInstance();
        dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setExpandEntityReferences(false);
        dbf.setValidating(false);
        db = dbf.newDocumentBuilder();
        db.setEntityResolver(new CatalogResolver());
        //        domRegistry = DOMImplementationRegistry.newInstance();
        //        domImplLS = (DOMImplementationLS) domRegistry
        //                .getDOMImplementation("LS");
        //        lsParser = domImplLS.createLSParser(
        //                DOMImplementationLS.MODE_SYNCHRONOUS,
        //                "http://www.w3.org/TR/REC-xml");
        //        domConfig = lsParser.getDomConfig();
        //        domConfig.setParameter("datatype-normalization", false);
    }

    /**
     * Assemble a test file suite. This method does the actual work of
     * assembling the test file suite with its test cases.
     * 
     * @throws Exception if the XLSTTestFileSuite cannot be assembled.
     */
    public void assemble() throws Exception {

        Element sourceBuilderElement;
        Element testElement;
        XSLTTransformTestCase testCase;
        Vector<XSLTTransformTestCase> testCases; // tests in this TestFileSuite
        NodeList utfxTests; // utfx:test elements

        testCases = new Vector<XSLTTransformTestCase>(0x4F);
        Document tdfDoc = parseTdf(filename); 
        utfxTests = tdfDoc.getElementsByTagName("utfx:test");

        // load and set default source builder for this test suite if exits
        sourceBuilderElement = (Element) xpath.evaluate(
            "/utfx:tests/utfx:source-builder", tdfDoc, XPathConstants.NODE);

        suite = new XSLTTestFileSuite(filename);

        if (sourceBuilderElement != null) {
            SourceParserFactory sbl = new SourceParserFactory();
            suite.setDefaultSourceBuilder(sbl
                .getSourceParser(sourceBuilderElement));
        }

        suite.setSourcePublicId(xpath.evaluate(
            "/utfx:tests/utfx:source-validation/utfx:dtd/@public", tdfDoc));
        suite.setSourceSystemId(xpath.evaluate(
            "/utfx:tests/utfx:source-validation/utfx:dtd/@system", tdfDoc));
        suite.setExpectedPublicId(xpath.evaluate(
            "/utfx:tests/utfx:expected-validation/utfx:dtd/@public", tdfDoc));
        suite.setExpectedSystemId(xpath.evaluate(
            "/utfx:tests/utfx:expected-validation/utfx:dtd/@system", tdfDoc));

        WrapperStylesheetGenerator wrapperGen = new WrapperStylesheetGenerator(getStylesheetUnderTestURI(tdfDoc));

        for (int i = 0; i < utfxTests.getLength(); i++) {
            testElement = (Element) utfxTests.item(i);

            Document wrapperDoc = wrapperGen.getWrapper(testElement);
            Source xsltSource = new DOMSource(wrapperDoc);
            Transformer transformer;
            try {
                transformer = tf.newTransformer(xsltSource);
            } catch (Exception e) {
                throw new MalformedStylesheetException(e);
            }

            testCase = new XSLTTransformTestCase(testElement, suite);
            testCase.setTransformer(transformer);
            testCases.add(testCase);
            suite.addTest(testCase);
        }
    }

	/**
     * Gets the XSLTTestFileSuite constructed by this assembler.
     * 
     * @return XSLTTestFileSuite constructed by this assembler.
     */
    public XSLTTestFileSuite getTestSuite() {
        return suite;
    }
    
    /**
     * Get the absolute URI of the stylesheet under test.
     * The stylesheet under test is retrieved from the TDF file
     * 
     * @param testDoc the TDF file
     * @return The absolute URI of the stylesheet under test
     * @throws MalformedURLException
     */
    private String getStylesheetUnderTestURI(Document testDoc) throws MalformedURLException {
        String xsltFile;
        File dir = new File(filename).getParentFile().getParentFile();

        try {
            String xsltFilename = xpath.evaluate("//utfx:stylesheet/@src", testDoc);
            xsltFile=(new File(dir, xsltFilename).toURL().toExternalForm()).toString();
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException("stylesheet source must be "
                + "defined in <utfx:stylesheet> element");
        }
        
        return xsltFile;
    }
    
    /**
     * Constructs a DOM document from the TDF filename
	 * @param tdfFilename
	 * @return DOM document
     * @throws IOException 
     * @throws SAXException 
     * @throws FileNotFoundException 
	 */
	private Document parseTdf(String tdfFilename) throws FileNotFoundException, SAXException, IOException {
    	return db.parse(new FileInputStream(filename));
	}

}