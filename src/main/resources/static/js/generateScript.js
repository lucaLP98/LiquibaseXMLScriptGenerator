 /*
 * Set of function for send request to generate XML script and view it 
 */
 
 /*
 * AJAX function for send asynch request to generate a Create Schema XML Script and view it into text area
 */
function sendGeneratorScriptCreateSchemaRequest(){
	let formData = new FormData(document.getElementById("schemaForm"));
	
	let notEmpty = checkNotEmptyField("schemaForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.schema_name = formData.get("schema_name");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/createSchemaScript/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate a Create Table XML Script and view it into text area
 */
function sendGeneratorScriptCreateTableRequest(){
	let formData = new FormData(document.getElementById("tableForm"));
	let object = {};
	let columnArray = [];
	let columnRead = false;
	let notEmpty = true;
	
	for(let pair of formData.entries()) {			
		if(pair[1] == "" && notEmpty == true){
			if(pair[0] != "column_default")	{
				notEmpty = false
			}
		}
		if(pair[0] == "column_name" && notEmpty == true) {
			if(columnRead == false){
				columnRead = true;
			} 
			var column = {};
			columnArray.push(column);			
			column[pair[0]] = pair[1];	
		}else if(columnRead == true && notEmpty == true){
			column[pair[0]] = pair[1];
		}
	}
	
	if(notEmpty == true){
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.table_schema = formData.get("table_schema");
		object.table_name = formData.get("table_name");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
	
		object.pk_name = formData.get("pk_name");
		object.pk_type = formData.get("pk_type");
		
		
		object.columns = columnArray;
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/createTableScript/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
	
};

/*
 * Iterates on form elemnt to find an empty input field
 *
 * param formId : ID of form
 * Return value : true if there aren't empty fields / false else
 */
function checkNotEmptyField(formId){
	let formData = new FormData(document.getElementById(formId));
	let notEmpty = true;

	for (let value of formData.values()) {
  		if(notEmpty==true && value==""){
			notEmpty = false;
		}
	}
	
	return notEmpty;
}

/*
 * It allows, combined with the pressure of a button, the empty text of the TextArea 
 * present in the HTML page is present, if this is not.
 */	
function copyText(){
  	let copyText = document.getElementById("scriptTextArea");

	if(copyText.value != ""){
  		copyText.select();
  		copyText.setSelectionRange(0, 99999); /* For mobile device */
  		navigator.clipboard.writeText(copyText.value);
  		
  		alert("Script copy on clipboard!");
	}
}

/*
 * AJAX function for send asynch request to generate a Drop Table XML Script and view it into text area
 */
function sendGenerateScriptDropTableRequest(){
	let dropTableForm = document.getElementById("dropTableForm");
	let formData = new FormData(dropTableForm);
	
	let notEmptyFields = checkNotEmptyField("dropTableForm")
	if(notEmptyFields == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.table_schema = formData.get("table_schema");
		object.table_name = formData.get("table_name");
		object.cascade_constraint = formData.get("cascade_constraint");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/dropTableScript/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
	
}

/*
 * AJAX function for send asynch request to generate a Drop Column XML Script and view it into text area
 */
 function sendGenerateScriptDropColumnRequest(){
	let dropColumnForm = document.getElementById("dropColumnForm");
	let formData = new FormData(dropColumnForm);
	
	let notEmpty = checkNotEmptyField("dropColumnForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.table_schema = formData.get("table_schema");
		object.table_name = formData.get("table_name");
		object.column_name = formData.get("column_name");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/dropColumnScript/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}