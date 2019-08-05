function addEvents() {  
	activateTree(document.getElementById("LinkedList1"));

}

function activateTree(oList) { 
	for (var i=0; i < oList.getElementsByTagName("ul").length; i++) {    
		oList.getElementsByTagName("ul")[i].style.display="none";           }                                                                     if (oList.addEventListener) {    oList.addEventListener("click", toggleBranch, false);  } else if (oList.attachEvent) {     oList.attachEvent("onclick", toggleBranch);  }  addLinksToBranches(oList);} 
function toggleBranch(event) {var oBranch, cSubBranches; if (event.target) {   oBranch = event.target; } else if (event.srcElement) {    oBranch = event.srcElement;} cSubBranches = oBranch.getElementsByTagName("ul");  if (cSubBranches.length > 0) {   if (cSubBranches[0].style.display == "block") {     cSubBranches[0].style.display = "none";   } else {     cSubBranches[0].style.display = "block";  } }} function addLinksToBranches(oList) { var cBranches = oList.getElementsByTagName("li"); var i, n, cSubBranches; if (cBranches.length > 0) {    for (i=0, n = cBranches.length; i < n; i++) {    cSubBranches = cBranches[i].getElementsByTagName("ul");    if (cSubBranches.length > 0) {      addLinksToBranches(cSubBranches[0]);      cBranches[i].className = "HandCursorStyle";      cBranches[i].style.color = "black";     cSubBranches[0].style.color = "black";     cSubBranches[0].style.cursor = "auto";   }  }  }}

function HideContent(d) {	
	document.getElementById(d).style.display='none';
}


function ShowContent(d) {	
	document.getElementById(d).style.display='block';
}

function ReverseDisplay(d) {
	if(document.getElementById(d).style.display == 'none') {
		document.getElementById(d).style.display='block';
	} else { 
		document.getElementById(d).style.display == 'none' ;}
	}

function HideAllShowOne(d) {	
	var IDValues = "Calls Overrides Extends Implements Overrides Annotation Fields Exception";  
	IDValues = IDValues.replace(/[,\s"']/g," ");  
	IDValues = IDValues.replace(/^\s*/,"");  
	IDValues = IDValues.replace(/\s*$/,""); 
	IDValues = IDValues.replace(/  +/g," ");  
	var IDList = IDValues.split(" ");  
	for(var i=0 ; i < IDList.length ;i++) { HideContent(IDList[i]);} 
	ShowContent(d);
}


function HideAll() {	
	var IDValues = "Calls Overrides Extends Implements Overrides Annotation Fields Exception";  
	IDValues = IDValues.replace(/[,\s"']/g," ");  
	IDValues = IDValues.replace(/^\s*/,"");  
	IDValues = IDValues.replace(/\s*$/,""); 
	IDValues = IDValues.replace(/  +/g," ");  
	var IDList = IDValues.split(" ");  
	for(var i=0 ; i < IDList.length ;i++) { 
		HideContent(IDList[i]);
	} 
}
