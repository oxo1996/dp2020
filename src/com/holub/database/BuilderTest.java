package com.holub.database;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class BuilderTest {

	@Test
    public void test(){  
		try {
			
//			Table people = TableFactory.create( "people",
//					   new String[]{ "First", "Last"		} );
//			people.insert( new String[]{ "Allen",	"Holub" 	} );
//			people.insert( new String[]{ "Ichabod",	"Crane" 	} );
//			people.insert( new String[]{ "Rip",		"VanWinkle" } );
//			people.insert( new String[]{ "Goldie",	"Locks" 	} );
			
//			Reader in = new FileReader( "c:/dp2020/name.csv" );
//			Table people = TableFactory.create( new CSVImporter(in) );
//			in.close();
			
			Reader in = new FileReader( "c:/dp2020/test.xml" );
			Table people = TableFactory.create( new XMLImporter(in) );
			in.close();
			
			// ---importer---
			// ---exporter---
			
//			javax.swing.JFrame frame = new javax.swing.JFrame();
//			frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//			JTableExporter tableBuilder = new JTableExporter();
//			people.export( tableBuilder );
//			frame.getContentPane().add(new JScrollPane( tableBuilder.getJTable() ) );
//			frame.pack();
//			frame.setVisible( true );
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("c:/dp2020/test2.html"));
			HTMLExporter tableBuilder = new HTMLExporter(writer);
			people.export( tableBuilder );
			writer.close();
			
//			BufferedWriter writer = new BufferedWriter(new FileWriter("c:/dp2020/test.xml"));
//			CSVExporter tableBuilder = new CSVExporter(writer);
//			people.export( tableBuilder );
//			writer.close();
			
//			BufferedWriter writer = new BufferedWriter(new FileWriter("c:/dp2020/test.xml"));
//			XMLExporter tableBuilder = new XMLExporter(writer);
//			people.export( tableBuilder );
//			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }

}
