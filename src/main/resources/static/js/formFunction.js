 /*
 * Set of function for load form for various types of scripts
 *
 * Author : Luca Pastore
 * Target : Alten Italia SpA
 * File : W3Function.js
 */

//Function that fills the schemaSelect whith all schema present in database - for each schema will create an option
function createOptionForSchemaSelect(selectName){
	const xhttp = new XMLHttpRequest();
	
	xhttp.onload = function() {
    	let select = document.getElementById(selectName);
    	let jsonResponse = JSON.parse(this.response);
    	let schemaArray = jsonResponse.schema_list;
    	for(let i=0; i<schemaArray.length; i++){
			let option = document.createElement("option");
			option.value = schemaArray[i].schema_name;
			option.innerHTML = schemaArray[i].schema_name;
			select.appendChild(option);

		}
   	}
  	xhttp.open("GET", "/api/schema/", true);
  	xhttp.send();
};

//load the page createTableForm.html in Homepage for the creation of "Create Table" Script
function loadTableForm(){
	$('#formContainer').load('forms/createTableForm.html');
	createOptionForSchemaSelect("tableSchemaSelect");
};