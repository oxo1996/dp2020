package com.holub.database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class HTMLExporter implements Table.Exporter {

	private final Writer out;
	private String tableName;
	
	public HTMLExporter( Writer out )
	{	this.out = out;
	}
	
	@Override
	public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {	
		this.tableName = tableName;
		
		out.write("<html><head><title>");
		out.write(tableName);
		out.write("</title></head><body><table>");
		storeRow(columnNames);
	}

	@Override
	public void storeRow(Iterator data) throws IOException {
		out.write("<tr>");
		while( data.hasNext() ) {
			out.write("<td>");
			out.write(data.next().toString());
			out.write("</td>");
		}
		out.write("</tr>");
	}

	public void startTable() throws IOException {/*nothing to do*/}
	
	public void endTable() throws IOException {
		out.write("</table></body></html>");
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

			BufferedWriter writer = new BufferedWriter(new FileWriter("test.html"));
			HTMLExporter tableBuilder = new HTMLExporter(writer);
			people.export( tableBuilder );
			writer.close();
						
		}
	}
	
}
