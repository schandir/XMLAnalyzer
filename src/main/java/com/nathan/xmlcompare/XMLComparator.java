package com.nathan.xmlcompare;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.ElementNameQualifier;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

/**
 *
 * Java program to compare two XML files using XMLUnit example

 * @author Javin Paul
 */
public class XMLComparator {


    public static void main(String args[]) throws FileNotFoundException,
            SAXException, IOException {

        // reading two xml file to compare in Java program
        FileInputStream fis1 = new FileInputStream("/home/nathan/development/current/XMLAnalyzer/reference.xml");
        FileInputStream fis2 = new FileInputStream("/home/nathan/development/current/XMLAnalyzer/sample1.xml");

        // using BufferedReader for improved performance
        BufferedReader  source = new BufferedReader(new InputStreamReader(fis1));
        BufferedReader  target = new BufferedReader(new InputStreamReader(fis2));

        XMLUnit.setIgnoreAttributeOrder(true);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setNormalizeWhitespace(true);

        //comparing two XML using XMLUnit in Java
        List differences = compareXML(source, target);

        //showing differences found in two xml files
        printDifferences(differences);

    }

    public static List compareXML(Reader source, Reader target) throws
            SAXException, IOException{
        DifferenceListener myDifferenceListener = new IgnoreTextAndAttributeValuesDifferenceListener();


        //creating Diff instance to compare two XML files
        Diff xmlDiff = new Diff(source, target);
        xmlDiff.overrideDifferenceListener(myDifferenceListener);
        //for getting detailed differences between two xml files
        DetailedDiff detailXmlDiff = new DetailedDiff(xmlDiff);

        return detailXmlDiff.getAllDifferences();
    }


    public static void printDifferences(List<Difference> differences){
        int totalDifferences = differences.size();
        System.out.println("===============================");
        System.out.println("Total differences : " + totalDifferences);
        System.out.println("================================");

        for(Difference difference : differences){
            System.out.println(difference);
        }
    }
}