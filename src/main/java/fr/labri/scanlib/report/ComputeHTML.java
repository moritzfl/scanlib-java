package fr.labri.scanlib.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import java.util.HashSet;
import java.util.List;

import java.util.Set;
import java.util.TreeMap;

import fr.labri.scanlib.database.DB;

public class ComputeHTML {

	public static void main(String[] args) throws IOException {
		final TreeMap<String,Set<String>> database = new TreeMap<String, Set<String>>(DB.readContentLibrary("scanlib-data.xml"));

		Set<String> firstLetters = new HashSet<String>();

		for(String lib : database.keySet()) {
			firstLetters.add(String.valueOf(lib.charAt(0)).toUpperCase());
		}

		List<String> letters = new ArrayList<String>(firstLetters);
		Collections.sort(letters);


		StringBuffer buff = new StringBuffer();

		buff.append("<h3><a name='top'>Scanlib - List of "+database.keySet().size()+" Libraries with Prefixes</a><h3>");
		buff.append("<br><br>");
		buff.append("<hr><br>");
		buff.append("<div align='center'>");
		for(String letter : letters) {
			buff.append("<a href='#"+letter+"'>"+letter+"</a>");
			if(letters.indexOf(letter) < letters.size()-1)
				buff.append(" - ");
		}
		buff.append("</div>");
		buff.append("<hr><br>");


		Set<Integer> numbers = new HashSet<Integer>();
		for(String lib : database.keySet()) {
			numbers.add(database.get(lib).size());
		}

		List<Integer> sorted = new ArrayList<Integer>(numbers);
		Collections.sort(sorted);
		Collections.reverse(sorted);

		buff.append("<ul id=\"LinkedList1\" class=\"LinkedList\">");

		for(String letter : letters) {
			buff.append("<hr>");
			buff.append("<h3><a name='"+letter+"'>"+letter+"</a></h3>");	
			for(String lib : database.keySet()) {
				if(lib.toUpperCase().startsWith(letter)) {
					buff.append("<li>"+lib+" ("+database.get(lib).size()+")");		
					buff.append("\t\t<ul id=\"LinkedList2\" class=\"LinkedList2\">\n");
					for(String regexp : database.get(lib)) {
						buff.append("<li>"+regexp+"</li>");
					}
					buff.append("</ul>\n");		
					buff.append("</li>\n");		

				}

			}
			buff.append("<a href='#top'>Top</a>");		
		}


		buff.append("</ul>\n");		

		HTMLReport.produceReport("report/scanlib.html", buff);
	} 

}
