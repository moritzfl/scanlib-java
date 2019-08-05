package fr.labri.scanlib.report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HTMLReport {

	public static void produceReport(String htmlFile, StringBuffer content) throws IOException {
		FileWriter fw = new FileWriter(htmlFile);
		fw.write("<html>");
		fw.write("<head>");
		fw.write("<meta charset=\"utf-8\">" +
		"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
		"<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">"+
		"<link href=\"css/bootstrap-responsive.min.css\" rel=\"stylesheet\">"+
		"<link href=\"css/custom.css\" rel=\"stylesheet\">");
		fw.write("<script  type='text/javascript' src=\"js/report.js\"></script>");
		//fw.write(getCss());
		//fw.write(getJS());
		fw.write("</head>");
		fw.write("<body onload=\"addEvents();\">");
		
		fw.write(content.toString());
		
		fw.write("<script src=\"js/jquery.min.js\"></script>");
		fw.write("<script src=\"js/bootstrap.min.js\"></script>");
		fw.write("<script src=\"js/gumtree.js\"></script>");

		fw.write("<body></html>");
		fw.close();
	}
	
	@Deprecated
	public static String getCss() {
		return "<style type=\"text/css\">"+
		  "ul.LinkedList { display: block; font-size : small}"+
		 " ul.LinkedList ul { display: none; } "+
		 " a:link {color:#FF0000;}  "+
		 " a:visited {color:#00FF00;}  "+
		  "a:hover {color:#FF00FF;}  "+
		 " a:active {color:#0000FF;}  "+
		  ".HandCursorStyle { cursor: pointer; cursor: hand;color:#0000FF; } "+
		  "ul.LinkedList2 { display: block; font-size : small}"+
		  "ul.LinkedList2 ul { display: none; } "+
		  "ul.LinkedList3 { display: block; font-size : small}"+
		  "ul.LinkedList3 ul { display: none; } "+
		"</style>";
	}
	
	@Deprecated
	public static String getJS() {
		return "<script type=\"text/JavaScript\">" +
		  
		    "function addEvents() {"+
		    "  activateTree(document.getElementById(\"LinkedList1\"));"+
		   //"   activateTree(document.getElementById(\"LinkedList2\"));"+
		   //"   activateTree(document.getElementById(\"LinkedList3\"));"+
		    "}" +

		    "function activateTree(oList) {"+
		  
		     " for (var i=0; i < oList.getElementsByTagName(\"ul\").length; i++) {"+
		    "    oList.getElementsByTagName(\"ul\")[i].style.display=\"none\";         "   +
		    "  }                                                                  "+
		 
		   "   if (oList.addEventListener) {"+
		    "    oList.addEventListener(\"click\", toggleBranch, false);"+
		    "  } else if (oList.attachEvent) { "+
		    "    oList.attachEvent(\"onclick\", toggleBranch);"+
		    "  }"+
		   
		    "  addLinksToBranches(oList);"+
		    "}"+

		  
		   " function toggleBranch(event) {"+
		      "var oBranch, cSubBranches;"+
		     " if (event.target) {"+
		     "   oBranch = event.target;"+
		     " } else if (event.srcElement) { "+
		     "   oBranch = event.srcElement;"+
		      "}"+
		     " cSubBranches = oBranch.getElementsByTagName(\"ul\");"+
		    "  if (cSubBranches.length > 0) {"+
		     "   if (cSubBranches[0].style.display == \"block\") {"+
		     "     cSubBranches[0].style.display = \"none\";"+
		     "   } else {"+
		     "     cSubBranches[0].style.display = \"block\";"+
		     "  }"+
		     " }"+
		    "}"+

		   
		   " function addLinksToBranches(oList) {"+
		     " var cBranches = oList.getElementsByTagName(\"li\");"+
		     " var i, n, cSubBranches;"+
		    " if (cBranches.length > 0) {"+
		    "    for (i=0, n = cBranches.length; i < n; i++) {"+
		      "    cSubBranches = cBranches[i].getElementsByTagName(\"ul\");"+
		      "    if (cSubBranches.length > 0) {"+
		      "      addLinksToBranches(cSubBranches[0]);"+
		      "      cBranches[i].className = \"HandCursorStyle\";"+
		      "      cBranches[i].style.color = \"black\";"+
		       "     cSubBranches[0].style.color = \"black\";"+
		       "     cSubBranches[0].style.cursor = \"auto\";"+
		       "   }"+
		      "  }"+
		    "  }"+
		    "}"+
		    
		    "function HideContent(d) {" +
		    "	document.getElementById(d).style.display='none';"+
		    "}"+
		    "function ShowContent(d) {" +
		    "	document.getElementById(d).style.display='block';"+
		    "}"+
		    "function ReverseDisplay(d) {" +
		    "	if(document.getElementById(d).style.display == 'none') {document.getElementById(d).style.display='block';} "+
		    "else { document.getElementById(d).style.display == 'none' ;}" +
		    "}"+
		    
		    "function HideAllShowOne(d) {" +
		    "	var IDValues = 'Calls Overrides Extends Implements Overrides Annotation Fields'; " +
			" IDValues = IDValues.replace(/[,\\s\"\']/g,\" \"); "+
			" IDValues = IDValues.replace(/^\\s*/,\"\"); "+
			" IDValues = IDValues.replace(/\\s*$/,\"\"); "+
			" IDValues = IDValues.replace(/  +/g,\" \"); "+
			" var IDList = IDValues.split(\" \"); "+
			" for(var i=0 ; i < IDList.length ;i++) { HideContent(IDList[i]);}" +
			" ShowContent(d);"+
		    "}"+
		    ""+
		 " </script>";
	}

	public static void produceProjectUsage(String htmlFile,
			Map<String, Set<String>> projects_usage) throws IOException {
		FileWriter fw = new FileWriter(htmlFile);
		fw.write("<html>");
		fw.write("<head>");
		fw.write("<meta charset=\"utf-8\">" +
		"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
		"<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">"+
		"<link href=\"css/bootstrap-responsive.min.css\" rel=\"stylesheet\">"+
		"<link href=\"css/custom.css\" rel=\"stylesheet\">");
		fw.write(getCss());
		fw.write(getJS());
		fw.write("</head>");
		fw.write("<body>");
		fw.write("<table>");
		fw.write("<tr><td>Project</td><td>Nombre d'élements utilisés</td></tr>");
		List<Integer> sizes = new ArrayList<Integer>();
		for(String entry : projects_usage.keySet()) {
			if(!sizes.contains(projects_usage.get(entry).size()))
				sizes.add(projects_usage.get(entry).size());
		}
		Collections.sort(sizes);
		Collections.reverse(sizes);
		for(int size : sizes) {
			for(String entry : projects_usage.keySet()) {
				if(projects_usage.get(entry).size() == size) {
					fw.write("<tr><td>"+entry+"</td><td>"+size+"</td></tr>");
				}
			}
		}
		
		
		fw.write("</table>");
		fw.write("<body></html>");
		fw.close();
	}
		
}
