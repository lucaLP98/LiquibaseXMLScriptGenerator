/*
 * This file contain the javascript function for W3.CSS
 */

/*
 * Open Menu SideBar
 */
function w3_open() { document.getElementById("menuSidebar").style.display = "block"; }

/*
 * Close Menu SideBar
 */			
function w3_close() { document.getElementById("menuSidebar").style.display = "none"; }

/*
 * Opne the cascade sub menu for each item ov sidebar menu
 *
 * param idDrop: the id of dropListButton
 */	
function dropSubMenu(idDrop) {
  	var x = document.getElementById(idDrop);
  
  	if (x.className.indexOf("w3-show") == -1) {
    	x.className += " w3-show";
    	x.previousElementSibling.className += " w3-blue";
  	} else { 
    	x.className = x.className.replace(" w3-show", "");
    	x.previousElementSibling.className = 
    	x.previousElementSibling.className.replace(" w3-blue", "");
  	}
}

function clearTextArea(){
	document.getElementById("scriptTextArea").innerHTML = "";
}