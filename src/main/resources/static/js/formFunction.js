 /*
 * Set of function for load form for various types of scripts
 */

/*
 * Function that fills the schema select whith all schema present in database - for each schema will create an option
 *
 * param selectId is the ID of the select element to which we want to add options
 */
function createOptionForSchemaSelect(selectId){
	const xhttp = new XMLHttpRequest();
	
	xhttp.onload = function() {
    	let select = document.getElementById(selectId);
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

/*
 * Function tha remove all option for the input Select element 
 *
 * param selectElement is the element and not its ID
 */
function removeOptions(selectElement) {
   var i, L = selectElement.options.length - 1;
   for(i = L; i >= 0; i--) {
      selectElement.remove(i);
   }
}

/*
 * load the page createTableForm.html in Homepage for the creation of "Create Table" Script
 */
function loadCreateTableForm(){
	$('#formContainer').load('forms/createTableForm.html');
	let schemaSelectId = "table_schema";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page dropTableForm.html in Homepage for the creation of "Drop Table" Script
 */
function loadDropTableForm(){
	$('#formContainer').load('forms/dropTableForm.html');
	let schemaSelectId = "table_schema";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page dropColumnForm.html in Homepage for the creation of "Drop Column" Script
 */
function loadDropColumnForm(){
	$('#formContainer').load('forms/dropColumnForm.html');
	let schemaSelectId = "table_schema";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * Function that fills the table select whith all DB table present in a specific database schema - for each table will create an option
 *
 * param schemaSelectId : is the ID of select which contains the list of DB schema tha we use to retrive the schema of tables to load into select
 * param tableSelectId : is the ID of select which will contain the list of DB tables for the selected DB schema
 * param columnSelectId : is the ID of select which will contain the list of DB Columns for the selected DB Table (may by null if there isn't a columnSelect in page)
 */
function loadTableOption(schemaSelectId, tableSelectId, columnSelectId){
	let schemaSelected = document.getElementById(schemaSelectId).value;

	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		let tableSelect = document.getElementById(tableSelectId);
		tableSelect.disabled = false;
    	removeOptions(tableSelect);
    	
    	let disabledOption = document.createElement("option");
    	disabledOption.selected = true;
    	disabledOption.value = "";
    	disabledOption.innerHTML = " -- select an option -- "
    	tableSelect.appendChild(disabledOption);  	
    	if(columnSelectId != null){
			let disabledOption2 = document.createElement("option");
    		disabledOption2.selected = true;
    		disabledOption2.value = "";
    		disabledOption2.innerHTML = " -- select an option -- "
    		let columnSelect = document.getElementById(columnSelectId);
    		columnSelect.appendChild(disabledOption2); 
		}
    	 	
    	let jsonResponse = JSON.parse(this.response);
    	let tableArray = jsonResponse.table_list;    	
    	for(let i=0; i<tableArray.length; i++){
			let option = document.createElement("option");
			option.value = tableArray[i].table_name;
			option.innerHTML = tableArray[i].table_name;
			tableSelect.appendChild(option); 
		}
   	}
   	
  	xhttp.open("GET", "/api/tables/bySchema/" + schemaSelected, true);
  	xhttp.send();
}

/*
 * Function that fills the columns select whith all DB columns present in a specific database Table - for each column will create an option
 *
 * param schemaSelectId : is the ID of select which contains the list of DB schema tha we use to retrive the schema of tables to load into select
 * param tableSelectId : is the ID of select which will contain the list of DB tables for the selected DB schema
 * param columnSelectId : is the ID of select which will contain the list of Columns for the selected DB Table (and Schema)
 */
function loadColumnOption(schemaSelectId, tableSelectId, columnSelectId){
	let schemaSelected = document.getElementById(schemaSelectId).value;
	let tableSelected = document.getElementById(tableSelectId).value;

	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		let columnSelect = document.getElementById(columnSelectId);
		columnSelect.disabled = false;
    	removeOptions(columnSelect);
    	
    	let disabledOption = document.createElement("option");
    	disabledOption.selected = true;
    	disabledOption.value = "";
    	disabledOption.innerHTML = " -- select an option -- "
    	columnSelect.appendChild(disabledOption);
    	
    	let jsonResponse = JSON.parse(this.response);
    	let columnArray = jsonResponse.column_list;
    	for(let i=0; i<columnArray.length; i++){
			let option = document.createElement("option");
			option.value = columnArray[i].column_name;
			option.innerHTML = columnArray[i].column_name;
			columnSelect.appendChild(option); 
		}
   	}
   	
  	xhttp.open("GET", "/api/columns/byTable/" + schemaSelected + "&" + tableSelected, true);
  	xhttp.send();
}

/*
 * Function for load in "createTableForm.html" the form for insert new column to add at table
 */
function addColumnToTable(){
  	$('#newCoulmnForm').load('forms/columnTableForm.html');
  	document.getElementById("newCoulmnForm").removeAttribute("id");
}