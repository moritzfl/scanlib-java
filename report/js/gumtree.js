if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.slice(0, str.length) == str;
  };
}

function getMappedElement(eltId) {
	if (eltId.startsWith("mapping-src")) {
		return eltId.replace("src","dst");  	 	
  	}
  	else {
  		return eltId.replace("dst","src");
  	}
}

function isSrc(eltId) {
	return eltId.startsWith("mapping-src");
}

$("span.token").popover({ 
	trigger: 'manual', animate: false, html: true
/*	template: '<div class="popover" onmousemove="$(this).hide();" onmouseover="$(this).mouseleave(function() {$(this).hide(); });"><div class="arrow"></div><div class="popover-inner"><h3 class="popover-title"></h3><div class="popover-content"><p></p></div></div></div>'*/
});
/*

$("span.token").popover({ trigger: 'manual' }).click(function(e){ 
	 e.preventDefault() ;
});

*/

$("span.token").popover({ trigger: 'manual' }).mouseenter(function(e){ 
	$(this).popover('show');
});

$("span.token").popover({ trigger: 'manual' }).mouseleave(function(e){ 
	$(this).popover('hide');
});



