package com.holub.database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class HTMLExporter implements Table.Exporter {

	private StringBuilder html= new StringBuilder();
	private String tableName;
	
	@Override
	public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {	
		this.tableName = tableName;
		
		html.append("<html><head><title>");
		html.append(tableName);
		html.append("</title></head><body><table>");
		storeRow(columnNames);
	}

	@Override
	public void storeRow(Iterator data) throws IOException {
		html.append("<tr>");
		while( data.hasNext() ) {
			html.append("<td>");
			html.append(data.next().toString());
			html.append("</td>");
		}
		html.append("</tr>");
	}
	
	public String getHTML() throws IOException{
		return html.toString();
	}

	public void startTable() throws IOException {/*nothing to do*/}
	public void endTable()   throws IOException {/*nothing to do*/}

	
	public void BufferedWritter() throws IOException {
		html.append("</table></body></html>");
		BufferedWriter writer = new BufferedWriter(new FileWriter(tableName + ".html"));
		writer.write(html.toString());
		writer.close();
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

			HTMLExporter tableBuilder = new HTMLExporter();
			people.export( tableBuilder );
			
			tableBuilder.BufferedWritter();
			
		}
	}
	
}
