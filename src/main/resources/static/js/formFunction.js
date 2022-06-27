 /*
 * Set of function for load form for various types of scripts
 */

const COLUMN_ALL = 0;
const COLUMN_NULL = 1;
const COLUMN_NOT_NULL = 2;
const COLUMN_INT = 3;
const COLUMN_DEFAULT = 4;
const COLUMN_TYPE = 5;

const UNIQUE_CONSTRAINT = 6;
const FOREIGN_KEY_CONSTRAINT = 7;
const ALL_CONSTRAINT = 8;

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
 * load the page createSchemaForm.html in Homepage for the creation of "Create Schema" Script
 */
function loadCreateSchemaForm(){
	$('#formContainer').load('forms/createSchemaForm.html');
};


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
 * load the page addColumnForm.html in Homepage for the creation of "Add Column" Script
 */
function loadAddColumnForm(){
	$('#formContainer').load('forms/addColumnForm.html');
	let schemaSelectId = "schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page dropNotNullConstraintForm.html in Homepage for the creation of "Add Column" Script
 */
function loadDropNotNullConstraintForm(){
	$('#formContainer').load('forms/dropNotNullConstraintForm.html');
	let schemaSelectId = "table_schema";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page addNotNullConstraintForm.html in Homepage for the creation of "Add Column" Script
 */
function loadAddNotNullConstraintForm(){
	$('#formContainer').load('forms/addNotNullConstraintForm.html');
	let schemaSelectId = "table_schema";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page addUniqueConstraintForm.html in Homepage for the creation of "Add Unique Constraint" Script
 */
function loadAddUniqueConstraintForm(){
	$('#formContainer').load('forms/addUniqueConstraintForm.html');
	let schemaSelectId = "table_schema";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page dropUniqueConstraintForm.html in Homepage for the creation of "Drop Unique Constraint" Script
 */
function loadDropUniqueConstraintForm(){
	$('#formContainer').load('forms/dropUniqueConstraintForm.html');
	let schemaSelectId = "table_schema";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page renameTableForm.html in Homepage for the creation of "Rename Table" Script
 */
function loadRenameTableForm(){
	$('#formContainer').load('forms/renameTableForm.html');
	let schemaSelectId = "schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page renameColumnForm.html in Homepage for the creation of "Rename Column" Script
 */
function loadRenameColumnForm(){
	$('#formContainer').load('forms/renameColumnForm.html');
	let schemaSelectId = "schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page modifyDataTypeForm.html in Homepage for the creation of "Modify Column Data Type" Script
 */
function loadModifyColumnDataTypeForm(){
	$('#formContainer').load('forms/modifyDataTypeForm.html');
	let schemaSelectId = "schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page addAutoIncrementForm.html in Homepage for the creation of "Add auto increment to column" Script
 */
function loadAddAutoIncrementForm(){
	$('#formContainer').load('forms/addAutoIncrementForm.html');
	let schemaSelectId = "schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page addDefaultValue.html in Homepage for the creation of "Add default value" Script
 */
function loadAddDefaultValueForm(){
	$('#formContainer').load('forms/addDefaultValueForm.html');
	let schemaSelectId = "schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page dropDefaultValue.html in Homepage for the creation of "Drop default value" Script
 */
function loadDropDefaultValueForm(){
	$('#formContainer').load('forms/dropDefaultValueForm.html');
	let schemaSelectId = "schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page addForeignKeyConstraintForm.html in Homepage for the creation of "Add Foreign Key Constraint" Script
 */
function loadAddForeignKeyConstraintForm(){
	$('#formContainer').load('forms/addForeignKeyConstraintForm.html');
	let schemaSelectId = "base_table_schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page dropForeignKeyConstraintForm.html in Homepage for the creation of "Drop Foreign Key Constraint" Script
 */
function loadDropForeignKeyConstraintForm(){
	$('#formContainer').load('forms/dropForeignKeyConstraintForm.html');
	let schemaSelectId = "base_table_schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page deleteDataForm.html in Homepage for the creation of "DELETE" Script
 */
function loadDeleteDataForm(){
	$('#formContainer').load('forms/deleteDataForm.html');
	let schemaSelectId = "schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page insertDataForm.html in Homepage for the creation of "INSERT" Script
 */
function loadInsertDataForm(){
	$('#formContainer').load('forms/insertDataForm.html');
	let schemaSelectId = "schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * load the page updateDataForm.html in Homepage for the creation of "UPDATE" Script
 */
function loadUpdateDataForm(){
	$('#formContainer').load('forms/updateDataForm.html');
	let schemaSelectId = "schema_name";
	createOptionForSchemaSelect(schemaSelectId);
};

/*
 * Function for load in "createTableForm.html" the form for insert new column to add at table
 */
function addColumnToTable(){
  	$('#newCoulmnForm').load('forms/columnTableForm.html');
  	document.getElementById("newCoulmnForm").removeAttribute("id");
}

/*
 * Function for load in "addUniqueConstraintForm.html" the form for insert new column to add at the cobstraint
 */
function addColumnToUniqueConstraint(){
	$('#newUniqueCoulmnForm').load('forms/columnUniqueConstraintForm.html');
	document.getElementById("newUniqueCoulmnForm").removeAttribute("id");
}

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
 * Function tha enable the input field "Data Length" if selected Data Type is Varchar or Char
 * and disabled it else
 * 
 * param dataLengthInputFieldId : id of data lenght input field
 * param dataTypeInputFieldId : id of data type input field
 */
function enableDataLenghtInputField(dataLengthInputFieldId, dataTypeInputFieldId){	
	dataLenghInputField = document.getElementById(dataLengthInputFieldId);
	dataTypeInputField = document.getElementById(dataTypeInputFieldId);
	
	dataType = dataTypeInputField.value;
	dataLenghInputField.value = "";
	
	if(dataType == "VARCHAR" || dataType == "CHAR"){
		dataLenghInputField.disabled = false;
	}else{
		dataLenghInputField.disabled = true;
	}
}

/*
 * Function that fills the table select whith all DB table present in a specific database schema - for each table will create an option
 *
 * param schemaSelectId : is the ID of select which contains the list of DB schema tha we use to retrive the schema of tables to load into select
 * param tableSelectId : is the ID of select which will contain the list of DB tables for the selected DB schema
 * param columnSelectId : is the ID of select which will contain the list of DB Columns for the selected DB Table (may by null if there isn't a columnSelect in page)
 * param constraintTableSelectId : is the ID of select which will contain the list of constraint for the selecte table (may by null if there isn't a constraintSelect in page)
 */
function loadTableOption(schemaSelectId, tableSelectId, columnSelectId, constraintTableSelectId){
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
		if(constraintTableSelectId != null){
			let disabledOption3 = document.createElement("option");
    		disabledOption3.selected = true;
    		disabledOption3.value = "";
    		disabledOption3.innerHTML = " -- select an option -- "
    		let constraintSelect = document.getElementById(constraintTableSelectId);
    		constraintSelect.appendChild(disabledOption3); 
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
 * Function to load option in table's selects and fill references schema input field with base schema 
 * for the form addForeignKeyConstraint
 *
 * baseSchemaSelectId : the id of base schema select
 * referenceSchemaInputId :  the id of reference schema input text field
 * baseTableSelectId : the id of base table select
 * referencesTableSelectId : the id of references table select
 * baseColumnSelectId : the id of base column select
 * referencesColumnSelectId : the id of reference column select
 */ 
function loadTableSelectsForAddForeignKey(baseSchemaSelectId, referenceSchemaInputId, baseTableSelectId, referencesTableSelectId, baseColumnSelectId, referencesColumnSelectId){
	loadTableOption(baseSchemaSelectId, baseTableSelectId, baseColumnSelectId, null);
	loadTableOption(baseSchemaSelectId, referencesTableSelectId, referencesColumnSelectId, null);
	
	let schemaSelected = document.getElementById(baseSchemaSelectId);
	document.getElementById(referenceSchemaInputId).value =schemaSelected.value;
}

/*
 * Function that fills the columns select whith all DB columns present in a specific database Table - for each column will create an option
 *
 * param schemaSelectId : is the ID of select which contains the list of DB schema tha we use to retrive the schema of tables to load into select
 * param tableSelectId : is the ID of select which will contain the list of DB tables for the selected DB schema
 * param columnSelectId : is the ID of select which will contain the list of Columns for the selected DB Table (and Schema)
 * param retriveType : type of column to retrive. Possible value = {COLUMN_ALL, COLUMN_NULL, COLUMN_NOT_NULL, COLUMN_DEFAULT, COLUMN_INT}
 * param dataType : may by NULL. Indicates the data type of the columns to retrieve, not null only if retriveType = COLUMN_TYPE
 */
function loadColumnOption(schemaSelectId, tableSelectId, columnSelectId, retriveType, dataType){
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
   
   	let requestUri = "/api/columns/";
   	switch(retriveType){
		case COLUMN_ALL: requestUri += "byTable/"; break;
		case COLUMN_NULL: requestUri += "Null/"; break;
		case COLUMN_NOT_NULL: requestUri += "NotNull/"; break;
		case COLUMN_INT: requestUri += "Integer/"; break;
		case COLUMN_DEFAULT: requestUri += "WithDefaultValue/"; break;
		case COLUMN_TYPE: requestUri += "byDataType/"+dataType+"&"; break;
		default: requestUri += "byTable/"; break;
	}
	
  	xhttp.open("GET", requestUri + schemaSelected + "&" + tableSelected, true);
  	xhttp.send();
}

/*
 * Load the column type in a input text field
 *
 * param schemaInputId : the id of schema name input field, used to retrive the column's schema name
 * param tableInputId : the id of table name input field, used to retrive the column's table name
 * param columnInputId : the id of column name input field, used to retrive the column name
 */
function loadColumnTypeByColumnName(schemaInputId, tableInputId, columnInputId, columnTypeInputId){
	let schemaName = document.getElementById(schemaInputId).value;
	let tableName = document.getElementById(tableInputId).value;
	let columnName = document.getElementById(columnInputId).value;
	
	let columnTypeInpuField = document.getElementById(columnTypeInputId);
	
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		let jsonResponse = JSON.parse(this.response);
		columnTypeInpuField.value = jsonResponse.column_type;
   	}
   
  	xhttp.open("GET", "/api/columns/byName/"+schemaName+"&"+tableName+"&"+columnName, true);
  	xhttp.send();
}

/*
 * Fill a column select with all columns of a certain type by their membership table
 *
 * param baseColumnTypeInputId : the id of input field which contains the column data type
 * param ReferencedColumnSelectId : the id of select which will contain the columns found
 * param tableNameSelectId : the id of input field which contains the column's table name
 * param schemaNameSelectId : the id of input field which contains the column's schema name
 */
function loadReferenceColumnForeignKeyConstraint(baseColumnTypeInputId, ReferencedColumnSelectId, tableNameSelectId, schemaNameSelectId, referencedColumnTypeId){
	let baseColumnType = document.getElementById(baseColumnTypeInputId).value;
	let referencedColumnType = document.getElementById(referencedColumnTypeId);
	referencedColumnType.value = baseColumnType;
	
	loadColumnOption(schemaNameSelectId, tableNameSelectId, ReferencedColumnSelectId, COLUMN_TYPE, baseColumnType);
}

/*
 * Function that fills the constraint select whith all DB constraint present in a specific database Table - for each constraint will create an option
 *
 * param schemaSelectId : is the ID of select which contains the list of DB schema tha we use to retrive the schema of tables to load into select
 * param tableSelectId : is the ID of select which will contain the list of DB tables for the selected DB schema
 * param constraintSelectId : is the ID of select which will contain the list of Constraints for the selected DB Table (and Schema)
 * param constraintType : type of constraint to retrive. Possible value = {ALL_CONSTRAINT, UNIQUE_CONSTRAINT}
 */
function loadConstraintOption(schemaSelectId, tableSelectId, constraintSelectId, constraintType){
	let schemaSelected = document.getElementById(schemaSelectId).value;
	let tableSelected = document.getElementById(tableSelectId).value;

	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		let constraintSelect = document.getElementById(constraintSelectId);
		constraintSelect.disabled = false;
    	removeOptions(constraintSelect);
    	
    	let disabledOption = document.createElement("option");
    	disabledOption.selected = true;
    	disabledOption.value = "";
    	disabledOption.innerHTML = " -- select an option -- "
    	constraintSelect.appendChild(disabledOption);
    	
    	let jsonResponse = JSON.parse(this.response);
    	let constraintArray = jsonResponse.constraint_list;
    	for(let i=0; i<constraintArray.length; i++){
			let option = document.createElement("option");
			option.value = constraintArray[i].constraint_name;
			option.innerHTML = constraintArray[i].constraint_name;
			constraintSelect.appendChild(option); 
		}
   	}
   
   	let requestUri = "/api/constraint/";
   	switch(constraintType){
		case UNIQUE_CONSTRAINT: requestUri += "unique/"; break;
		case ALL_CONSTRAINT: requestUri += "all/"; break;
		case FOREIGN_KEY_CONSTRAINT: requestUri += "foreignkey/"; break;
		default: requestUri += "all/"; break;
	}
	
  	xhttp.open("GET", requestUri + schemaSelected + "&" + tableSelected, true);
  	xhttp.send();
}

/*
 * Function that find the data type of selected column
 *
 * param columnTypeInputFieldId : the ID of column data type input text field
 * param columnSelectId : the ID of select that contains the selected column
 * param tableSelectId : the ID of select that contains the selected table's colum
 * param schemaSelectId : the ID of select that contains the selected schema that contain table's colum
 */
function loadColumnType(columnTypeInputFieldId, columnSelectId, tableSelectId, schemaSelectId){
	let selectedColumn = document.getElementById(columnSelectId).value;
	let selectedTable = document.getElementById(tableSelectId).value;
	let selectedSchema = document.getElementById(schemaSelectId).value;
	
	let columnTypeInputField = document.getElementById(columnTypeInputFieldId);
	columnTypeInputField.value = "";
	
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function(){
		let jsonResponse = JSON.parse(this.response);
		columnTypeInputField.value = jsonResponse.column_type;
	}
	xhttp.open("GET", "/api/columns/byName/" + selectedSchema + "&" + selectedTable + "&" + selectedColumn, true);
  	xhttp.send();
}

/*
 * Function that find the data type of selected column
 *
 * param columnTypeInputFieldId : the ID of column data type input text field
 * param columnSelectId : the ID of select that contains the selected column
 * param tableSelectId : the ID of select that contains the selected table's colum
 * param schemaSelectId : the ID of select that contains the selected schema that contain table's colum
 * param columnDefaultInputFieldId : the ID of column default value input text field
 */
function loadColumnTypeAndDefaultValue(columnTypeInputFieldId, columnDefaultInputFieldId, columnSelectId, tableSelectId, schemaSelectId){
	let selectedColumn = document.getElementById(columnSelectId).value;
	let selectedTable = document.getElementById(tableSelectId).value;
	let selectedSchema = document.getElementById(schemaSelectId).value;
	
	let columnTypeInputField = document.getElementById(columnTypeInputFieldId);
	columnTypeInputField.value = "";
	let columnDefaultInputField = document.getElementById(columnDefaultInputFieldId);
	columnDefaultInputField.value = "";
	
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function(){
		let jsonResponse = JSON.parse(this.response);
		columnTypeInputField.value = jsonResponse.column_type;
		columnDefaultInputField.value = jsonResponse.column_default;
	}
	xhttp.open("GET", "/api/columns/byName/" + selectedSchema + "&" + selectedTable + "&" + selectedColumn, true);
  	xhttp.send();
}

/*
 * Function that find the data type of selected column and modify input field type 
 *
 * param columnTypeInputFieldId : the ID of column data type input text field
 * param columnSelectId : the ID of select that contains the selected column
 * param tableSelectId : the ID of select that contains the selected table's colum
 * param schemaSelectId : the ID of select that contains the selected schema that contain table's colum
 * param inputFieldId : the ID of the input to field to which we want to change the type
 */
function loadColumnTypeAndChangeInputFieldType(columnTypeInputFieldId, columnSelectId, tableSelectId, schemaSelectId, inputFieldId){
	let selectedColumn = document.getElementById(columnSelectId).value;
	let selectedTable = document.getElementById(tableSelectId).value;
	let selectedSchema = document.getElementById(schemaSelectId).value;
	let inputField = document.getElementById(inputFieldId);
	
	let columnTypeInputField = document.getElementById(columnTypeInputFieldId);
	columnTypeInputField.value = "";
	inputField.value="";
	
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function(){
		let jsonResponse = JSON.parse(this.response);
		columnTypeInputField.value = jsonResponse.column_type;
		
		switch(jsonResponse.column_type){
			case "int": case  "bigint" : case "tinyint" : case "mediumint" : case "numeric" : case "float" : case "double" : case "decimal" : inputField.type = "number"; break;			
			case "date" : inputField.type = "date"; break;
			case "time" : case "timestamp" : inputField.type = "time"; break;
			default: inputField.type = "text"; break;
		}
	}
	xhttp.open("GET", "/api/columns/byName/" + selectedSchema + "&" + selectedTable + "&" + selectedColumn, true);
  	xhttp.send();
} 

function loadTableOptionInsertUpdateQuery(schemaSelectId, tableSelectId, divInputFieldsId){
	loadTableOption(schemaSelectId, tableSelectId, null);
	let inputDiv = document.getElementById(divInputFieldsId);
	inputDiv.innerHTML = " ";
}

/*
 * function to add input fields to columns dynamically based on the number and type of columns in the selected table
 *
 * param schemaSelectId : the ID of select for the selection of DB schemas
 * param tableSelectId : the ID of select for the selection of DB tables
 * param divInputFieldsId : the id of the div where the column fields will be added
 */
function addInputColumnIntoHTMLPage(schemaSelectId, tableSelectId, divInputFieldsId){
	let selectedTable = document.getElementById(tableSelectId).value;
	let selectedSchema = document.getElementById(schemaSelectId).value;
	let inputDiv = document.getElementById(divInputFieldsId);
	
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function(){
		let jsonResponse = JSON.parse(this.response);
		let columnArray = jsonResponse.column_list;
		
		let inputField = '';
		
		for(let i=0;i<columnArray.length;i++){
			let type = "";
			switch(columnArray[i].column_type){
				case "int": case  "bigint" : case "tinyint" : case "mediumint" : case "numeric" : case "float" : case "double" : case "decimal" : type = "number"; break;
				case "date" : type = "date"; break;	
				case "time" : case "timestamp" : type = "time"; break;
				default: type = "text"; break;
			}
			
			inputField += '<div class=\"form-group\"><label class=\"col-sm-2 control-label\">'+columnArray[i].column_name+'</label>';
			inputField += '<div class=\"col-sm-10\">';
			inputField += '<input type=\"'+type+'\" class=\"form-control\" id=\"'+columnArray[i].column_name+'\" name=\"'+columnArray[i].column_name+'\" placeholder=\"'+columnArray[i].column_name+'\" ></div></div>';
		}
		
		inputDiv.innerHTML = inputField;
	}
	xhttp.open("GET", "/api/columns/byTable/" + selectedSchema + "&" + selectedTable, true);
  	xhttp.send();
}