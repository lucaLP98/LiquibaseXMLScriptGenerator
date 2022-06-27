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
		object.changeLog = formData.get("changeLog");
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
		if(pair[1] == "" && notEmpty == true && pair[0] != "column_default" && pair[0] != "data_length" && pair[0] != "pk_data_length"){
			notEmpty = false;
		}
		if(pair[0] == "column_name" && notEmpty == true) {
			if(columnRead == false){
				columnRead = true;
			} 
			var column = {};
			columnArray.push(column);			
			column[pair[0]] = pair[1];	
		}else if(columnRead == true && notEmpty == true){
			if(pair[0] == "data_length" && (column.column_type == "VARCHAR" || column.column_type == "CHAR")){		
				if(pair[1] == ""){
					notEmpty = false;
				}else{
					let dataLength = parseInt(pair[1]);
					if(dataLength > 255){
						dataLength = 255;
					}else if(dataLength < 1){
						dataLength = 1;
					}
					column.column_type += "("+dataLength+")";
				}
			}else{
				column[pair[0]] = pair[1];
			}
		}
	}
	
	if(formData.get("pk_type") == "VARCHAR" || formData.get("pk_type") == "CHAR"){
		if(formData.get("pk_data_length") == ""){
			notEmpty = false;
		}else{
			let dataLength = parseInt(formData.get("pk_data_length"));
			if(dataLength > 255){
				dataLength = 255;
			}else if(dataLength < 1){
				dataLength = 1;
			}
			object.pk_type = formData.get("pk_type")+"("+dataLength+")";
		}
	}else{
		object.pk_type = formData.get("pk_type")
	}
	
	if(notEmpty == true){
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.table_schema = formData.get("table_schema");
		object.table_name = formData.get("table_name");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		object.pk_name = formData.get("pk_name");
		
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

	for (let pair of formData.entries()) {
  		if(pair[0]!= "column_default" && pair[0]!= "where_condition" && pair[0]!= "data_length" && notEmpty==true && pair[1]==""){
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
		object.changeLog = formData.get("changeLog");
		object.table_name = formData.get("table_name");
		object.cascade_constraint = formData.get("cascade_constraint");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
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
	let formData = new FormData(document.getElementById("dropColumnForm"));
	
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
		object.changeLog = formData.get("changeLog");
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

/*
 * AJAX function for send asynch request to generate a Add Column XML Script and view it into text area
 */
function sendGeneratorScriptAddColumnRequest(){
	let formData = new FormData(document.getElementById("addColumnForm"));
	
	let notEmpty = checkNotEmptyField("addColumnForm");	
	let dataType;
	
	if(notEmpty == true && formData.get("data_length") == ""){
		notEmpty = false;
	}else{
		dataType = formData.get("column_type");
		
		if(formData.get("column_type") == "VARCHAR" || formData.get("column_type") == "CHAR"){
			let lenght = parseInt(formData.get("data_length"));
			if(lenght > 255){
				lenght = 255;
			}else if(lenght<1){
				lenght = 1
			}
			dataType += "("+lenght+")";
		}
	}

	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.schema_name = formData.get("schema_name");
		object.table_name = formData.get("table_name");
		object.column_name = formData.get("column_name");	
		object.column_default = formData.get("column_default");
		object.column_type = dataType;
		object.is_nullable = formData.get("is_nullable");
		object.is_unique = formData.get("is_unique");
		object.changeLog = formData.get("changeLog");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
   		
  		xhttp.open("POST", "/api/scriptGenerator/addColumnScript/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate a Drop Not Null Constraint XML Script and view it into text area
 */
function sendGenerateScriptDropNotNullConstraintRequest(){
	let formData = new FormData(document.getElementById("dropNotNullConstraintForm"));
	
	let notEmpty = checkNotEmptyField("dropNotNullConstraintForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.table_schema = formData.get("table_schema");
		object.table_name = formData.get("table_name");
		object.column_name = formData.get("column_name");
		object.column_type = formData.get("column_type");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/dropNotNullConstraintScript/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate a Add Not Null Constraint XML Script and view it into text area
 */
 function sendGenerateScriptAddNotNullConstraintRequest(){
	let formData = new FormData(document.getElementById("addNotNullConstraintForm"));
	
	let notEmpty = checkNotEmptyField("addNotNullConstraintForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.table_schema = formData.get("table_schema");
		object.table_name = formData.get("table_name");
		object.column_name = formData.get("column_name");
		object.column_type = formData.get("column_type");
		object.default_null_value = formData.get("default_null_value");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/addNotNullConstraintScript/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate a Add Unique Constraint XML Script and view it into text area
 */
function sendGenerateScriptAddUniqueConstraintRequest(){
	let formData = new FormData(document.getElementById("addUniqueConstraintForm"));
	
	let notEmpty = checkNotEmptyField("addUniqueConstraintForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.table_schema = formData.get("table_schema");
		object.table_name = formData.get("table_name");
		object.constraint_name = formData.get("constraint_name");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		
		let columnArray = [];
		let columnRead = false;
		for(let pair of formData.entries()) {
			if(pair[0] == "column_name") {
				if(columnRead == false){
					columnRead = true;
				} 
				var column = {};
				columnArray.push(column);			
				column[pair[0]] = pair[1];	
			}else if(columnRead == true){
				column[pair[0]] = pair[1];
			}
		}
		object.columns = columnArray;
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/addUniqueConstraintScript/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate a Drop Unique Constraint XML Script and view it into text area
 */
function sendGenerateScriptDropUniqueConstraintRequest(){
	let formData = new FormData(document.getElementById("dropUniqueConstraintForm"));
	
	let notEmpty = checkNotEmptyField("dropUniqueConstraintForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.table_schema = formData.get("table_schema");
		object.table_name = formData.get("table_name");
		object.constraint_name = formData.get("constraint_name");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/dropUniqueConstraintScript/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate a Rename Table XML Script and view it into text area
 */
function sendGeneratorScriptRenameTableRequest(){
	let formData = new FormData(document.getElementById("renameTableForm"));
	
	let notEmpty = checkNotEmptyField("renameTableForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.schema_name = formData.get("schema_name");
		object.old_table_name = formData.get("old_table_name");
		object.new_table_name = formData.get("new_table_name");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/renameTableScript/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate a Rename Column XML Script and view it into text area
 */
function sendGenerateScriptRenameColumnRequest(){
	let formData = new FormData(document.getElementById("renameColumnForm"));
	
	let notEmpty = checkNotEmptyField("renameColumnForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.schema_name = formData.get("schema_name");
		object.table_name = formData.get("table_name");
		object.old_column_name = formData.get("old_column_name");
		object.new_column_name = formData.get("new_column_name");
		object.column_type = formData.get("column_type");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/renameColumnScript/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate a Modify Column Data Type XML Script and view it into text area
 */
function sendGenerateScriptModifyDataTypeRequest(){
	let formData = new FormData(document.getElementById("modifyDataTypeForm"));
	
	let notEmpty = checkNotEmptyField("modifyDataTypeForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.schema_name = formData.get("schema_name");
		object.table_name = formData.get("table_name");
		object.column_name = formData.get("column_name");
		object.old_column_type = formData.get("old_column_type");
		object.new_column_type = formData.get("new_column_type");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/modifyColumnDataType/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate an Add Auto Increment to Column XML Script and view it into text area
 */
function sendGenerateScriptAddAutoIncrementRequest(){
	let formData = new FormData(document.getElementById("addAutoIncrementForm"));
	
	let notEmpty = checkNotEmptyField("addAutoIncrementForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.schema_name = formData.get("schema_name");
		object.table_name = formData.get("table_name");
		object.column_name = formData.get("column_name");
		object.start_with = formData.get("start_with");
		object.increment_by = formData.get("increment_by");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/addAutoIncrement/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate an Add Default Value XML Script and view it into text area
 */
function sendGenerateScriptAddDefaultValueRequest(){
	let formData = new FormData(document.getElementById("addDefaultValueForm"));
	
	let notEmpty = checkNotEmptyField("addDefaultValueForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.schema_name = formData.get("schema_name");
		object.table_name = formData.get("table_name");
		object.column_name = formData.get("column_name");
		object.default_value = formData.get("default_value");
		object.column_type = formData.get("column_type");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/addDefaultValue/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate an Drop Default Value XML Script and view it into text area
 */
function sendGenerateScriptDropDefaultValueRequest(){
	let formData = new FormData(document.getElementById("dropDefaultValueForm"));
	
	let notEmpty = checkNotEmptyField("dropDefaultValueForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.schema_name = formData.get("schema_name");
		object.table_name = formData.get("table_name");
		object.column_name = formData.get("column_name");
		object.column_type = formData.get("column_type");
		object.default_value = formData.get("default_value");
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/dropDefaultValue/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate an Add Foreign Key Constraint XML Script and view it into text area
 */
function sendGeneratorScriptAddForeignKeyConstraintRequest(){
	let formData = new FormData(document.getElementById("addForeignKeyConstraintForm"));
	
	let notEmpty = checkNotEmptyField("addForeignKeyConstraintForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		object.constraint_name = formData.get("constraint_name");
		object.on_delete = formData.get("on_delete");
		object.on_update = formData.get("on_update");
		
		object.base_table_schema_name = formData.get("base_table_schema_name");
		object.base_table_name = formData.get("base_table_name");
		object.base_column_name = formData.get("base_column_name");
		
		object.referenced_table_schema_name = formData.get("referenced_table_schema_name");
		object.referenced_table_name = formData.get("referenced_table_name");
		object.referenced_column_name = formData.get("referenced_column_name");
		
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/addForeignKeyConstraint/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate an Drop Foreign Key Constraint XML Script and view it into text area
 */
function sendGeneratorScriptDropForeignKeyConstraintRequest(){
	let formData = new FormData(document.getElementById("dropForeignKeyConstraintForm"));
	
	let notEmpty = checkNotEmptyField("dropForeignKeyConstraintForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		
		object.base_table_schema_name = formData.get("base_table_schema_name");
		object.base_table_name = formData.get("base_table_name");
		object.constraint_name = formData.get("constraint_name");
		
		object.on_error = formData.get("on_error");
		object.on_fail = formData.get("on_fail");
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/dropForeignKeyConstraint/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate an DELETE XML Script and view it into text area
 */
function sendGeneratorScriptDeleteRequest(){
	let formData = new FormData(document.getElementById("deleteDataForm"));
	
	let notEmpty = checkNotEmptyField("deleteDataForm");
	if(notEmpty == true){
		let object = {};
		object.id_changeset = formData.get("id_changeset");
		object.author = formData.get("author");
		
		object.schema_name = formData.get("schema_name");
		object.table_name = formData.get("table_name");
		object.where_condition = formData.get("where_condition");
		
		object.changeLog = formData.get("changeLog");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		document.getElementById("scriptTextArea").innerHTML = this.responseText;
   		}
  		xhttp.open("POST", "/api/scriptGenerator/deleteData/", true);
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alert(" WARNING\n Fill in all fields to generate the script");
	}
}

/*
 * AJAX function for send asynch request to generate an INSERT XML Script and view it into text area
 */
function sendGeneratorScriptInsertRequest(){
	let formData = new FormData(document.getElementById("insertDataForm"));
	let object = {};
	let columns = {};
	
	for(let pair of formData.entries()) {			
		if(pair[0] != "id_changeset" && pair[0] != "author" && 
		   pair[0] != "schema_name" && pair[0] != "table_name" && 
		   pair[0] != "on_error" && pair[0] != "on_fail" && 
		   pair[0] != "changeLog"){
			
		   columns[pair[0]] = pair[1];
		}	
	}
	
	object.id_changeset = formData.get("id_changeset");
	object.author = formData.get("author");
	object.schema_name = formData.get("schema_name");
	object.table_name = formData.get("table_name");
	object.on_error = formData.get("on_error");
	object.on_fail = formData.get("on_fail");
	object.changeLog = formData.get("changeLog");		
		
	object.columns = columns;
	let json = JSON.stringify(object);
	
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
    	document.getElementById("scriptTextArea").innerHTML = this.responseText;
   	}
  	xhttp.open("POST", "/api/scriptGenerator/insertData/", true);
  	xhttp.setRequestHeader("Content-type", "application/json");
  	xhttp.send(json);
}

/*
 * AJAX function for send asynch request to generate an UPDATE XML Script and view it into text area
 */
function sendGeneratorScriptUpdateRequest(){
	let formData = new FormData(document.getElementById("updateDataForm"));
	let object = {};
	let columns = {};
	
	for(let pair of formData.entries()) {	
		if(pair[0] != "id_changeset" && pair[0] != "author" && 
		   pair[0] != "schema_name" && pair[0] != "table_name" && 
		   pair[0] != "where_condition" && pair[0] != "changeLog"){
			
			columns[pair[0]] = pair[1];
		}	
	}
	
	object.id_changeset = formData.get("id_changeset");
	object.author = formData.get("author");
	object.schema_name = formData.get("schema_name");
	object.table_name = formData.get("table_name");
	object.where_condition = formData.get("where_condition");
	object.changeLog = formData.get("changeLog");		
		
	object.columns = columns;
	let json = JSON.stringify(object);
	
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
    	document.getElementById("scriptTextArea").innerHTML = this.responseText;
   	}
  	xhttp.open("POST", "/api/scriptGenerator/updateData/", true);
  	xhttp.setRequestHeader("Content-type", "application/json");
  	xhttp.send(json);
}