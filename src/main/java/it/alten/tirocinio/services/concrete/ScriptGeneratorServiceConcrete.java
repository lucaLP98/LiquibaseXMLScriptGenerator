package it.alten.tirocinio.services.concrete;

import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import it.alten.tirocinio.api.DTO.scriptDTO.CreateTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DeleteDataScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropDefaultValueScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropForeignKeyConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropNotNullConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropUniqueConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.InsertDataScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.ModifyColumnDataTypeScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.RenameColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.RenameTableScriptDTO;
import it.alten.tirocinio.DAO.GenericEntityDAO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddAutoIncrementScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddDefaultValueScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddForeignKeyConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddNotNullConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.AddUniqueConstraintScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.CreateSchemaScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.ScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.UpdateDataScriptDTO;
import it.alten.tirocinio.liquibaseChangeElement.ChangeLog;
import it.alten.tirocinio.liquibaseChangeElement.ChangeSet;
import it.alten.tirocinio.model.ColumnMetadata;
import it.alten.tirocinio.model.KeyColumnMetadata;
import it.alten.tirocinio.model.TableMetadata;

import it.alten.tirocinio.repository.ColumnMetadataRepository;
import it.alten.tirocinio.repository.KeyColumnMetadataRepository;
import it.alten.tirocinio.repository.TableMetadataRepository;
import it.alten.tirocinio.services.ScriptGeneratorService;

/*
 * Service implementation of ScriptGeneratorService interface
 */
@Service
public class ScriptGeneratorServiceConcrete implements ScriptGeneratorService {
	private final TableMetadataRepository tableMetadataRepository;
	private final ColumnMetadataRepository columnMetadataRepository;
	private final KeyColumnMetadataRepository keyColumnMetadataRepository;
	private final GenericEntityDAO genericEntityDAO;
	@Autowired
	private ApplicationContext context;
	
	/* 
	 * Contructors
	 */
	public ScriptGeneratorServiceConcrete(TableMetadataRepository tableMetadataRepository, 
										  ColumnMetadataRepository columnMetadataRepository, 
										  KeyColumnMetadataRepository keyColumnMetadataRepository, 
										  GenericEntityDAO genericEntityDAO) {
		this.tableMetadataRepository = tableMetadataRepository;
		this.columnMetadataRepository = columnMetadataRepository;
		this.keyColumnMetadataRepository = keyColumnMetadataRepository;
		this.genericEntityDAO = genericEntityDAO;
	}
	
	/*
	 * Create the changeLog element
	 */
	private Element createChangeLog(Document document) {
		//create ChangeLog element
		Element changeLog =  document.createElement("databaseChangeLog");
		
		//add changeLog's attributes
		Attr xmlns = document.createAttribute("xmlns");
		xmlns.setValue("http://www.liquibase.org/xml/ns/dbchangelog");
		changeLog.setAttributeNode(xmlns);
		
		Attr xsi = document.createAttribute("xmlns:xsi");
		xsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
		changeLog.setAttributeNode(xsi);
		
		Attr pro = document.createAttribute("xmlns:pro");
		pro.setValue("http://www.liquibase.org/xml/ns/pro");
		changeLog.setAttributeNode(pro);
		
		Attr schemaLocation = document.createAttribute("xsi:schemaLocation");
		schemaLocation.setValue("http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.6.xsd\r\n http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-4.6.xsd");
		changeLog.setAttributeNode(schemaLocation);
		
		return changeLog;
	}
	
	/*
	 * Create the preCondition Element with its attributes
	 */
	private Element createPreConditionElement(Document document, ScriptDTO scriptDTO) {
		//create preConditionElement
		Element preConditionElement = document.createElement("preConditions");
		
		//add preCondition attribute
    	Attr onErrorAtribute = document.createAttribute("onError");
    	onErrorAtribute.setValue(scriptDTO.getOnError());
    	preConditionElement.setAttributeNode(onErrorAtribute);
    	
    	Attr onFailAtribute = document.createAttribute("onFail");
    	onFailAtribute.setValue(scriptDTO.getOnFail());
    	preConditionElement.setAttributeNode(onFailAtribute);
    	
    	Attr onSqlOutputAtribute = document.createAttribute("onSqlOutput");
    	onSqlOutputAtribute.setValue("TEST");
    	preConditionElement.setAttributeNode(onSqlOutputAtribute);
    	
    	return preConditionElement;
	}
	
	/*
	 * Generate a XML Script in a String format
	 */
	private String generateXMLScriptToString(Document document) {
		String XMLScript = "";
		
		try {
			/*
        	 *  creation of the xml script
        	 *  transform the DOM Object to an XML script
        	 */
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            //indentation for the XML script
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            
            DOMSource domSource = new DOMSource(document);
            
            //creating a String with the script XML
            StreamResult streamResultString = new StreamResult(new StringWriter());
            transformer.transform(domSource, streamResultString);
            XMLScript = streamResultString.getWriter().toString(); //it contain the XML script in format of String          
            /*
             * End script generation
             */
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
		
		return XMLScript;
	}
	
	/*
	 * Create a ChangeSet element
	 */
	private Element createChangeSetElement(Document document, ScriptDTO scriptDTO) {
		//changeSet element
        Element changeSet = document.createElement("changeSet");
        
        //add changeSet attribute
    	Attr changeSetAuthor = document.createAttribute("author");
    	changeSetAuthor.setValue(scriptDTO.getAuthor());
    	changeSet.setAttributeNode(changeSetAuthor);
    	
    	Attr changeSetId = document.createAttribute("id");
    	changeSetId.setValue(scriptDTO.getIdChangeset());
    	changeSet.setAttributeNode(changeSetId);
    	
    	return changeSet;
	}
	
	/*
	 * Method that allows you to manage the ChangeSet. It allows you to add to the general ChangeLog or to a local one, according to the specific requests
	 */
	private void manageChangeSet(ScriptDTO scriptDTO, Document document, Element changeSet) {
		ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
		
		//changeSet element
        if(scriptDTO.getChangeLog()) {
        	//create changeLog element
        	Element changeLogElement = createChangeLog(document);
        	//append ChangeLog to Document
        	document.appendChild(changeLogElement);
        	
        	//append changeSet element to changeLog
        	changeLogElement.appendChild(changeSet);
        }else if(scriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()){
        	ChangeSet changeSetToAdd = new ChangeSet(document, scriptDTO.getIdChangeset());
        	changeSetToAdd.setChangeSet(changeSet);
        	changeLog.addChangeSetToChangeLog(changeSetToAdd);
        }else {
        	//append changeSet element to document
	        document.appendChild(changeSet);
        }
	}
	
	/*
	 * Create a Column XML element to append at parent element
	 */
	private Element newColumnScript(Document document, ColumnMetadata column) {
		//add column element to addColumn
    	Element columnRollback = document.createElement("column");
    	
    	//add column attribute
    	Attr columnNameRolback = document.createAttribute("name");
    	columnNameRolback.setValue(column.getColumnName());
    	columnRollback.setAttributeNode(columnNameRolback); 
    	
    	Attr columnTypeRolback = document.createAttribute("type");
    	columnTypeRolback.setValue(column.getColumnType());
    	columnRollback.setAttributeNode(columnTypeRolback); 
    	
    	//add constraint element to column
    	Element constraintRollback = document.createElement("constraints");
    	columnRollback.appendChild(constraintRollback);
    	
    	//add constraint attribute
    	Attr nullableRolback = document.createAttribute("nullable");     
    	if(column.getIsNullable().equals("YES"))	nullableRolback.setValue("true");
    	else	nullableRolback.setValue("false");
    	constraintRollback.setAttributeNode(nullableRolback); 
    	
    	Attr uniqueNameAttribiteConstraint = document.createAttribute("uniqueConstraintName");
		uniqueNameAttribiteConstraint.setValue("Unique_constraint_"+column.getTableSchema()+"_"+column.getTableName()+"_"+column.getColumnName());
		constraintRollback.setAttributeNode(uniqueNameAttribiteConstraint);
    	
    	if(column.getColumnKey().equals("PRI")) {
    		Attr primaryKeyRolback = document.createAttribute("primaryKey");     
    		primaryKeyRolback.setValue("true");
    		constraintRollback.setAttributeNode(primaryKeyRolback); 
    		
    		Attr prkmaryKeyNameAttribiteConstraint = document.createAttribute("primaryKeyName");
        	prkmaryKeyNameAttribiteConstraint.setValue("Pk_"+column.getTableSchema()+"_"+column.getTableName());
        	constraintRollback.setAttributeNode(prkmaryKeyNameAttribiteConstraint);
    	}
    	
    	return columnRollback;
	}
	
	/*
	 * Create Element ChangeSet for Drop Table Script
	 */
	private Element generateDropTable(Document document, DropTableScriptDTO dropTableScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, dropTableScriptDTO);
    	
        /*
         * add preCondition element
         */
    	Element preConditionElement = createPreConditionElement(document, dropTableScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
    	
    	//add preCondition child
    	Element tableExistsElement = document.createElement("tableExists");
    	preConditionElement.appendChild(tableExistsElement);
    	
    	Attr schemaNamePreCond = document.createAttribute("schemaName");
    	schemaNamePreCond.setValue(dropTableScriptDTO.getTableSchema());
    	tableExistsElement.setAttributeNode(schemaNamePreCond);
    	
    	Attr tableNamePreCOnd = document.createAttribute("tableName");
    	tableNamePreCOnd.setValue(dropTableScriptDTO.getTableName());
    	tableExistsElement.setAttributeNode(tableNamePreCOnd);    	
    	
    	/*
    	 * add dropTable element
    	 */
    	Element dropTableElement = document.createElement("dropTable");
    	//add DropTable element to changeSet element
    	changeSet.appendChild(dropTableElement);
    	
    	//add dropTable attribute
    	Attr cascadeConstraints = document.createAttribute("cascadeConstraints");
    	cascadeConstraints.setValue(dropTableScriptDTO.getCascadeConstraint());
    	dropTableElement.setAttributeNode(cascadeConstraints);
    	
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(dropTableScriptDTO.getTableSchema());
    	dropTableElement.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(dropTableScriptDTO.getTableName());
    	dropTableElement.setAttributeNode(tableName);      
    	
    	/*
    	 * Generate RollBack
    	 */
    	TableMetadata table = tableMetadataRepository.getDBTablesByNameAndSchema(dropTableScriptDTO.getTableSchema(), dropTableScriptDTO.getTableName());
    	Set<ColumnMetadata> columns = columnMetadataRepository.getAllDBColumnsByTableAndSchema(dropTableScriptDTO.getTableSchema(), dropTableScriptDTO.getTableName());
    	//create rollback element
    	Element dropTableRollBack = document.createElement("rollback");
    	changeSet.appendChild(dropTableRollBack);
    	
    	//create createTable element
    	Element createTableRollBack = document.createElement("createTable");
    	dropTableRollBack.appendChild(createTableRollBack);
    	
    	Attr tableNameRollback = document.createAttribute("tableName");
    	tableNameRollback.setValue(table.getTableName());
    	createTableRollBack.setAttributeNode(tableNameRollback);   
    	
    	Attr schemaNameRollback = document.createAttribute("schemaName");
    	schemaNameRollback.setValue(table.getTableSchema());
    	createTableRollBack.setAttributeNode(schemaNameRollback);   
    	
    	//add column elements to createTableRollback element
    	for(ColumnMetadata c : columns) {
    		createTableRollBack.appendChild(newColumnScript(document, c));
    	}
    	
    	//add foreign key constraint
    	Set<KeyColumnMetadata> keyColumnsMetadata = keyColumnMetadataRepository.getKeyColumnForeignKeyConstraintsByTableAndSchema(dropTableScriptDTO.getTableName(), dropTableScriptDTO.getTableSchema());
    	for(KeyColumnMetadata c : keyColumnsMetadata) {
			Element addForeignKeyConstraintRollBack = document.createElement("addForeignKeyConstraint");
        	dropTableRollBack.appendChild(addForeignKeyConstraintRollBack);
        	
        	Attr baseSchemaNameRollback = document.createAttribute("baseTableSchemaName");
        	baseSchemaNameRollback.setValue(c.getBaseTableSchema());
        	addForeignKeyConstraintRollBack.setAttributeNode(baseSchemaNameRollback);
        	
        	Attr baseTableNameRollback = document.createAttribute("baseTableName");
        	baseTableNameRollback.setValue(c.getBaseTableName());
        	addForeignKeyConstraintRollBack.setAttributeNode(baseTableNameRollback);
        	
        	Attr foreignKeyConstraintNameRollback = document.createAttribute("constraintName");
        	foreignKeyConstraintNameRollback.setValue(c.getConstraintName());
        	addForeignKeyConstraintRollBack.setAttributeNode(foreignKeyConstraintNameRollback);
        	
        	Attr referencedColumnNameRollback = document.createAttribute("referencedColumnNames");
        	referencedColumnNameRollback.setValue(c.getReferencedColumnName());
        	addForeignKeyConstraintRollBack.setAttributeNode(referencedColumnNameRollback);
        	
        	Attr referencedTableNameRollback = document.createAttribute("referencedTableName");
        	referencedTableNameRollback.setValue(c.getReferencedTableName());
        	addForeignKeyConstraintRollBack.setAttributeNode(referencedTableNameRollback);
        	
        	Attr referencedTableSchemaNameRollback = document.createAttribute("referencedTableSchemaName");
        	referencedTableSchemaNameRollback.setValue(c.getReferencedTableSchema());
        	addForeignKeyConstraintRollBack.setAttributeNode(referencedTableSchemaNameRollback);
        	
        	Attr onDeleteRollback = document.createAttribute("onDelete");
        	onDeleteRollback.setValue(c.getOnDeleteClause());
        	addForeignKeyConstraintRollBack.setAttributeNode(onDeleteRollback);
        	
        	Attr onUpdateRollback = document.createAttribute("onUpdate");
        	onUpdateRollback.setValue(c.getOnUpdateClause());
        	addForeignKeyConstraintRollBack.setAttributeNode(onUpdateRollback);
    	}
    	
    	//insert value for the deleted table
    	String rollBackQuery = "SELECT * FROM " + dropTableScriptDTO.getTableSchema() + "." + dropTableScriptDTO.getTableName();
    	Set<Map<String, String>> resultSetRollBack = genericEntityDAO.selectQuery(rollBackQuery);
    	if(resultSetRollBack != null) {
	    	for(Map<String, String> c : resultSetRollBack) {    	
	    		Element insertElement = document.createElement("insert");
		    	//append INSERT to rollback
	    		dropTableRollBack.appendChild(insertElement);
	    		
	    		//INSERT element attributes
		    	Attr schemaNameRollBack = document.createAttribute("schemaName");
		    	schemaNameRollBack.setValue(dropTableScriptDTO.getTableSchema());
		    	insertElement.setAttributeNode(schemaNameRollBack);
		    	
		    	Attr tableNameRollBack = document.createAttribute("tableName");
		    	tableNameRollBack.setValue(dropTableScriptDTO.getTableName());
		    	insertElement.setAttributeNode(tableNameRollBack);
	    		
	    		for(String s : c.keySet()) {
	    			if(c.get(s) != null) {
	    				Element columnElement = document.createElement("column");
		        		insertElement.appendChild(columnElement);
		        		
		        		Attr columnName = document.createAttribute("name");
		        		columnName.setValue(s);
		        		columnElement.setAttributeNode(columnName);
		        		
		        		columnElement.appendChild(document.createTextNode(c.get(s)));
	    			}
	    		}
	    	}
    	}
    	
		return changeSet;
	}
	
	/*
	 * Method for generate a Drop Table Script
	 */
	@Override
	public String generateDropTableLiquibaseXMLScript(DropTableScriptDTO dropTableScriptDTO) {
		String dropTableXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(dropTableScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateDropTable(document, dropTableScriptDTO);
	        manageChangeSet(dropTableScriptDTO,  document, changeSet);
	        
        	/*
        	 * generate XML script
        	 */
        	dropTableXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
		
		return dropTableXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Drop Column Script
	 */
	private Element generateDropColumnChangeSet(Document document, DropColumnScriptDTO dropColumnScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, dropColumnScriptDTO);
    	
        /*
         * add preCondition element
         */
    	Element preConditionElement = createPreConditionElement(document, dropColumnScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
    	
    	//add preCondition child
    	Element columnExistsElement = document.createElement("columnExists");
    	preConditionElement.appendChild(columnExistsElement);
   	
    	Attr schemaNamePreCond = document.createAttribute("schemaName");
    	schemaNamePreCond.setValue(dropColumnScriptDTO.getTableSchema());
    	columnExistsElement.setAttributeNode(schemaNamePreCond);
    	
    	Attr tableNamePreCond = document.createAttribute("tableName");
    	tableNamePreCond.setValue(dropColumnScriptDTO.getTableName());
    	columnExistsElement.setAttributeNode(tableNamePreCond); 
    	
    	Attr columnNamePreCond = document.createAttribute("columnName");
    	columnNamePreCond.setValue(dropColumnScriptDTO.getColumnName());
    	columnExistsElement.setAttributeNode(columnNamePreCond); 
   	
    	/*
    	 * add dropColumn element
    	 */
    	Element dropColumnElement = document.createElement("dropTable");
    	//add dropColumn element to changeSet element
    	changeSet.appendChild(dropColumnElement);
    	
    	//add dropColumn attribute
    	Attr columnName = document.createAttribute("columnName");
    	columnName.setValue(dropColumnScriptDTO.getColumnName());
    	dropColumnElement.setAttributeNode(columnName);
    	
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(dropColumnScriptDTO.getTableSchema());
    	dropColumnElement.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(dropColumnScriptDTO.getTableName());
    	dropColumnElement.setAttributeNode(tableName); 
    	
    	/*
    	 * Generate RollBack
    	 */
    	ColumnMetadata column = columnMetadataRepository.getDBColumnByNameAndTableAndSchema(dropColumnScriptDTO.getTableSchema(), dropColumnScriptDTO.getTableName(), dropColumnScriptDTO.getColumnName());
    	//create rollback element
    	Element dropColumnRollBack = document.createElement("rollback");
    	changeSet.appendChild(dropColumnRollBack);
    	
    	//create element addColumn
    	Element addColumnRollback = document.createElement("addColumn");
    	dropColumnRollBack.appendChild(addColumnRollback);
    	
    	//add addColumn attribute
    	Attr tableNameRollback = document.createAttribute("tableName");
    	tableNameRollback.setValue(column.getTableName());
    	addColumnRollback.setAttributeNode(tableNameRollback); 
    	
    	Attr tableSchemaRollback = document.createAttribute("schemaName");
    	tableSchemaRollback.setValue(column.getTableSchema());
    	addColumnRollback.setAttributeNode(tableSchemaRollback); 
    	
    	//add column element to addColumn;
    	addColumnRollback.appendChild(newColumnScript(document, column));
    	
    	return changeSet;
	}
	
	/*
	 * Method for generate a Drop Column Script
	 */
	@Override
	public String generateDropColumnLiquibaseXMLScript(DropColumnScriptDTO dropColumnScriptDTO) {
		String dropColumnXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(dropColumnScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateDropColumnChangeSet(document, dropColumnScriptDTO);
	        manageChangeSet(dropColumnScriptDTO,  document, changeSet);
			
        	/*
        	 * generate XML script
        	 */
        	dropColumnXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
		
		return dropColumnXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Create Table Script
	 */
	private Element generateCreateTableChangeSet(Document document, CreateTableScriptDTO createTableScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, createTableScriptDTO);
    	
        /*
         * add preCondition element
         */
    	Element preConditionElement = createPreConditionElement(document, createTableScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
    	
    	//add preCondition child
    	Element notExists = document.createElement("not");
    	preConditionElement.appendChild(notExists);
    	
    	Element tableExistsElement = document.createElement("tableExists");
    	notExists.appendChild(tableExistsElement);
   	
    	Attr schemaNamePreCond = document.createAttribute("schemaName");
    	schemaNamePreCond.setValue(createTableScriptDTO.getTableSchema());
    	tableExistsElement.setAttributeNode(schemaNamePreCond);
    	
    	Attr tableNamePreCond = document.createAttribute("tableName");
    	tableNamePreCond.setValue(createTableScriptDTO.getTableName());
    	tableExistsElement.setAttributeNode(tableNamePreCond); 
    	
    	/*
    	 * generate CreateTable element
    	 */
    	Element createTableElement = document.createElement("createTable");
        //add createTable element element to changeSet
        changeSet.appendChild(createTableElement);
        
        //add CreateTable attributes
        Attr schemaName = document.createAttribute("schemaName");
        schemaName.setValue(createTableScriptDTO.getTableSchema());
        createTableElement.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(createTableScriptDTO.getTableName());
    	createTableElement.setAttributeNode(tableName); 
    	
    	//add primary key column element to CreateTable element
    	Element primaryKeyElement = document.createElement("column");
        //add primaryKey column element to CreateTable
    	createTableElement.appendChild(primaryKeyElement);
    	
    	//add primary key constraint to column
    	Element primaryKeyConstraint = document.createElement("constraints");
    	primaryKeyElement.appendChild(primaryKeyConstraint);
    	
    	Attr prkmaryKeyAttribiteConstraint = document.createAttribute("primaryKey");
    	prkmaryKeyAttribiteConstraint.setValue("true");
    	primaryKeyConstraint.setAttributeNode(prkmaryKeyAttribiteConstraint);
    	
    	Attr prkmaryKeyNameAttribiteConstraint = document.createAttribute("primaryKeyName");
    	prkmaryKeyNameAttribiteConstraint.setValue("Pk_"+createTableScriptDTO.getTableSchema()+"_"+createTableScriptDTO.getTableName());
    	primaryKeyConstraint.setAttributeNode(prkmaryKeyNameAttribiteConstraint);
    	
    	//add Primary key column attribute
    	Attr prkmaryKeyName = document.createAttribute("name");
    	prkmaryKeyName.setValue(createTableScriptDTO.getPrimaryKeyName());
    	primaryKeyElement.setAttributeNode(prkmaryKeyName);
    	
    	Attr prkmaryKeyType = document.createAttribute("type");
    	prkmaryKeyType.setValue(createTableScriptDTO.getPrimaryKeyType());
    	primaryKeyElement.setAttributeNode(prkmaryKeyType);
    	
    	//add columns to table
    	for(AddColumnScriptDTO c : createTableScriptDTO.getColumns()) {
    		//add new column to table
    		Element columnElement = document.createElement("column");
    		createTableElement.appendChild(columnElement);
    		
    		//add column attributes
    		Attr columnName = document.createAttribute("name");
    		columnName.setValue(c.getColumnName());
    		columnElement.setAttributeNode(columnName);
        	
    		Attr columnType = document.createAttribute("type");
    		columnType.setValue(c.getColumnType());
    		columnElement.setAttributeNode(columnType);
    		
    		if(!c.getColumnDefault().equals("")) {
    			Attr columnDefaultValue = document.createAttribute("defaultValue");
    			columnDefaultValue.setValue(c.getColumnDefault());
        		columnElement.setAttributeNode(columnDefaultValue);
    		}
    		
    		//add column constraint
    		Element constraintElement = document.createElement("constraints");
    		columnElement.appendChild(constraintElement);
    		
    		Attr nullableAttributeConstraint = document.createAttribute("nullable");
    		nullableAttributeConstraint.setValue(c.getIsNullable());
    		constraintElement.setAttributeNode(nullableAttributeConstraint);
    		
    		Attr uniqueAttributeConstraint = document.createAttribute("unique");
    		uniqueAttributeConstraint.setValue(c.getUnique());
    		constraintElement.setAttributeNode(uniqueAttributeConstraint);
    		
    		Attr uniqueNameAttribiteConstraint = document.createAttribute("uniqueConstraintName");
    		uniqueNameAttribiteConstraint.setValue("Unique_constraint_"+createTableScriptDTO.getTableSchema()+"_"+createTableScriptDTO.getTableName()+"_"+c.getColumnName());
    		constraintElement.setAttributeNode(uniqueNameAttribiteConstraint);
    		
    	}
    	
    	/*
    	 * add rollback element
    	 */
		Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		Element dropTableRollbackElement = document.createElement("dropTable");
		rollbackElement.appendChild(dropTableRollbackElement);
		
		Attr tableSchemaDropTableRollback = document.createAttribute("schemaName");
		tableSchemaDropTableRollback.setValue(createTableScriptDTO.getTableSchema());
		dropTableRollbackElement.setAttributeNode(tableSchemaDropTableRollback);
		
		Attr tableNameDropTableRollback = document.createAttribute("tableName");
		tableNameDropTableRollback.setValue(createTableScriptDTO.getTableName());
		dropTableRollbackElement.setAttributeNode(tableNameDropTableRollback);
		
		return changeSet;
	}
	
	/*
	 * Method for generate a Create Table Script
	 */
	@Override
	public String generateCreateTableLiquibaseXMLScript(CreateTableScriptDTO createTableScriptDTO) {
		String createTableXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(createTableScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateCreateTableChangeSet(document, createTableScriptDTO);
	        manageChangeSet(createTableScriptDTO,  document, changeSet);
        	
        	/*
        	 * generate XML script
        	 */
        	createTableXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
		
		return createTableXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Add Auto Increment Script
	 */
	private Element generateCreateSchemaChangeSet(Document document, CreateSchemaScriptDTO createSchemaScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, createSchemaScriptDTO);
        
        /*
         * Pre Condition element
         */
        Element preConditionElement = createPreConditionElement(document, createSchemaScriptDTO);
        changeSet.appendChild(preConditionElement);
        
        //add sql check
        Element sqlCheckElement = document.createElement("sqlCheck");
        preConditionElement.appendChild(sqlCheckElement);
		
		//add attributes to sql check
		Attr expectedResultAttribute = document.createAttribute("expectedResult");
		expectedResultAttribute.setValue("0");
		sqlCheckElement.setAttributeNode(expectedResultAttribute);
		
		String querySchemaAlreadyExists = "SELECT COUNT(*) FROM information_schema.schemata WHERE (schema_name = '"+createSchemaScriptDTO.getSchemaName()+"')";
		sqlCheckElement.appendChild(document.createTextNode(querySchemaAlreadyExists));
        
        /*
         * create sql element
         */
        Element sqlElement = document.createElement("sql");
		changeSet.appendChild(sqlElement);
		
		//add attributes to sql element
		Attr sqlElementAttribute = document.createAttribute("endDelimiter");
		sqlElementAttribute.setValue(";");
		sqlElement.setAttributeNode(sqlElementAttribute);
		
		//add sql script to sql element
		sqlElement.appendChild(document.createTextNode("CREATE SCHEMA " + createSchemaScriptDTO.getSchemaName()));
        
		/*
    	 * add rollback element
    	 */
		Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		Element sqlElementRollback = document.createElement("sql");
		rollbackElement.appendChild(sqlElementRollback);
		Attr sqlElementAttributeRollback  = document.createAttribute("endDelimiter");
		sqlElementAttributeRollback.setValue(";");
		sqlElementRollback.setAttributeNode(sqlElementAttributeRollback);
		sqlElementRollback.appendChild(document.createTextNode("DROP SCHEMA " + createSchemaScriptDTO.getSchemaName()));
		
		return changeSet;
	}
	
	/*
	 * Method for generate a Create Schema Script
	 */
	@Override
	public String generateCreateSchemaLiquibaseXMLScript(CreateSchemaScriptDTO createSchemaScriptDTO){
		String createSchemaXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(createSchemaScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateCreateSchemaChangeSet(document, createSchemaScriptDTO);
	        manageChangeSet(createSchemaScriptDTO,  document, changeSet);
    		
	        /*
	         * generate XML script
	         */
	        createSchemaXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
		
		return createSchemaXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Add Column Script
	 */
	private Element generateAddColumnChangeSet(Document document, AddColumnScriptDTO addColumnScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, addColumnScriptDTO);
        
        /*
         * add preCondition element
         */
    	Element preConditionElement = createPreConditionElement(document, addColumnScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
    	
    	//add preCondition child        	
    	Element notExists = document.createElement("not");
    	preConditionElement.appendChild(notExists);
    	
    	Element columnExists = document.createElement("columnExists");
    	notExists.appendChild(columnExists);
    	
    	Attr schemaNameColumnPreCond = document.createAttribute("schemaName");
    	schemaNameColumnPreCond.setValue(addColumnScriptDTO.getSchemaName());
    	columnExists.setAttributeNode(schemaNameColumnPreCond);
    	
    	Attr tableNameColumnPreCond = document.createAttribute("tableName");
    	tableNameColumnPreCond.setValue(addColumnScriptDTO.getTableName());
    	columnExists.setAttributeNode(tableNameColumnPreCond); 
    	
    	Attr columnNamePreCond = document.createAttribute("columnName");
    	columnNamePreCond.setValue(addColumnScriptDTO.getColumnName());
    	columnExists.setAttributeNode(columnNamePreCond); 
    	
    	/*
    	 * generate addColumn element
    	 */
    	Element addColumnElement = document.createElement("addColumn");
    	//append addColumn element to changeset
    	changeSet.appendChild(addColumnElement);
    	
    	//add addColumn element's attributes
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(addColumnScriptDTO.getSchemaName());
    	addColumnElement.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(addColumnScriptDTO.getTableName());
    	addColumnElement.setAttributeNode(tableName); 
    	
    	//append Column element to addColumn element (Column = new column to add)
    	Element columnElement = document.createElement("column");
    	addColumnElement.appendChild(columnElement);
    	
    	//add column element's attribute
    	Attr columnName = document.createAttribute("name");
    	columnName.setValue(addColumnScriptDTO.getColumnName());
    	columnElement.setAttributeNode(columnName);
    	
    	Attr columnType = document.createAttribute("type");
    	columnType.setValue(addColumnScriptDTO.getColumnType());
    	columnElement.setAttributeNode(columnType);
    	
    	if(!addColumnScriptDTO.getColumnDefault().equals("")) {
    		Attr columnDefaultValue = document.createAttribute("defaultValue");
    		columnDefaultValue.setValue(addColumnScriptDTO.getColumnDefault());
        	columnElement.setAttributeNode(columnDefaultValue);
    	}
    	
    	//append constraint element to column element
    	Element columnConstraintElement = document.createElement("constraints");
    	columnElement.appendChild(columnConstraintElement);
    	
    	//add constraint element's attributes
    	Attr nullable = document.createAttribute("nullable");
    	nullable.setValue(addColumnScriptDTO.getIsNullable());
    	columnConstraintElement.setAttributeNode(nullable);
    	
    	Attr unique = document.createAttribute("unique");
    	unique.setValue(addColumnScriptDTO.getUnique());
    	columnConstraintElement.setAttributeNode(unique);
    	
    	Attr uniqueName = document.createAttribute("uniqueConstraintName");
    	uniqueName.setValue("Unique_constraint_"+addColumnScriptDTO.getSchemaName()+"_"+addColumnScriptDTO.getTableName()+"_"+addColumnScriptDTO.getColumnName());
    	columnConstraintElement.setAttributeNode(uniqueName);
    	
    	/*
    	 * add rollback element
    	 */
		Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		Element dropColumnRollbackElement = document.createElement("dropColumn");
		rollbackElement.appendChild(dropColumnRollbackElement);
		
		Attr tableSchemaDropColumnRollback = document.createAttribute("schemaName");
		tableSchemaDropColumnRollback.setValue(addColumnScriptDTO.getSchemaName());
		dropColumnRollbackElement.setAttributeNode(tableSchemaDropColumnRollback);
		
		Attr tableNameDropColumnRollback = document.createAttribute("tableName");
		tableNameDropColumnRollback.setValue(addColumnScriptDTO.getTableName());
		dropColumnRollbackElement.setAttributeNode(tableNameDropColumnRollback);
		
		Element columnRollbackElement = document.createElement("column");
		dropColumnRollbackElement.appendChild(columnRollbackElement);
		
		Attr columnNameDropColumnRollback = document.createAttribute("name");
		columnNameDropColumnRollback.setValue(addColumnScriptDTO.getColumnName());
		columnRollbackElement.setAttributeNode(columnNameDropColumnRollback);
		
		return changeSet;
	}
	
	/*
	 * Method for generate a Add Column Script
	 */
	@Override
	public String generateAddColumnLiquibaseXMLScript(AddColumnScriptDTO addColumnScriptDTO) {
		String addColumnXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(addColumnScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
				document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
	        
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateAddColumnChangeSet(document, addColumnScriptDTO);
	        manageChangeSet(addColumnScriptDTO,  document, changeSet);
			
	        /*
	         * generate XML script
	         */
	        addColumnXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
		
		return addColumnXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Drop Not Null Constraint Script
	 */
	private Element generateDropNotNullConstraintChangeSet(Document document, DropNotNullConstraintScriptDTO dropNotNullConstraintScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, dropNotNullConstraintScriptDTO);
        
        /*
         * add preCondition element
         */
    	Element preConditionElement = createPreConditionElement(document, dropNotNullConstraintScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
		
    	//add preCondition child
    	Element columnExistsElement = document.createElement("columnExists");
    	preConditionElement.appendChild(columnExistsElement);
   	
    	Attr schemaNamePreCond = document.createAttribute("schemaName");
    	schemaNamePreCond.setValue(dropNotNullConstraintScriptDTO.getTableSchema());
    	columnExistsElement.setAttributeNode(schemaNamePreCond);
    	
    	Attr tableNamePreCond = document.createAttribute("tableName");
    	tableNamePreCond.setValue(dropNotNullConstraintScriptDTO.getTableName());
    	columnExistsElement.setAttributeNode(tableNamePreCond); 
    	
    	Attr columnNamePreCond = document.createAttribute("columnName");
    	columnNamePreCond.setValue(dropNotNullConstraintScriptDTO.getColumnName());
    	columnExistsElement.setAttributeNode(columnNamePreCond); 
    	
    	/*
    	 * Add DropNotNullConstraint element
    	 */
    	Element dropNotNullConstraintElement = document.createElement("dropNotNullConstraint");
    	//append dropNotNullConstraint Element to changeSet
		changeSet.appendChild(dropNotNullConstraintElement);
		
		//append dropNotNullConstraintElement's attributes
		Attr tableSchemaDropNotNullConstraint = document.createAttribute("schemaName");
		tableSchemaDropNotNullConstraint.setValue(dropNotNullConstraintScriptDTO.getTableSchema());
		dropNotNullConstraintElement.setAttributeNode(tableSchemaDropNotNullConstraint);
		
		Attr tableNameDropNotNullConstraint = document.createAttribute("tableName");
		tableNameDropNotNullConstraint.setValue(dropNotNullConstraintScriptDTO.getTableName());
		dropNotNullConstraintElement.setAttributeNode(tableNameDropNotNullConstraint);
		
		Attr columnNameDropNotNullConstraint = document.createAttribute("columnName");
		columnNameDropNotNullConstraint.setValue(dropNotNullConstraintScriptDTO.getColumnName());
		dropNotNullConstraintElement.setAttributeNode(columnNameDropNotNullConstraint);
		
		Attr columnDataTypeDropNotNullConstraint = document.createAttribute("columnDataType");
		columnDataTypeDropNotNullConstraint.setValue(dropNotNullConstraintScriptDTO.getColumnDataType());
		dropNotNullConstraintElement.setAttributeNode(columnDataTypeDropNotNullConstraint);
    	
    	/*
    	 * add rollback element
    	 */
		Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		Element addNotNullConstraintRollbackElement = document.createElement("addNotNullConstraint");
		rollbackElement.appendChild(addNotNullConstraintRollbackElement);
		
		Attr tableSchemaAddNotNullConstraintRollback = document.createAttribute("schemaName");
		tableSchemaAddNotNullConstraintRollback.setValue(dropNotNullConstraintScriptDTO.getTableSchema());
		addNotNullConstraintRollbackElement.setAttributeNode(tableSchemaAddNotNullConstraintRollback);
		
		Attr tableNameAddNotNullConstraintRollback = document.createAttribute("tableName");
		tableNameAddNotNullConstraintRollback.setValue(dropNotNullConstraintScriptDTO.getTableName());
		addNotNullConstraintRollbackElement.setAttributeNode(tableNameAddNotNullConstraintRollback);
		
		Attr columnNameAddNotNullConstraintRollback = document.createAttribute("columnName");
		columnNameAddNotNullConstraintRollback.setValue(dropNotNullConstraintScriptDTO.getColumnName());
		addNotNullConstraintRollbackElement.setAttributeNode(columnNameAddNotNullConstraintRollback);
		
		Attr columnDataTypeAddNotNullConstraintRollback = document.createAttribute("columnDataType");
		columnDataTypeAddNotNullConstraintRollback.setValue(dropNotNullConstraintScriptDTO.getColumnDataType());
		addNotNullConstraintRollbackElement.setAttributeNode(columnDataTypeAddNotNullConstraintRollback);
		
		Attr validateAddNotNullConstraintRollback = document.createAttribute("validate");
		validateAddNotNullConstraintRollback.setValue("true");
		addNotNullConstraintRollbackElement.setAttributeNode(validateAddNotNullConstraintRollback);
		
		return changeSet;
	}
	
	/*
	 * Method for generate a Drop Not Null Constraint Script
	 */
	@Override
	public String generateDropNotNullConstraintLiquibaseXMLScript(DropNotNullConstraintScriptDTO dropNotNullConstraintScriptDTO) {
		String dropNotNullConstraintXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(dropNotNullConstraintScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateDropNotNullConstraintChangeSet(document, dropNotNullConstraintScriptDTO);
	        manageChangeSet(dropNotNullConstraintScriptDTO,  document, changeSet);
	        
        	/*
	         * generate XML script
	         */
        	dropNotNullConstraintXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
		
		return dropNotNullConstraintXMLScript;
	}

	/*
	 * Create Element ChangeSet for Add Not Null COnstraint Script
	 */
	private Element generateAddNotNullConstraintChangeSet(Document document, AddNotNullConstraintScriptDTO addNotNullConstraintScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, addNotNullConstraintScriptDTO);
        
        /*
         * add preCondition element
         */
    	Element preConditionElement = createPreConditionElement(document, addNotNullConstraintScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
		
    	//add preCondition child
    	Element columnExistsElement = document.createElement("columnExists");
    	preConditionElement.appendChild(columnExistsElement);
   	
    	Attr schemaNamePreCond = document.createAttribute("schemaName");
    	schemaNamePreCond.setValue(addNotNullConstraintScriptDTO.getTableSchema());
    	columnExistsElement.setAttributeNode(schemaNamePreCond);
    	
    	Attr tableNamePreCond = document.createAttribute("tableName");
    	tableNamePreCond.setValue(addNotNullConstraintScriptDTO.getTableName());
    	columnExistsElement.setAttributeNode(tableNamePreCond); 
    	
    	Attr columnNamePreCond = document.createAttribute("columnName");
    	columnNamePreCond.setValue(addNotNullConstraintScriptDTO.getColumnName());
    	columnExistsElement.setAttributeNode(columnNamePreCond); 
    	
    	/*
    	 * Add addNotNullConstraint element
    	 */
    	Element addNotNullConstraintElement = document.createElement("addNotNullConstraint");
    	//append addNotNullConstraint to changeSet
		changeSet.appendChild(addNotNullConstraintElement);
		
		//append addNotNullConstraintElement's attributes
		Attr tableSchemaAddNotNullConstraint = document.createAttribute("schemaName");
		tableSchemaAddNotNullConstraint.setValue(addNotNullConstraintScriptDTO.getTableSchema());
		addNotNullConstraintElement.setAttributeNode(tableSchemaAddNotNullConstraint);
		
		Attr tableNameAddNotNullConstraint = document.createAttribute("tableName");
		tableNameAddNotNullConstraint.setValue(addNotNullConstraintScriptDTO.getTableName());
		addNotNullConstraintElement.setAttributeNode(tableNameAddNotNullConstraint);
		
		Attr columnNameAddNotNullConstraint = document.createAttribute("columnName");
		columnNameAddNotNullConstraint.setValue(addNotNullConstraintScriptDTO.getColumnName());
		addNotNullConstraintElement.setAttributeNode(columnNameAddNotNullConstraint);
		
		Attr columnDataTypeAddNotNullConstraint = document.createAttribute("columnDataType");
		columnDataTypeAddNotNullConstraint.setValue(addNotNullConstraintScriptDTO.getColumnDataType());
		addNotNullConstraintElement.setAttributeNode(columnDataTypeAddNotNullConstraint);
		
		Attr validateAddNotNullConstraint = document.createAttribute("validate");
		validateAddNotNullConstraint.setValue("true");
		addNotNullConstraintElement.setAttributeNode(validateAddNotNullConstraint);
		
		Attr defaultNullValueAddNotNullConstraint = document.createAttribute("defaultNullValue");
		defaultNullValueAddNotNullConstraint.setValue(addNotNullConstraintScriptDTO.getDefaultNullValue());
		addNotNullConstraintElement.setAttributeNode(defaultNullValueAddNotNullConstraint);
    	
		/*
    	 * add rollback element
    	 */
		Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		Element dropNotNullConstraintElementRollback = document.createElement("dropNotNullConstraint");
		rollbackElement.appendChild(dropNotNullConstraintElementRollback);
		
		//append dropNotNullConstraintElement's attributes
		Attr tableSchemaDropNotNullConstraint = document.createAttribute("schemaName");
		tableSchemaDropNotNullConstraint.setValue(addNotNullConstraintScriptDTO.getTableSchema());
		dropNotNullConstraintElementRollback.setAttributeNode(tableSchemaDropNotNullConstraint);
		
		Attr tableNameDropNotNullConstraint = document.createAttribute("tableName");
		tableNameDropNotNullConstraint.setValue(addNotNullConstraintScriptDTO.getTableName());
		dropNotNullConstraintElementRollback.setAttributeNode(tableNameDropNotNullConstraint);
		
		Attr columnNameDropNotNullConstraint = document.createAttribute("columnName");
		columnNameDropNotNullConstraint.setValue(addNotNullConstraintScriptDTO.getColumnName());
		dropNotNullConstraintElementRollback.setAttributeNode(columnNameDropNotNullConstraint);
		
		Attr columnDataTypeDropNotNullConstraint = document.createAttribute("columnDataType");
		columnDataTypeDropNotNullConstraint.setValue(addNotNullConstraintScriptDTO.getColumnDataType());
		dropNotNullConstraintElementRollback.setAttributeNode(columnDataTypeDropNotNullConstraint);
		
		return changeSet;
	}
	
	/*
	 * Method for generate a Add Not Null Constraint Script
	 */
	@Override
	public String generateAddNotNullConstraintLiquibaseXMLScript(AddNotNullConstraintScriptDTO addNotNullConstraintScriptDTO) {
		String addNotNullConstraintXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(addNotNullConstraintScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateAddNotNullConstraintChangeSet(document, addNotNullConstraintScriptDTO);
	        manageChangeSet(addNotNullConstraintScriptDTO,  document, changeSet);
	        
        	/*
	         * generate XML script
	         */
        	addNotNullConstraintXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
		
		return addNotNullConstraintXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Add Unique COnstraint Script
	 */
	private Element generateAddUniqueConstraintChangeSet(Document document, AddUniqueConstraintScriptDTO addUniqueConstraintScriptDTO){
		//changeSet element
        Element changeSet = createChangeSetElement(document, addUniqueConstraintScriptDTO);
        
        /*
         * add preCondition element
         */
    	Element preConditionElement = createPreConditionElement(document, addUniqueConstraintScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
		
    	//add preCondition child
    	Element notElement = document.createElement("not");
    	preConditionElement.appendChild(notElement);
    	
    	Element uniqueConstraintExistsElement = document.createElement("uniqueConstraintExists");
    	notElement.appendChild(uniqueConstraintExistsElement);
    	
    	Attr tableNamePreCond = document.createAttribute("tableName");
    	tableNamePreCond.setValue(addUniqueConstraintScriptDTO.getTableName());
    	uniqueConstraintExistsElement.setAttributeNode(tableNamePreCond); 
    	
    	Attr columnNamePreCond = document.createAttribute("columnNames");
    	String columnsStr = "";
    	for(AddUniqueConstraintScriptDTO.ColumnUnique s : addUniqueConstraintScriptDTO.getColumns()) {
    		columnsStr += s.getColumnName() + ", ";
    	}
    	columnsStr = columnsStr.substring(0, columnsStr.length() - 2);
    	columnNamePreCond.setValue(columnsStr);
    	uniqueConstraintExistsElement.setAttributeNode(columnNamePreCond); 
    	
    	Attr constraintNamePreCond = document.createAttribute("constraintName");
    	constraintNamePreCond.setValue(addUniqueConstraintScriptDTO.getConstrainName());
    	uniqueConstraintExistsElement.setAttributeNode(constraintNamePreCond); 
    	
    	for(AddUniqueConstraintScriptDTO.ColumnUnique s : addUniqueConstraintScriptDTO.getColumns()) {
    		Element columnExistsElement = document.createElement("columnExists");
    		preConditionElement.appendChild(columnExistsElement);
    		
    		Attr tableColumnSchemaPreCond = document.createAttribute("schemaName");
    		tableColumnSchemaPreCond.setValue(addUniqueConstraintScriptDTO.getTableSchema());
    		columnExistsElement.setAttributeNode(tableColumnSchemaPreCond); 
    		
    		Attr tableColumnPreCond = document.createAttribute("tableName");
    		tableColumnPreCond.setValue(addUniqueConstraintScriptDTO.getTableName());
    		columnExistsElement.setAttributeNode(tableColumnPreCond); 
    		
    		Attr columnNameExistsPreCond = document.createAttribute("columnName");
    		columnNameExistsPreCond.setValue(s.getColumnName());
    		columnExistsElement.setAttributeNode(columnNameExistsPreCond);
    	}
    	
    	/*
    	 * Add Unique Constraint element
    	 */
    	Element addUniqueConstraintElement = document.createElement("addUniqueConstraint");
    	//append addUniqueConstraint element to changeset
    	changeSet.appendChild(addUniqueConstraintElement);
    	
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(addUniqueConstraintScriptDTO.getTableSchema());
    	addUniqueConstraintElement.setAttributeNode(schemaName); 
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(addUniqueConstraintScriptDTO.getTableName());
    	addUniqueConstraintElement.setAttributeNode(tableName); 

    	Attr columnName = document.createAttribute("columnNames");
    	columnName.setValue(columnsStr);
    	addUniqueConstraintElement.setAttributeNode(columnName); 
    	
    	Attr constraintName = document.createAttribute("constraintName");
    	constraintName.setValue(addUniqueConstraintScriptDTO.getConstrainName());
    	addUniqueConstraintElement.setAttributeNode(constraintName); 
    	
    	Attr validate = document.createAttribute("validate");
    	validate.setValue("true");
    	addUniqueConstraintElement.setAttributeNode(validate); 
    	
    	Attr disabled = document.createAttribute("disabled");
    	disabled.setValue("false");
    	addUniqueConstraintElement.setAttributeNode(disabled); 
    	
    	/*
    	 * add rollback element
    	 */
		Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		Element dropUniqueConstraintElement = document.createElement("dropUniqueConstraint");
    	//append addUniqueConstraint element to rolback
    	rollbackElement.appendChild(dropUniqueConstraintElement);
		
    	Attr schemaNameRolback = document.createAttribute("schemaName");
    	schemaNameRolback.setValue(addUniqueConstraintScriptDTO.getTableSchema());
    	dropUniqueConstraintElement.setAttributeNode(schemaNameRolback); 
    	
    	Attr tableNameRolback = document.createAttribute("tableName");
    	tableNameRolback.setValue(addUniqueConstraintScriptDTO.getTableName());
    	dropUniqueConstraintElement.setAttributeNode(tableNameRolback); 
		
    	Attr columnNamesRolback = document.createAttribute("columnNames");
    	columnNamesRolback.setValue(columnsStr);
    	dropUniqueConstraintElement.setAttributeNode(columnNamesRolback); 
    	
    	Attr constraintNameRolback = document.createAttribute("constraintName");
    	constraintNameRolback.setValue(addUniqueConstraintScriptDTO.getConstrainName());
    	dropUniqueConstraintElement.setAttributeNode(constraintNameRolback);
		
		return changeSet;
	}
	
	/*
	 * Method for generate an Add Unique Constraint Script
	 */
	@Override
	public String generateAddUniqueConstraintLiquibaseXMLScript(AddUniqueConstraintScriptDTO addUniqueConstraintScriptDTO) {
		String addUniqueConstraintXMLScript = "ciao";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(addUniqueConstraintScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateAddUniqueConstraintChangeSet(document, addUniqueConstraintScriptDTO);
	        manageChangeSet(addUniqueConstraintScriptDTO,  document, changeSet);     
        	
			/*
	         * generate XML script
	         */
			addUniqueConstraintXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
		
		return addUniqueConstraintXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Drop Unique Constraint Script
	 */
	private Element generateDropUniqueConstraintChangeSet(Document document, DropUniqueConstraintScriptDTO dropUniqueConstraintScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, dropUniqueConstraintScriptDTO);
        
        /*
         * add preCondition element
         */
    	Element preConditionElement = createPreConditionElement(document, dropUniqueConstraintScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
		
    	//add preCondition child
    	Element uniqueConstraintExistsElement = document.createElement("uniqueConstraintExists");
    	preConditionElement.appendChild(uniqueConstraintExistsElement);
    	
    	Attr tableNamePreCond = document.createAttribute("tableName");
    	tableNamePreCond.setValue(dropUniqueConstraintScriptDTO.getTableName());
    	uniqueConstraintExistsElement.setAttributeNode(tableNamePreCond); 
    	
    	Attr constraintNamePreCond = document.createAttribute("constraintName");
    	constraintNamePreCond.setValue(dropUniqueConstraintScriptDTO.getConstrainName());
    	uniqueConstraintExistsElement.setAttributeNode(constraintNamePreCond);
	
    	/*
    	 * Add DropUniqueConstraint element
    	 */
		Element dropUniqueConstraintElement = document.createElement("dropUniqueConstraint");
    	//append addUniqueConstraint element to rolback
		changeSet.appendChild(dropUniqueConstraintElement);
		
    	Attr schemaNameRolback = document.createAttribute("schemaName");
    	schemaNameRolback.setValue(dropUniqueConstraintScriptDTO.getTableSchema());
    	dropUniqueConstraintElement.setAttributeNode(schemaNameRolback); 
    	
    	Attr tableNameRolback = document.createAttribute("tableName");
    	tableNameRolback.setValue(dropUniqueConstraintScriptDTO.getTableName());
    	dropUniqueConstraintElement.setAttributeNode(tableNameRolback); 
    	
    	Attr constraintNameRolback = document.createAttribute("constraintName");
    	constraintNameRolback.setValue(dropUniqueConstraintScriptDTO.getConstrainName());
    	dropUniqueConstraintElement.setAttributeNode(constraintNameRolback); 
    	
		return changeSet;
	}
	
	/*
	 * Method for generate an Drop Unique Constraint Script
	 */
	@Override
	public String generateDropUniqueConstraintLiquibaseXMLScript(DropUniqueConstraintScriptDTO dropUniqueConstraintScriptDTO) {
		String dropUniqueConstraintXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(dropUniqueConstraintScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateDropUniqueConstraintChangeSet(document, dropUniqueConstraintScriptDTO);
	        manageChangeSet(dropUniqueConstraintScriptDTO,  document, changeSet);
	        
        	/*
	         * generate XML script
	         */
        	dropUniqueConstraintXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        	
		return dropUniqueConstraintXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Rename Table Script
	 */
	public Element generateRenameTableChangeSet(Document document, RenameTableScriptDTO renameTableScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, renameTableScriptDTO);
        
        /*
         * add preCondition element
         */
    	Element preConditionElement = createPreConditionElement(document, renameTableScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
        
        //add preCondition child
    	//old table exists
    	Element oldTtableExistsElement = document.createElement("tableExists");
    	preConditionElement.appendChild(oldTtableExistsElement);
    	
    	Attr schemaOldNamePreCond = document.createAttribute("schemaName");
    	schemaOldNamePreCond.setValue(renameTableScriptDTO.getSchemaName());
    	oldTtableExistsElement.setAttributeNode(schemaOldNamePreCond);
    	
    	Attr oldTableNamePreCOnd = document.createAttribute("tableName");
    	oldTableNamePreCOnd.setValue(renameTableScriptDTO.getOldTableName());
    	oldTtableExistsElement.setAttributeNode(oldTableNamePreCOnd);
    	
    	//new table not exists
    	Element not = document.createElement("not");
    	preConditionElement.appendChild(not);
        
    	Element newTtableExistsElement = document.createElement("tableExists");
    	not.appendChild(newTtableExistsElement);
    	
    	Attr schemaNewNamePreCond = document.createAttribute("schemaName");
    	schemaNewNamePreCond.setValue(renameTableScriptDTO.getSchemaName());
    	newTtableExistsElement.setAttributeNode(schemaNewNamePreCond);
    	
    	Attr newTableNamePreCOnd = document.createAttribute("tableName");
    	newTableNamePreCOnd.setValue(renameTableScriptDTO.getNewTableName());
    	newTtableExistsElement.setAttributeNode(newTableNamePreCOnd);
    	
    	/*
    	 * add Rename Table Element
    	 */
    	Element renameTableElement = document.createElement("renameTable");
    	//add rename table element element to changeSet
    	changeSet.appendChild(renameTableElement);
    	
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(renameTableScriptDTO.getSchemaName());
    	renameTableElement.setAttributeNode(schemaName);
    	
    	Attr oldTableName = document.createAttribute("oldTableName");
    	oldTableName.setValue(renameTableScriptDTO.getOldTableName());
    	renameTableElement.setAttributeNode(oldTableName);
    	
    	Attr newTableName = document.createAttribute("newTableName");
    	newTableName.setValue(renameTableScriptDTO.getNewTableName());
    	renameTableElement.setAttributeNode(newTableName);
        
    	/*
    	 * Add rollback element
    	 */
    	Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		Element renameTableRollback = document.createElement("renameTable");
    	//add rename table element element to changeSet
		rollbackElement.appendChild(renameTableRollback);
    	
    	Attr schemaNameRolback = document.createAttribute("schemaName");
    	schemaNameRolback.setValue(renameTableScriptDTO.getSchemaName());
    	renameTableRollback.setAttributeNode(schemaNameRolback);
    	
    	Attr oldTableNameRollback = document.createAttribute("oldTableName");
    	oldTableNameRollback.setValue(renameTableScriptDTO.getNewTableName());
    	renameTableRollback.setAttributeNode(oldTableNameRollback);
    	
    	Attr newTableNameRollback = document.createAttribute("newTableName");
    	newTableNameRollback.setValue(renameTableScriptDTO.getOldTableName());
    	renameTableRollback.setAttributeNode(newTableNameRollback);
		
		return changeSet;
	}
	
	/*
	 * Method for generate an Rename Table Script
	 */
	@Override
	public String generateRenameTableLiquibaseXMLScript(RenameTableScriptDTO renameTableScriptDTO) {
		String renameTableXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(renameTableScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateRenameTableChangeSet(document, renameTableScriptDTO);
	        manageChangeSet(renameTableScriptDTO,  document, changeSet);
	        
			/*
			 * generate XML script
			 */
			renameTableXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		
		return renameTableXMLScript;
	}

	/*
	 * Create Element ChangeSet for Rename Column Script
	 */
	private Element generateRenameColumnChangeSet(Document document, RenameColumnScriptDTO renameColumnScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, renameColumnScriptDTO);
        
        /*
         * add preCondition element
         */
    	Element preConditionElement = createPreConditionElement(document, renameColumnScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
        
        //add preCondition child
    	//old column exists
    	Element oldColumnExistsElement = document.createElement("columnExists");
    	preConditionElement.appendChild(oldColumnExistsElement);
    	
    	Attr schemaOldNamePreCond = document.createAttribute("schemaName");
    	schemaOldNamePreCond.setValue(renameColumnScriptDTO.getSchemaName());
    	oldColumnExistsElement.setAttributeNode(schemaOldNamePreCond);
    	
    	Attr oldTableNamePreCOnd = document.createAttribute("tableName");
    	oldTableNamePreCOnd.setValue(renameColumnScriptDTO.getTableName());
    	oldColumnExistsElement.setAttributeNode(oldTableNamePreCOnd);
    	
    	Attr oldColumnNamePreCOnd = document.createAttribute("columnName");
    	oldColumnNamePreCOnd.setValue(renameColumnScriptDTO.getOldColumnName());
    	oldColumnExistsElement.setAttributeNode(oldColumnNamePreCOnd);
    	
    	//new column not exists
    	Element not = document.createElement("not");
    	preConditionElement.appendChild(not);
        
    	Element newColumnExistsElement = document.createElement("columnExists");
    	not.appendChild(newColumnExistsElement);
    	
    	Attr schemaNewNamePreCond = document.createAttribute("schemaName");
    	schemaNewNamePreCond.setValue(renameColumnScriptDTO.getSchemaName());
    	newColumnExistsElement.setAttributeNode(schemaNewNamePreCond);
    	
    	Attr newTableNamePreCOnd = document.createAttribute("tableName");
    	newTableNamePreCOnd.setValue(renameColumnScriptDTO.getTableName());
    	newColumnExistsElement.setAttributeNode(newTableNamePreCOnd);
    	
    	Attr newColumnNamePreCOnd = document.createAttribute("columnName");
    	newColumnNamePreCOnd.setValue(renameColumnScriptDTO.getNewColumnName());
    	newColumnExistsElement.setAttributeNode(newColumnNamePreCOnd);
        
    	/*
    	 * Add RenameColumn element
    	 */
		Element renameColumn = document.createElement("renameColumn");
    	//add rename column element to changeSet
		changeSet.appendChild(renameColumn);
    	
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(renameColumnScriptDTO.getSchemaName());
    	renameColumn.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(renameColumnScriptDTO.getTableName());
    	renameColumn.setAttributeNode(tableName);        	
    	
    	Attr newColumnName = document.createAttribute("newColumnName");
    	newColumnName.setValue(renameColumnScriptDTO.getNewColumnName());
    	renameColumn.setAttributeNode(newColumnName);
    	
    	Attr oldColumnName = document.createAttribute("oldColumnName");
    	oldColumnName.setValue(renameColumnScriptDTO.getOldColumnName());
    	renameColumn.setAttributeNode(oldColumnName);
    	
    	Attr columnType = document.createAttribute("columnDataType");
    	columnType.setValue(renameColumnScriptDTO.getColumnType());
    	renameColumn.setAttributeNode(columnType);
    	
    	/*
    	 * Add rollback element
    	 */
    	Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		Element renameColumnRollback = document.createElement("renameColumn");
    	//add rename column element element to rollback
		rollbackElement.appendChild(renameColumnRollback);
    	
    	Attr schemaNameRolback = document.createAttribute("schemaName");
    	schemaNameRolback.setValue(renameColumnScriptDTO.getSchemaName());
    	renameColumnRollback.setAttributeNode(schemaNameRolback);
    	
    	Attr tableNameRollback = document.createAttribute("tableName");
    	tableNameRollback.setValue(renameColumnScriptDTO.getTableName());
    	renameColumnRollback.setAttributeNode(tableNameRollback);        	
    	
    	Attr newColumnNameRollback = document.createAttribute("newColumnName");
    	newColumnNameRollback.setValue(renameColumnScriptDTO.getOldColumnName());
    	renameColumnRollback.setAttributeNode(newColumnNameRollback);
    	
    	Attr oldColumnNameRollback = document.createAttribute("oldColumnName");
    	oldColumnNameRollback.setValue(renameColumnScriptDTO.getNewColumnName());
    	renameColumnRollback.setAttributeNode(oldColumnNameRollback);
    	
    	Attr columnTypeRollback = document.createAttribute("columnDataType");
    	columnTypeRollback.setValue(renameColumnScriptDTO.getColumnType());
    	renameColumnRollback.setAttributeNode(columnTypeRollback);
		
		return changeSet;
	}
	
	/*
	 * Method for generate an Rename Column Script
	 */
	@Override
	public String generateRenameColumnLiquibaseXMLScript(RenameColumnScriptDTO renameColumnScriptDTO) {
		String renameColumnXMLScript = "";
		
		try {	        
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(renameColumnScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateRenameColumnChangeSet(document, renameColumnScriptDTO);
	        manageChangeSet(renameColumnScriptDTO,  document, changeSet);
        	
	        /*
			 * generate XML script
			 */
	        renameColumnXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}  
	        
	    return renameColumnXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Modify Column Data Type Script
	 */
	private Element generateModifyColumnDataTypeChangeSet(Document document, ModifyColumnDataTypeScriptDTO modifyColumnDataTypeScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, modifyColumnDataTypeScriptDTO);
        
        /*
         * Pre-condition element
         */
        Element preConditionElement = createPreConditionElement(document, modifyColumnDataTypeScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
    	
        //add preCondition child
    	//column exists
    	Element columnExistsElement = document.createElement("columnExists");
    	preConditionElement.appendChild(columnExistsElement);
    	
    	Attr schemaNamePreCond = document.createAttribute("schemaName");
    	schemaNamePreCond.setValue(modifyColumnDataTypeScriptDTO.getSchemaName());
    	columnExistsElement.setAttributeNode(schemaNamePreCond);
    	
    	Attr tableNamePreCond = document.createAttribute("tableName");
    	tableNamePreCond.setValue(modifyColumnDataTypeScriptDTO.getTableName());
    	columnExistsElement.setAttributeNode(tableNamePreCond);
    	
    	Attr columnNamePreCOnd = document.createAttribute("columnName");
    	columnNamePreCOnd.setValue(modifyColumnDataTypeScriptDTO.getColumnName());
    	columnExistsElement.setAttributeNode(columnNamePreCOnd);
    	
    	/*
    	 * Modify column data type element
    	 */
    	Element modifyColumnDataType = document.createElement("modifyDataType");
    	//add modify column data type element to changeSet
		changeSet.appendChild(modifyColumnDataType);
    	
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(modifyColumnDataTypeScriptDTO.getSchemaName());
    	modifyColumnDataType.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(modifyColumnDataTypeScriptDTO.getTableName());
    	modifyColumnDataType.setAttributeNode(tableName);        	
    	
    	Attr columnName = document.createAttribute("columnName");
    	columnName.setValue(modifyColumnDataTypeScriptDTO.getColumnName());
    	modifyColumnDataType.setAttributeNode(columnName);
    	
    	Attr newDataType = document.createAttribute("newDataType");
    	newDataType.setValue(modifyColumnDataTypeScriptDTO.getNewColumnType());
    	modifyColumnDataType.setAttributeNode(newDataType);
    	
    	/*
    	 * Add rollback element
    	 */
    	Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		//append rolback child
		Element modifyColumnDataTypeRollback = document.createElement("modifyDataType");
    	//add modify column data type element to rollback
		rollbackElement.appendChild(modifyColumnDataTypeRollback);
    	
    	Attr schemaNameRollback = document.createAttribute("schemaName");
    	schemaNameRollback.setValue(modifyColumnDataTypeScriptDTO.getSchemaName());
    	modifyColumnDataTypeRollback.setAttributeNode(schemaNameRollback);
    	
    	Attr tableNameRollback = document.createAttribute("tableName");
    	tableNameRollback.setValue(modifyColumnDataTypeScriptDTO.getTableName());
    	modifyColumnDataTypeRollback.setAttributeNode(tableNameRollback);        	
    	
    	Attr columnNameRollback = document.createAttribute("columnName");
    	columnNameRollback.setValue(modifyColumnDataTypeScriptDTO.getColumnName());
    	modifyColumnDataTypeRollback.setAttributeNode(columnNameRollback);
    	
    	Attr newDataTypeRollback = document.createAttribute("newDataType");
    	newDataTypeRollback.setValue(modifyColumnDataTypeScriptDTO.getOldColumnType());
    	modifyColumnDataTypeRollback.setAttributeNode(newDataTypeRollback);
		
		return changeSet;
	}
	
	/*
	 * Method for generate an Modify Column Data Type Script
	 */
	@Override
	public String generateModifyColumnDataTypeLiquibaseXMLScript(ModifyColumnDataTypeScriptDTO modifyColumnDataTypeScriptDTO) {
		String modifyColumnDataTypeXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(modifyColumnDataTypeScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateModifyColumnDataTypeChangeSet(document, modifyColumnDataTypeScriptDTO);
	        manageChangeSet(modifyColumnDataTypeScriptDTO,  document, changeSet);
        	
	        /*
			 * generate XML script
			 */
	        modifyColumnDataTypeXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}  
	        
	    return modifyColumnDataTypeXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Add Auto Increment Script
	 */
	private Element generateAddAutoIncrementChangeSet(Document document, AddAutoIncrementScriptDTO addAutoIncrementScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, addAutoIncrementScriptDTO);
        
        /*
         * Pre-condition element
         */
        Element preConditionElement = createPreConditionElement(document, addAutoIncrementScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
    	
        //add preCondition child
    	//column exists
    	Element columnExistsElement = document.createElement("columnExists");
    	preConditionElement.appendChild(columnExistsElement);
    	
    	Attr schemaNamePreCond = document.createAttribute("schemaName");
    	schemaNamePreCond.setValue(addAutoIncrementScriptDTO.getSchemaName());
    	columnExistsElement.setAttributeNode(schemaNamePreCond);
    	
    	Attr tableNamePreCond = document.createAttribute("tableName");
    	tableNamePreCond.setValue(addAutoIncrementScriptDTO.getTableName());
    	columnExistsElement.setAttributeNode(tableNamePreCond);
    	
    	Attr columnNamePreCond = document.createAttribute("columnName");
    	columnNamePreCond.setValue(addAutoIncrementScriptDTO.getColumnName());
    	columnExistsElement.setAttributeNode(columnNamePreCond);
    	
    	/*
    	 * Add auto increment element
    	 */
    	Element addAutoIncrementElement = document.createElement("addAutoIncrement");
    	changeSet.appendChild(addAutoIncrementElement);
    	
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(addAutoIncrementScriptDTO.getSchemaName());
    	addAutoIncrementElement.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(addAutoIncrementScriptDTO.getTableName());
    	addAutoIncrementElement.setAttributeNode(tableName);
    	
    	Attr columnName = document.createAttribute("columnName");
    	columnName.setValue(addAutoIncrementScriptDTO.getColumnName());
    	addAutoIncrementElement.setAttributeNode(columnName);
    	
    	Attr startWith = document.createAttribute("startWith");
    	startWith.setValue(addAutoIncrementScriptDTO.getStratWith().toString());
    	addAutoIncrementElement.setAttributeNode(startWith);
    	
    	Attr incrementBy = document.createAttribute("incrementBy");
    	incrementBy.setValue(addAutoIncrementScriptDTO.getIncrementBy().toString());
    	addAutoIncrementElement.setAttributeNode(incrementBy);
        
        return changeSet;
	}
	
	/*
	 * Method for generate an Add Auto Increment Script
	 */
	@Override
	public String generateAddAutoIncrementLiquibaseXMLScript(AddAutoIncrementScriptDTO addAutoIncrementScriptDTO) {
		String addAutoIncrementXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(addAutoIncrementScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateAddAutoIncrementChangeSet(document, addAutoIncrementScriptDTO);
	        manageChangeSet(addAutoIncrementScriptDTO,  document, changeSet);
		
        	/*
			 * generate XML script
			 */
        	addAutoIncrementXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
        	
		return addAutoIncrementXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Add Auto Increment Script
	 */
	private Element generateAddDefaultValueChangeSet(Document document, AddDefaultValueScriptDTO addDefaultValueScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, addDefaultValueScriptDTO);
        
        /*
         * Pre-condition element
         */
        Element preConditionElement = createPreConditionElement(document, addDefaultValueScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
    	
        //add preCondition child
    	//column exists
    	Element columnExistsElement = document.createElement("columnExists");
    	preConditionElement.appendChild(columnExistsElement);
    	
    	Attr schemaNamePreCond = document.createAttribute("schemaName");
    	schemaNamePreCond.setValue(addDefaultValueScriptDTO.getSchemaName());
    	columnExistsElement.setAttributeNode(schemaNamePreCond);
    	
    	Attr tableNamePreCond = document.createAttribute("tableName");
    	tableNamePreCond.setValue(addDefaultValueScriptDTO.getTableName());
    	columnExistsElement.setAttributeNode(tableNamePreCond);
    	
    	Attr columnNamePreCond = document.createAttribute("columnName");
    	columnNamePreCond.setValue(addDefaultValueScriptDTO.getColumnName());
    	columnExistsElement.setAttributeNode(columnNamePreCond);
    	
    	/*
    	 * Add Default Value Element
    	 */
    	Element addDefaultValueElement = document.createElement("addDefaultValue");
    	changeSet.appendChild(addDefaultValueElement);
    	
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(addDefaultValueScriptDTO.getSchemaName());
    	addDefaultValueElement.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(addDefaultValueScriptDTO.getTableName());
    	addDefaultValueElement.setAttributeNode(tableName);
    	
    	Attr columnName = document.createAttribute("columnName");
    	columnName.setValue(addDefaultValueScriptDTO.getColumnName());
    	addDefaultValueElement.setAttributeNode(columnName);
    	
    	Attr columnDataType = document.createAttribute("columnDataType");
    	columnDataType.setValue(addDefaultValueScriptDTO.getColumnType());
    	addDefaultValueElement.setAttributeNode(columnDataType);
    	
    	Attr defaultValue = document.createAttribute("defaultValue");
    	defaultValue.setValue(addDefaultValueScriptDTO.getDefaultValue());
    	addDefaultValueElement.setAttributeNode(defaultValue);
    	
    	/*
    	 * Rollback element
    	 */
    	Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		//append rolback child
		Element dropDefaultValueRollback = document.createElement("dropDefaultValue");
    	//add modify column data type element to rollback
		rollbackElement.appendChild(dropDefaultValueRollback);
    	
    	Attr schemaNameRollback = document.createAttribute("schemaName");
    	schemaNameRollback.setValue(addDefaultValueScriptDTO.getSchemaName());
    	dropDefaultValueRollback.setAttributeNode(schemaNameRollback);
    	
    	Attr tableNameRollback = document.createAttribute("tableName");
    	tableNameRollback.setValue(addDefaultValueScriptDTO.getTableName());
    	dropDefaultValueRollback.setAttributeNode(tableNameRollback);        	
    	
    	Attr columnNameRollback = document.createAttribute("columnName");
    	columnNameRollback.setValue(addDefaultValueScriptDTO.getColumnName());
    	dropDefaultValueRollback.setAttributeNode(columnNameRollback);
    	
    	Attr columnDataTypeRollback = document.createAttribute("columnDataType");
    	columnDataTypeRollback.setValue(addDefaultValueScriptDTO.getColumnType());
    	dropDefaultValueRollback.setAttributeNode(columnDataTypeRollback);
    	
    	return changeSet;
	}
	
	/*
	 * Method for generate an Add Default Value Script
	 */
	@Override
	public String generateAddDefaultValueLiquibaseXMLScript(AddDefaultValueScriptDTO addDefaultValueScriptDTO) {
		String addDefaultValueXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(addDefaultValueScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateAddDefaultValueChangeSet(document, addDefaultValueScriptDTO);
	        manageChangeSet(addDefaultValueScriptDTO,  document, changeSet);
	        
        	/*
			 * generate XML script
			 */
        	addDefaultValueXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		
		return addDefaultValueXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Drop Default Value Script
	 */
	private Element generateDropDefaultValueChangeSet(Document document, DropDefaultValueScriptDTO dropDefaultValueScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, dropDefaultValueScriptDTO);
        
        /*
         * Pre-condition element
         */
        Element preConditionElement = createPreConditionElement(document, dropDefaultValueScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
    	
        //add preCondition child
    	//column exists
    	Element columnExistsElement = document.createElement("columnExists");
    	preConditionElement.appendChild(columnExistsElement);
    	
    	Attr schemaNamePreCond = document.createAttribute("schemaName");
    	schemaNamePreCond.setValue(dropDefaultValueScriptDTO.getSchemaName());
    	columnExistsElement.setAttributeNode(schemaNamePreCond);
    	
    	Attr tableNamePreCond = document.createAttribute("tableName");
    	tableNamePreCond.setValue(dropDefaultValueScriptDTO.getTableName());
    	columnExistsElement.setAttributeNode(tableNamePreCond);
    	
    	Attr columnNamePreCond = document.createAttribute("columnName");
    	columnNamePreCond.setValue(dropDefaultValueScriptDTO.getColumnName());
    	columnExistsElement.setAttributeNode(columnNamePreCond);
    	
    	/*
    	 * Drop Default Value Element
    	 */
    	Element addDefaultValueElement = document.createElement("dropDefaultValue");
    	changeSet.appendChild(addDefaultValueElement);
    	
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(dropDefaultValueScriptDTO.getSchemaName());
    	addDefaultValueElement.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(dropDefaultValueScriptDTO.getTableName());
    	addDefaultValueElement.setAttributeNode(tableName);
    	
    	Attr columnName = document.createAttribute("columnName");
    	columnName.setValue(dropDefaultValueScriptDTO.getColumnName());
    	addDefaultValueElement.setAttributeNode(columnName);
    	
    	Attr columnDataType = document.createAttribute("columnDataType");
    	columnDataType.setValue(dropDefaultValueScriptDTO.getColumnType());
    	addDefaultValueElement.setAttributeNode(columnDataType);
    	
    	/*
    	 * Rollback element
    	 */
    	Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		//append rolback child
		Element addDefaultValueRollback = document.createElement("addDefaultValue");
		rollbackElement.appendChild(addDefaultValueRollback);
    	
    	Attr schemaNameRollback = document.createAttribute("schemaName");
    	schemaNameRollback.setValue(dropDefaultValueScriptDTO.getSchemaName());
    	addDefaultValueRollback.setAttributeNode(schemaNameRollback);
    	
    	Attr tableNameRollback = document.createAttribute("tableName");
    	tableNameRollback.setValue(dropDefaultValueScriptDTO.getTableName());
    	addDefaultValueRollback.setAttributeNode(tableNameRollback);        	
    	
    	Attr columnNameRollback = document.createAttribute("columnName");
    	columnNameRollback.setValue(dropDefaultValueScriptDTO.getColumnName());
    	addDefaultValueRollback.setAttributeNode(columnNameRollback);
    	
    	Attr columnDataTypeRollback = document.createAttribute("columnDataType");
    	columnDataTypeRollback.setValue(dropDefaultValueScriptDTO.getColumnType());
    	addDefaultValueRollback.setAttributeNode(columnDataTypeRollback);
    	
    	Attr defaultValueRollback = document.createAttribute("defaultValue");
    	defaultValueRollback.setValue(dropDefaultValueScriptDTO.getDefaultValue());
    	addDefaultValueRollback.setAttributeNode(defaultValueRollback);
		
		return changeSet;
	}
	
	/*
	 * Method for generate an Drop Default Value Script
	 */
	@Override
	public String generateDropDefaultValueLiquibaseXMLScript(DropDefaultValueScriptDTO dropDefaultValueScriptDTO) {
		String dropDefaultValueXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(dropDefaultValueScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateDropDefaultValueChangeSet(document, dropDefaultValueScriptDTO);
	        manageChangeSet(dropDefaultValueScriptDTO,  document, changeSet);
		
        	/*
			 * generate XML script
			 */
        	dropDefaultValueXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		
		return dropDefaultValueXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Add Foreign Key Constraint Script
	 */
	private Element generateAddForeignKeyConstraintChangeSet(Document document, AddForeignKeyConstraintScriptDTO addForeignKeyConstraintScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, addForeignKeyConstraintScriptDTO);
        
        /*
         * Pre-condition element
         */
        Element preConditionElement = createPreConditionElement(document, addForeignKeyConstraintScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
    	
    	//add preCondition child
    	//foreign key constraint not exists
    	Element notElement = document.createElement("not");
    	preConditionElement.appendChild(notElement);
    	
    	Element foreignKeyConstraintExistsElement = document.createElement("foreignKeyConstraintExists");
    	notElement.appendChild(foreignKeyConstraintExistsElement);
    	
    	Attr schemaNamePreCond = document.createAttribute("schemaName");
    	schemaNamePreCond.setValue(addForeignKeyConstraintScriptDTO.getBaseSchemaName());
    	foreignKeyConstraintExistsElement.setAttributeNode(schemaNamePreCond);
    	
    	Attr foreignKeyNamePreCond = document.createAttribute("foreignKeyName");
    	foreignKeyNamePreCond.setValue(addForeignKeyConstraintScriptDTO.getConstraintName());
    	foreignKeyConstraintExistsElement.setAttributeNode(foreignKeyNamePreCond);
    	
    	/*
    	 * Add Foreign Key Constraint Element
    	 */
    	Element addForeignKeyConstraintElement = document.createElement("addForeignKeyConstraint");
    	//append foreign key to changeSet
    	changeSet.appendChild(addForeignKeyConstraintElement);
    	
    	//add foreign key attributes
    	Attr baseSchemaName = document.createAttribute("baseTableSchemaName");
    	baseSchemaName.setValue(addForeignKeyConstraintScriptDTO.getBaseSchemaName());
    	addForeignKeyConstraintElement.setAttributeNode(baseSchemaName);
    	
    	Attr baseTableName = document.createAttribute("baseTableName");
    	baseTableName.setValue(addForeignKeyConstraintScriptDTO.getBaseTableName());
    	addForeignKeyConstraintElement.setAttributeNode(baseTableName);
    	
    	Attr baseColumnName = document.createAttribute("baseColumnNames");
    	baseColumnName.setValue(addForeignKeyConstraintScriptDTO.getBaseColumnName());
    	addForeignKeyConstraintElement.setAttributeNode(baseColumnName);
    	
    	Attr constraintName = document.createAttribute("constraintName");
    	constraintName.setValue(addForeignKeyConstraintScriptDTO.getConstraintName());
    	addForeignKeyConstraintElement.setAttributeNode(constraintName);
    	
    	Attr onDelete = document.createAttribute("onDelete");
    	onDelete.setValue(addForeignKeyConstraintScriptDTO.getOnDelete());
    	addForeignKeyConstraintElement.setAttributeNode(onDelete);
    	
    	Attr onUpdate  = document.createAttribute("onUpdate");
    	onUpdate.setValue(addForeignKeyConstraintScriptDTO.getOnUpdate());
    	addForeignKeyConstraintElement.setAttributeNode(onUpdate);
    	
    	Attr referencedColumnNames  = document.createAttribute("referencedColumnNames");
    	referencedColumnNames.setValue(addForeignKeyConstraintScriptDTO.getReferencedColumnName());
    	addForeignKeyConstraintElement.setAttributeNode(referencedColumnNames);
    	
    	Attr referencedTableName  = document.createAttribute("referencedTableName");
    	referencedTableName.setValue(addForeignKeyConstraintScriptDTO.getReferencedTableName());
    	addForeignKeyConstraintElement.setAttributeNode(referencedTableName);
    	
    	Attr referencedTableSchemaName  = document.createAttribute("referencedTableSchemaName");
    	referencedTableSchemaName.setValue(addForeignKeyConstraintScriptDTO.getReferencedSchemaName());
    	addForeignKeyConstraintElement.setAttributeNode(referencedTableSchemaName);
    	
    	Attr validate  = document.createAttribute("validate");
    	validate.setValue("true");
    	addForeignKeyConstraintElement.setAttributeNode(validate);
    	
    	/*
    	 * Rollback Element
    	 */
    	Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		//append rolback child
		Element dropForeignKeyConstraintRollback = document.createElement("dropForeignKeyConstraint");
		rollbackElement.appendChild(dropForeignKeyConstraintRollback);
    	
    	Attr schemaNameRollback = document.createAttribute("baseTableSchemaName");
    	schemaNameRollback.setValue(addForeignKeyConstraintScriptDTO.getBaseSchemaName());
    	dropForeignKeyConstraintRollback.setAttributeNode(schemaNameRollback);
    	
    	Attr tableNameRollback = document.createAttribute("baseTableName");
    	tableNameRollback.setValue(addForeignKeyConstraintScriptDTO.getBaseTableName());
    	dropForeignKeyConstraintRollback.setAttributeNode(tableNameRollback);
    	
    	Attr constraintNameRollback = document.createAttribute("constraintName");
    	constraintNameRollback.setValue(addForeignKeyConstraintScriptDTO.getConstraintName());
    	dropForeignKeyConstraintRollback.setAttributeNode(constraintNameRollback);
		
		return changeSet;
	}
	
	/*
	 * Method for generate an Add Foreign Key Constraint Script
	 */
	@Override
	public String generateAddForeignKeyConstraintLiquibaseXMLScript(AddForeignKeyConstraintScriptDTO addForeignKeyConstraintScriptDTO) {
		String addForeignKeyConstraintXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(addForeignKeyConstraintScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateAddForeignKeyConstraintChangeSet(document, addForeignKeyConstraintScriptDTO);
	        manageChangeSet(addForeignKeyConstraintScriptDTO,  document, changeSet);
        	
        	/*
			 * generate XML script
			 */
        	addForeignKeyConstraintXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		
		return addForeignKeyConstraintXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Drop Foreign Key Constraint Script
	 */
	private Element generateDropForeignKeyConstraintChangeSet(Document document, DropForeignKeyConstraintScriptDTO dropForeignKeyConstraintScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, dropForeignKeyConstraintScriptDTO);
        
        /*
         * Pre-condition element
         */
        Element preConditionElement = createPreConditionElement(document, dropForeignKeyConstraintScriptDTO);
    	//add preCondition element to changeSet
    	changeSet.appendChild(preConditionElement);
    	
    	//add preCondition child
    	//foreign key constraint exists        	
    	Element foreignKeyConstraintExistsElement = document.createElement("foreignKeyConstraintExists");
    	preConditionElement.appendChild(foreignKeyConstraintExistsElement);
    	
    	Attr schemaNamePreCond = document.createAttribute("schemaName");
    	schemaNamePreCond.setValue(dropForeignKeyConstraintScriptDTO.getBaseSchemaName());
    	foreignKeyConstraintExistsElement.setAttributeNode(schemaNamePreCond);
    	
    	Attr foreignKeyNamePreCond = document.createAttribute("foreignKeyName");
    	foreignKeyNamePreCond.setValue(dropForeignKeyConstraintScriptDTO.getConstraintName());
    	foreignKeyConstraintExistsElement.setAttributeNode(foreignKeyNamePreCond);
    	
    	/*
    	 * Drop Foreign Key Constraint Element
    	 */
    	Element dropForeignKeyConstraintElement = document.createElement("dropForeignKeyConstraint");
    	//append drop foreign key to changeSet
    	changeSet.appendChild(dropForeignKeyConstraintElement);
    	
    	//drop foreign key attributes
    	Attr baseSchemaName = document.createAttribute("baseTableSchemaName");
    	baseSchemaName.setValue(dropForeignKeyConstraintScriptDTO.getBaseSchemaName());
    	dropForeignKeyConstraintElement.setAttributeNode(baseSchemaName);
    	
    	Attr baseTableName = document.createAttribute("baseTableName");
    	baseTableName.setValue(dropForeignKeyConstraintScriptDTO.getBaseTableName());
    	dropForeignKeyConstraintElement.setAttributeNode(baseTableName);
    	
    	Attr constraintName = document.createAttribute("constraintName");
    	constraintName.setValue(dropForeignKeyConstraintScriptDTO.getConstraintName());
    	dropForeignKeyConstraintElement.setAttributeNode(constraintName);
    	
    	/*
    	 * Rollback Element
    	 */	
		KeyColumnMetadata keyColumnMetadata = keyColumnMetadataRepository.getKeyColumnForeignKeyConstraintsByConstraintNameAndSchema(dropForeignKeyConstraintScriptDTO.getConstraintName(), dropForeignKeyConstraintScriptDTO.getBaseSchemaName());
		if(keyColumnMetadata != null) {
			Element rollbackElement = document.createElement("rollback");
    		changeSet.appendChild(rollbackElement);
    		
    		//append rolback child
    		Element addForeignKeyConstraintRollback = document.createElement("addForeignKeyConstraint");
    		rollbackElement.appendChild(addForeignKeyConstraintRollback);
    		
    		Attr schemaNameRollback = document.createAttribute("baseTableSchemaName");
        	schemaNameRollback.setValue(keyColumnMetadata.getBaseTableSchema());
        	addForeignKeyConstraintRollback.setAttributeNode(schemaNameRollback);
        	
        	Attr tableNameRollback = document.createAttribute("baseTableName");
        	tableNameRollback.setValue(keyColumnMetadata.getBaseTableName());
        	addForeignKeyConstraintRollback.setAttributeNode(tableNameRollback);
        	
        	Attr constraintNameRollback = document.createAttribute("constraintName");
        	constraintNameRollback.setValue(keyColumnMetadata.getConstraintName());
        	addForeignKeyConstraintRollback.setAttributeNode(constraintNameRollback);
        	
        	Attr referencedColumnNameRollback = document.createAttribute("referencedColumnNames");
        	referencedColumnNameRollback.setValue(keyColumnMetadata.getReferencedColumnName());
        	addForeignKeyConstraintRollback.setAttributeNode(referencedColumnNameRollback);
        	
        	Attr referencedTableNameRollback = document.createAttribute("referencedTableName");
        	referencedTableNameRollback.setValue(keyColumnMetadata.getReferencedTableName());
        	addForeignKeyConstraintRollback.setAttributeNode(referencedTableNameRollback);
        	
        	Attr referencedTableSchemaNameRollback = document.createAttribute("referencedTableSchemaName");
        	referencedTableSchemaNameRollback.setValue(keyColumnMetadata.getReferencedTableSchema());
        	addForeignKeyConstraintRollback.setAttributeNode(referencedTableSchemaNameRollback);
        	
        	Attr onDeleteRollback = document.createAttribute("onDelete");
        	onDeleteRollback.setValue(keyColumnMetadata.getOnDeleteClause());
        	addForeignKeyConstraintRollback.setAttributeNode(onDeleteRollback);
        	
        	Attr onUpdateRollback = document.createAttribute("onUpdate");
        	onUpdateRollback.setValue(keyColumnMetadata.getOnUpdateClause());
        	addForeignKeyConstraintRollback.setAttributeNode(onUpdateRollback);
		}
		
		return changeSet;
	}
	
	/*
	 * Method for generate an Drop Foreign Key Constraint Script
	 */
	@Override
	public String generateDropForeignKeyConstraintLiquibaseXMLScript(DropForeignKeyConstraintScriptDTO dropForeignKeyConstraintScriptDTO) {
		String dropForeignKeyConstraintXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(dropForeignKeyConstraintScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateDropForeignKeyConstraintChangeSet(document, dropForeignKeyConstraintScriptDTO);
	        manageChangeSet(dropForeignKeyConstraintScriptDTO,  document, changeSet);
	        
        	/*
			 * generate XML script
			 */
        	dropForeignKeyConstraintXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		
		return dropForeignKeyConstraintXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Delete Query Script
	 */
	private Element generateDeleteDataChangeSet(Document document, DeleteDataScriptDTO deleteDataScriptDTO) {
		//changeSet element
        Element changeSet = createChangeSetElement(document, deleteDataScriptDTO);
        
    	/*
    	 * DELETE Element
    	 */
    	Element deleteElement = document.createElement("delete");
    	//append DELETE to changeSet
    	changeSet.appendChild(deleteElement);
    	
    	//DELETE element attributes
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(deleteDataScriptDTO.getSchemaName());
    	deleteElement.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(deleteDataScriptDTO.getTableName());
    	deleteElement.setAttributeNode(tableName);
    	
    	//append WHERE condition element to DELETE
    	if(!deleteDataScriptDTO.getWhereCondition().equals("")) {
    		Element whereCondition = document.createElement("where");
    		deleteElement.appendChild(whereCondition);
    		
    		whereCondition.appendChild(document.createTextNode(deleteDataScriptDTO.getWhereCondition()));
    	}
    	
    	/*
    	 * Rollback Element
    	 */	    	
    	String rollBackQuery = "SELECT * FROM " + deleteDataScriptDTO.getSchemaName() + "." + deleteDataScriptDTO.getTableName();
    	if(!deleteDataScriptDTO.getWhereCondition().equals("")) {
    		rollBackQuery += " WHERE " + deleteDataScriptDTO.getWhereCondition();
    	}
    	Set<Map<String, String>> resultSetRollBack = genericEntityDAO.selectQuery(rollBackQuery);
    		
    	if(resultSetRollBack != null) {
    		Element rollbackElement = document.createElement("rollback");
    		changeSet.appendChild(rollbackElement);
    		
	    	for(Map<String, String> c : resultSetRollBack) {  
	    		Element insertElement = document.createElement("insert");
		    	//append INSERT to rollback
	    		rollbackElement.appendChild(insertElement);
	    		
	    		//INSERT element attributes
		    	Attr schemaNameRollBack = document.createAttribute("schemaName");
		    	schemaNameRollBack.setValue(deleteDataScriptDTO.getSchemaName());
		    	insertElement.setAttributeNode(schemaNameRollBack);
		    	
		    	Attr tableNameRollBack = document.createAttribute("tableName");
		    	tableNameRollBack.setValue(deleteDataScriptDTO.getTableName());
		    	insertElement.setAttributeNode(tableNameRollBack);
		    	
	    		for(String s : c.keySet()) {
	    			if(c.get(s) != null) {
	    				Element columnElement = document.createElement("column");
		        		insertElement.appendChild(columnElement);
		        		
		        		Attr columnName = document.createAttribute("name");
		        		columnName.setValue(s);
		        		columnElement.setAttributeNode(columnName);
		        		
		        		columnElement.appendChild(document.createTextNode(c.get(s)));
	    			}
	    		}
	    	}
    	}
    	
    	return changeSet;
	}
	
	/*
	 * Method for generate a Delete Query Script
	 */
	@Override
	public String generateDeleteDataLiquibaseXMLScript(DeleteDataScriptDTO deleteDataScriptDTO) {
		String deleteQueryXMLScript = "";
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(deleteDataScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateDeleteDataChangeSet(document, deleteDataScriptDTO);
	        manageChangeSet(deleteDataScriptDTO,  document, changeSet);
			
        	/*
			 * generate XML script
			 */
    		deleteQueryXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		
		return deleteQueryXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for Insert Query Script
	 */
	private Element generateInsertDataChangeSet(Document document, InsertDataScriptDTO insertDataScriptDTO) {
		Set<ColumnMetadata> columnsMetadatas = columnMetadataRepository.getAllDBColumnsByTableAndSchema(insertDataScriptDTO.getSchemaName(), insertDataScriptDTO.getTableName());
		
		//changeSet element
        Element changeSet = createChangeSetElement(document, insertDataScriptDTO);
        
        /*
         * Pre-condition element
         */
        String whereCondition = "";
        int idFieldNumber = 0;
		for(ColumnMetadata c : columnsMetadatas) {
			if(c.getColumnKey().equals("PRI")) {
				idFieldNumber++;
				if(idFieldNumber > 1) {
					whereCondition += " AND ";
				}
				whereCondition += c.getColumnName() + " = \"" + insertDataScriptDTO.getColumns().get(c.getColumnName()) +"\"";
			}
		}
		if(!whereCondition.equals("")) {
			Element preConditionElement = createPreConditionElement(document, insertDataScriptDTO);
        	//add preCondition element to changeSet
        	changeSet.appendChild(preConditionElement);
        	
        	 //add sql check
	        Element sqlCheckElement = document.createElement("sqlCheck");
	        preConditionElement.appendChild(sqlCheckElement);
    		
    		//add attributes to sql check
    		Attr expectedResultAttribute = document.createAttribute("expectedResult");
    		expectedResultAttribute.setValue("0");
    		sqlCheckElement.setAttributeNode(expectedResultAttribute);        		  	
    		
    		String queryRestriction = "SELECT COUNT(*) FROM " + insertDataScriptDTO.getSchemaName() + "." + insertDataScriptDTO.getTableName() + " WHERE ( "+whereCondition+" )";
    		sqlCheckElement.appendChild(document.createTextNode(queryRestriction));
		}
        
        /*
         * INSERT ELEMENT
         */
		Element insertElement = document.createElement("insert");
    	//append INSERT to changeSet
    	changeSet.appendChild(insertElement);
    	
    	//INSERT element attributes
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(insertDataScriptDTO.getSchemaName());
    	insertElement.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(insertDataScriptDTO.getTableName());
    	insertElement.setAttributeNode(tableName);
    	
    	//append column value to insert
    	for(ColumnMetadata c : columnsMetadatas) {
    		if(!insertDataScriptDTO.getColumns().get(c.getColumnName()).equals("")) {
    			Element columnElement = document.createElement("column");
        		insertElement.appendChild(columnElement);
        		
        		Attr columnName = document.createAttribute("name");
        		columnName.setValue(c.getColumnName());
        		columnElement.setAttributeNode(columnName);
        		
        		columnElement.appendChild(document.createTextNode(insertDataScriptDTO.getColumns().get(c.getColumnName())));
    		}
    	}
    	
    	/*
    	 * ROLLBACK ELEMENT
    	 */
    	Element rollbackElement = document.createElement("rollback");
		changeSet.appendChild(rollbackElement);
		
		//append rolback child
		Element deleteRollback = document.createElement("delete");
		rollbackElement.appendChild(deleteRollback);
    	
    	Attr schemaNameRollback = document.createAttribute("schemaName");
    	schemaNameRollback.setValue(insertDataScriptDTO.getSchemaName());
    	deleteRollback.setAttributeNode(schemaNameRollback);
    	
    	Attr tableNameRollback = document.createAttribute("tableName");
    	tableNameRollback.setValue(insertDataScriptDTO.getTableName());
    	deleteRollback.setAttributeNode(tableNameRollback);
    	
    	Element whereConditionElementRollback = document.createElement("where");
    	deleteRollback.appendChild(whereConditionElementRollback);
    	
    	String whereConditionRollback = "";
    	for(ColumnMetadata c : columnsMetadatas) {
    		if(!insertDataScriptDTO.getColumns().get(c.getColumnName()).equals("")) {
    			whereConditionRollback += c.getColumnName() + " = \"" + insertDataScriptDTO.getColumns().get(c.getColumnName())  + "\" AND ";
    		}
    	}
    	whereConditionRollback = whereConditionRollback.substring(0, whereConditionRollback.length() - 5);
    	whereConditionElementRollback.appendChild(document.createTextNode(whereConditionRollback));
		
		return changeSet;
	}
	
	/*
	 * Method for generate a Insert Query Script
	 */
	@Override
	public String generateInsertDataLiquibaseXMLScript(InsertDataScriptDTO insertDataScriptDTO) {
		String insertDataXMLScript = "";
		
		try {	        
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(insertDataScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateInsertDataChangeSet(document, insertDataScriptDTO);
	        manageChangeSet(insertDataScriptDTO,  document, changeSet);
        	
    		/*
			 * generate XML script
			 */
    		insertDataXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		
		return insertDataXMLScript;
	}
	
	/*
	 * Create Element ChangeSet for UPDATE data Script
	 */
	private Element generateUpdateDataChangeSet(Document document, UpdateDataScriptDTO updateDataScriptDTO) {
		Set<ColumnMetadata> columnsMetadatas = columnMetadataRepository.getAllDBColumnsByTableAndSchema(updateDataScriptDTO.getSchemaName(), updateDataScriptDTO.getTableName());

		//changeSet element
        Element changeSet = createChangeSetElement(document, updateDataScriptDTO);
        
        /*
         * Append UPDATE element
         */
        Element updateElement = document.createElement("update");
    	//append UPDATE to changeSet
    	changeSet.appendChild(updateElement);
    	
    	//UPDATE element attributes
    	Attr schemaName = document.createAttribute("schemaName");
    	schemaName.setValue(updateDataScriptDTO.getSchemaName());
    	updateElement.setAttributeNode(schemaName);
    	
    	Attr tableName = document.createAttribute("tableName");
    	tableName.setValue(updateDataScriptDTO.getTableName());
    	updateElement.setAttributeNode(tableName);
    	
    	//append column value to update element
    	for(ColumnMetadata c : columnsMetadatas) {
    		if(!updateDataScriptDTO.getColumns().get(c.getColumnName()).equals("")) {
    			Element columnElement = document.createElement("column");
    			updateElement.appendChild(columnElement);
        		
        		Attr columnName = document.createAttribute("name");
        		columnName.setValue(c.getColumnName());
        		columnElement.setAttributeNode(columnName);
        		
        		columnElement.appendChild(document.createTextNode(updateDataScriptDTO.getColumns().get(c.getColumnName())));
    		}
    	}
    	
    	//append WHERE condition element to UPDATE
    	if(!updateDataScriptDTO.getWhereCondition().equals("")) {
    		Element whereCondition = document.createElement("where");
    		updateElement.appendChild(whereCondition);
    		
    		whereCondition.appendChild(document.createTextNode(updateDataScriptDTO.getWhereCondition()));
    	}
		
		return changeSet;
	}
	
	/*
	 * Method for generate a Update Query Script
	 */
	@Override
	public String generateUpdateDataLiquibaseXMLScript(UpdateDataScriptDTO updateDataScriptDTO) {
		String updateDataXMLScript = "";
		
		
		try {
			ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
			Document document;
			
			if(updateDataScriptDTO.getAddToChangelog() && changeLog!=null && changeLog.changeLogExists()) {
				document = changeLog.getChangeLogDocument();
			}else {
		        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			
			/*
			 * generate changeSet element
			 */
	        Element changeSet = generateUpdateDataChangeSet(document, updateDataScriptDTO);
	        manageChangeSet(updateDataScriptDTO,  document, changeSet);
	        
	        /*
			 * generate XML script
			 */
	        updateDataXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
	        
		return updateDataXMLScript;
	}
}
