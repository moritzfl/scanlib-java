package fr.labri.scanlib.database;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DB {

	/*
	 * Return with format "keyword -> library"
	 */
	public static Map<String,String> readContentIndex(String file) {
		final Map<String,String> database = new HashMap<String,String>();
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				String lib = "";
				boolean kw=false;

				public void startElement(String uri, String localName,String qName, 
						Attributes attributes) throws SAXException {
					if (qName.equalsIgnoreCase("lib")) {
						lib = attributes.getValue("id").trim();
					}
					if (qName.equalsIgnoreCase("kw")) {
						kw=true;
					}
				}

				public void endElement(String uri, String localName,String qName) throws SAXException {
					if (qName.equalsIgnoreCase("kw")) {
						kw=false;
					}
				}

				public void characters(char ch[], int start, int length) throws SAXException {
					if (kw) {
						database.put(new String(ch, start, length), lib);
					}
				}
			};
		
			if(new File(file).exists())
				saxParser.parse(file, handler);
			else {
				InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(file);
				saxParser.parse(is, handler);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return database;
	}

	/*
	 * Return with format "library -> Set<keyword>"
	 */
	public static Map<String,Set<String>> readContentLibrary(String file) {
		final Map<String,Set<String>> database = new HashMap<String,Set<String>>();
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				String lib = "";
				boolean kw=false;

				public void startElement(String uri, String localName,String qName, 
						Attributes attributes) throws SAXException {
					if (qName.equalsIgnoreCase("lib")) {
						lib = attributes.getValue("id").trim();
					}
					if (qName.equalsIgnoreCase("kw")) {
						kw=true;
					}
				}

				public void endElement(String uri, String localName,String qName) throws SAXException {
					if (qName.equalsIgnoreCase("kw")) {
						kw=false;
					}
				}

				public void characters(char ch[], int start, int length) throws SAXException {
					if (kw) {
						if(!database.containsKey(lib)) database.put(lib,new TreeSet<String>());
						database.get(lib).add(new String(ch, start, length));
					}
				}
			};
			saxParser.parse(file, handler);
			if(new File(file).exists())
				saxParser.parse(file, handler);
			else {
				InputStream is = DB.class.getResourceAsStream(file);
				saxParser.parse(is, handler);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return database;
	}

	//After deletion of elements in the database, the new minimal longest prefixes can be computed per library
	private static void checkReduction(Map<String, Set<String>> database) {
		for(String lib : database.keySet()) {
			if(database.get(lib).size()==1) {
				continue;
			}
			List<String> tmp = new ArrayList<String>(database.get(lib));
			String shortTest = longestCommonPrefix(tmp);
			if(!shortTest.isEmpty()) {
				boolean fail=false;
				for(String other : database.keySet()) {
					if(other.equals(lib)==false) {
						for(String othereg : database.get(other)) {
							if(othereg.startsWith(shortTest)) {
								fail=true;
								break;
							}
						}
					}
				}
				if(!fail) {
					database.get(lib).clear();
					database.get(lib).add(shortTest);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Map<String,String> db = readContentIndex("scanlib-data-bob.xml");
		saveContent(db, "scanlib-data.xml");
	}

	public static void writeContent(Map<String, Set<String>> database, String file) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;

			docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("scanlib");
			doc.appendChild(rootElement);
			for(String lib : database.keySet()) {

				// staff elements
				Element library = doc.createElement("lib");
				rootElement.appendChild(library);

				// set attribute to staff element
				Attr attr = doc.createAttribute("id");
				attr.setValue(lib);
				library.setAttributeNode(attr);

				for(String imp : database.get(lib)) {
					Element regexp = doc.createElement("kw");
					regexp.appendChild(doc.createTextNode(imp));
					library.appendChild(regexp);
				}	
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(file));
			transformer.transform(source, result);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveContent(Map<String, String> database, String file) {
		Map<String,Set<String>> index = new HashMap<String, Set<String>>();
		for(String kw : database.keySet()) {
			if(!index.containsKey(database.get(kw))) index.put(database.get(kw), new HashSet<String>());
			index.get(database.get(kw)).add(kw);
		}
		checkReduction(index);
		writeContent(index, file);
	}
	
	private static String longestCommonPrefix(List<String> strs) {
		// Start typing your Java solution below
		// DO NOT write main() function
		if(strs.size()==0) return"";
		String shortest = strs.get(0);
		for(String str: strs){
			if(str.isEmpty()) return "";
			if(str.length()<shortest.length())
				shortest = str;
		}
		int length = shortest.length();
		while(true){
			boolean good = true;
			for(String str:strs){
				if(!str.substring(0,length).equals(shortest.substring(0,length))){
					length--;
					good =false;
					break;
				}
			}
			if(good) return shortest.substring(0,length);
			if(length==0) return "";
		}
	}
}
