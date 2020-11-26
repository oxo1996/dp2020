package com.holub.database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class XMLExporter implements Table.Exporter {
	
	private final Writer out;
	private String tableName;
	private int width;
	private String[] columns;
	
	public XMLExporter( Writer out )
	{	this.out = out;
	}
	
	@Override
	public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {	
		
		out.write("<"+tableName+">\n");
		
		this.tableName = tableName;
		this.width = width;
		
		columns = new String[width];
		int i = 0;
		while (columnNames.hasNext()) {
			columns[i++] = columnNames.next().toString();
		}
		
	}

	@Override
	public void storeRow(Iterator data) throws IOException {
		int i = 0;
		out.write( "<row>" );
		while( data.hasNext())
		{	
			Object datum = data.next();
			
			out.write( "<"+columns[i].toString()+">" );
			if( datum != null )	out.write( datum.toString() );
			out.write( "<"+columns[i].toString()+"/>" );
			
			if( ++i < width ) out.write("\t");
		}
		out.write( "<row/>" );
		out.write("\n");
	}

	public void startTable() throws IOException {
		out.write("<?xml version = \"1.0\"?>\n");
	}
	
	public void endTable() throws IOException {
		out.write("<"+tableName+"/>");
	}
	
	public static class Test
	{ 	public static void main( String[] args ) throws IOException
		{	
			Table people = TableFactory.create( "people",
						   new String[]{ "First", "Last"		} );
			people.insert( new String[]{ "Allen",	"Holub" 	} );
			people.insert( new String[]{ "Ichabod",	"Crane" 	} );
			people.insert( new String[]{ "Rip",		"VanWinkle" } );
			people.insert( new String[]{ "Goldie",	"Locks" 	} );

			BufferedWriter writer = new BufferedWriter(new FileWriter("test.xml"));
			XMLExporter tableBuilder = new XMLExporter(writer);
			people.export( tableBuilder );
			writer.close();
		}
	}
}
