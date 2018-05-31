package com.nathan.xmlcompare;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.ElementNameQualifier;
import org.custommonkey.xmlunit.MatchTracker;
import org.custommonkey.xmlunit.NodeDetail;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLAnalyzer {

    public static void main(String[] args) {
        //URL url1 = XMLAnalyzer.class.getResource("src/reference.xml");
        //URL url2 = XMLAnalyzer.class.getResource("src/sample1.xml");
        FileReader fr1 = null;
        FileReader fr2 = null;
        try {
            fr1 = new FileReader("/home/nathan/development/current/XMLAnalyzer/reference.xml");
            fr2 = new FileReader("/home/nathan/development/current/XMLAnalyzer/sample1.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Diff diff = new Diff(fr1, fr2);
            System.out.println("Similar? " + diff.similar());
            System.out.println("Identical? " + diff.identical());

            DetailedDiff detDiff = new DetailedDiff(diff);
            detDiff.overrideMatchTracker(new MatchTrackerImpl());
            detDiff.overrideElementQualifier(new ElementNameQualifier());
            detDiff.getAllDifferences();

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class MatchTrackerImpl implements MatchTracker {

    public void matchFound(Difference difference) {
        if (difference != null) {
            NodeDetail controlNode = difference.getControlNodeDetail();
            NodeDetail testNode = difference.getTestNodeDetail();

            String controlNodeValue = printNode(controlNode.getNode());
            String testNodeValue = printNode(testNode.getNode());

            if (controlNodeValue != null) {
                System.out.println("####################");
                System.out.println("Control Node: " + controlNodeValue);
            }
            if (testNodeValue != null) {
                System.out.println("Compare Node: " + testNodeValue);
                System.out.println("####################");
            }
        }
    }

    private static String printNode(Node node) {
        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            StringWriter sw = new StringWriter();
            try {
                Transformer t = TransformerFactory.newInstance().newTransformer();
                t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                t.transform(new DOMSource(node), new StreamResult(sw));
            } catch (TransformerException te) {
                System.out.println("nodeToString Transformer Exception");
            }
            return sw.toString();

        }
        return null;
    }
}