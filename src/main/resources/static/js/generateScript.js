function sendGeneratorScriptCreateTableRequest(){
	/*
	let myForm = document.getElementById("tableForm");
	let formData = new FormData(myForm);
	let object = {};
	let primaryKey = {};
	
	let columnArray = [];
	let column = {};
	let columnRead = false;
		
	for(let [name, value] of formData) {
		if(name == "columnName") {
			if(columnRead == false){
				columnRead = true;
			}else{
				columnArray.push(column);
			}
			column.name = value;	
			//alert(column.name);	
		}else if(columnRead == true){
			column.name = value;	
			//alert(column.name);	
		}
	}	
	
	object.idCHangeset = formData.get("idChangeSet");
	object.authorName = formData.get("authorName");
	object.tableSchema = formData.get("tableSchemaSelect");
	object.tableName = formData.get("tableName");
	
	primaryKey.primaryKeyName = formData.get("pkName");
	primaryKey.primaryKeyType = formData.get("pkType");
	primaryKey.primaryKeyColumn = formData.get("primaryKey");
	
	object.primaryKeyColumn = primaryKey;
	object.columns = columnArray;

	var json = JSON.stringify(object);
	
	alert(json);
	*/
};

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
	
	let object = {};
	object.id_changeset = formData.get("id_changeset");
	object.author = formData.get("author");
	object.table_schema = formData.get("table_schema");
	object.table_name = formData.get("table_name");
	object.cascade_constraint = formData.get("cascade_constraint");
	object.on_error = formData.get("on_error");
	object.on_fail = formData.get("on_fail");
	var json = JSON.stringify(object);
	
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
    	document.getElementById("scriptTextArea").innerHTML = this.responseText;
   	}
  	xhttp.open("POST", "/api/scriptGenerator/dropTableScript/", true);
  	xhttp.setRequestHeader("Content-type", "application/json");
  	xhttp.send(json);
}

/*
 * AJAX function for send asynch request to generate a Drop Column XML Script and view it into text area
 */
 function sendGenerateScriptDropColumnRequest(){
	let dropColumnForm = document.getElementById("dropColumnForm");
	let formData = new FormData(dropColumnForm);
	
	let object = {};
	object.id_changeset = formData.get("id_changeset");
	object.author = formData.get("author");
	object.table_schema = formData.get("table_schema");
	object.table_name = formData.get("table_name");
	object.column_name = formData.get("column_name");
	object.on_error = formData.get("on_error");
	object.on_fail = formData.get("on_fail");
	var json = JSON.stringify(object);
	
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
    	document.getElementById("scriptTextArea").innerHTML = this.responseText;
   	}
  	xhttp.open("POST", "/api/scriptGenerator/dropColumnScript/", true);
  	xhttp.setRequestHeader("Content-type", "application/json");
  	xhttp.send(json);
}