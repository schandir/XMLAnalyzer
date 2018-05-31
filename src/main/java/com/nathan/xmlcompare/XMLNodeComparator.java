package com.nathan.xmlcompare;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XMLNodeComparator {

    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    public static void main() throws Exception {
        Document doc = loadXMLFromString("<test>\n" +
                "  <elem>b</elem>\n" +
                "  <elem>a</elem>\n" +
                "</test>");
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//test//elem");
        NodeList all = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        List<String> values = new ArrayList<>();
        if (all != null && all.getLength() > 0) {
            for (int i = 0; i < all.getLength(); i++) {
                values.add(all.item(i).getTextContent());
            }
        }
        Set<String> expected = new HashSet<>(Arrays.asList("a", "b"));
    }
}
