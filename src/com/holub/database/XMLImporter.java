package com.holub.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.holub.tools.ArrayIterator;

public class XMLImporter implements Table.Importer {

	private BufferedReader  in;			// null once end-of-file reached
	private String[]        columnNames;
	private String          tableName;
	
	private Document doc;
	private XPath xpath;
	private int rdx = 1;
		
	public XMLImporter( Reader in ) throws IOException
	{	
		this.in = in instanceof BufferedReader ? (BufferedReader)in : new BufferedReader(in);
		
		String result = "";
		String line;
        while ((line = (this.in).readLine()) != null) {
            result = result + line.trim();// result = URL로 XML을 읽은 값
        }
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
		
        InputSource is = new InputSource(new StringReader(result));        
        DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
			XPathFactory xpathFactory = XPathFactory.newInstance();
            xpath = xpathFactory.newXPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void startTable() throws IOException {
		
        try {
            XPathExpression expr = xpath.compile("/");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            NodeList child = nodeList.item(0).getChildNodes();
            Node node = child.item(0);
            tableName = node.getNodeName();
            
            expr = xpath.compile("/"+tableName+"/row[1]");
            nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            child = nodeList.item(0).getChildNodes();
            columnNames = new String[child.getLength()];
            for (int i = 0; i < child.getLength(); i++) {
                node = child.item(i);
                columnNames[i] = node.getNodeName();
            }               
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String loadTableName()		throws IOException
	{	return tableName;
	}
	public int loadWidth()			    throws IOException
	{	return columnNames.length;
	}
	public Iterator loadColumnNames()	throws IOException
	{	return new ArrayIterator(columnNames);  //{=CSVImporter.ArrayIteratorCall}
	}

	@Override
	public Iterator loadRow() throws IOException {
		
		String[] row = new String[columnNames.length];
		
		try {
			XPathExpression expr = xpath.compile("/"+tableName+"/row["+(rdx++)+"]");
	        NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	        if (nodeList.getLength()==0) return null;
	        NodeList child = nodeList.item(0).getChildNodes();
	        for (int i = 0; i < child.getLength(); i++) {
	            Node node = child.item(i);
	            row[i] = node.getTextContent();
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
        
		return new ArrayIterator(row);
	}

	@Override
	public void endTable() throws IOException {		
	}
	
	public static class Test
	{ 	public static void main( String[] args ) throws IOException
		{	
			Reader in = new FileReader( "test.xml" );
			
			Table people = TableFactory.create( new XMLImporter(in) );
			in.close();
	
//			javax.swing.JFrame frame = new javax.swing.JFrame();
//			frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	
			BufferedWriter writer = new BufferedWriter(new FileWriter("test.html"));

			HTMLExporter tableBuilder = new HTMLExporter(writer);
			people.export( tableBuilder );
	
			writer.close();
//			frame.getContentPane().add(
//					new JScrollPane( tableBuilder.getJTable() ) );
//			frame.pack();
//			frame.setVisible( true );			
		}
	}

}
