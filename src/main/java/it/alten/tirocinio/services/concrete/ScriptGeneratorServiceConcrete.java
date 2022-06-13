package it.alten.tirocinio.services.concrete;

import java.io.StringWriter;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import it.alten.tirocinio.api.DTO.scriptDTO.DropColumnScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.DropTableScriptDTO;
import it.alten.tirocinio.api.DTO.scriptDTO.ScriptDTO;
import it.alten.tirocinio.model.ColumnMetadata;
import it.alten.tirocinio.model.TableMetadata;
import it.alten.tirocinio.repository.ColumnMetadataRepository;
import it.alten.tirocinio.repository.TableMetadataRepository;
import it.alten.tirocinio.services.ScriptGeneratorService;

@Service
public class ScriptGeneratorServiceConcrete implements ScriptGeneratorService {
	
	private final TableMetadataRepository tableMetadataRepository;
	private final ColumnMetadataRepository columnMetadataRepository;
	
	public ScriptGeneratorServiceConcrete(TableMetadataRepository tableMetadataRepository, ColumnMetadataRepository columnMetadataRepository) {
		this.tableMetadataRepository = tableMetadataRepository;
		this.columnMetadataRepository = columnMetadataRepository;
	}
	
	/*
	 * Create the preCondition Element with its attributes
	 */
	private Element createPreConditionElement(Document document, ScriptDTO scriptDTO) {
		//create preConditionElement
		Element preConditionElement = document.createElement("preCondition");
		
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
    	Element constraintNullableRollback = document.createElement("constraints");
    	columnRollback.appendChild(constraintNullableRollback);
    	
    	//add constraint attribute
    	Attr nullableRolback = document.createAttribute("nullable");     
    	if(column.getIsNullable().equals("YES"))	nullableRolback.setValue("true");
    	else	nullableRolback.setValue("false");
    	constraintNullableRollback.setAttributeNode(nullableRolback); 
    	
    	if(column.getColumnKey().equals("PRI")) {
    		Element constraintPrimariKeyRollback = document.createElement("constraints");
        	columnRollback.appendChild(constraintPrimariKeyRollback);
    		Attr primaryKeyRolback = document.createAttribute("primaryKey");     
    		primaryKeyRolback.setValue("true");
    		constraintPrimariKeyRollback.setAttributeNode(primaryKeyRolback); 
    	}
    	
    	return columnRollback;
	}
	
	@Override
	public String generateDropTableLiquibaseXMLScript(DropTableScriptDTO dropTableScriptDTO) {
		String dropTableXMLScript = "";
		
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
	        Document document = documentBuilder.newDocument();
	        
	        //changeSet element
	        Element changeSet = document.createElement("changeSet");
	        //add changeSet element to document
	        document.appendChild(changeSet);
	        
	        //add changeSet attribute
        	Attr changeSetAuthor = document.createAttribute("author");
        	changeSetAuthor.setValue(dropTableScriptDTO.getAuthor());
        	changeSet.setAttributeNode(changeSetAuthor);
        	
        	Attr changeSetId = document.createAttribute("id");
        	changeSetId.setValue(dropTableScriptDTO.getIdChangeset());
        	changeSet.setAttributeNode(changeSetId);
        	
        	//add preCondition element
        	Element preConditionElement = createPreConditionElement(document, dropTableScriptDTO);
        	//add preCondition element to changeSet
        	changeSet.appendChild(preConditionElement);
        	
        	//add preCondition child
        	Element notExists = document.createElement("not");
        	preConditionElement.appendChild(notExists);
        	
        	Element tableExistsElement = document.createElement("tableExists");
        	notExists.appendChild(tableExistsElement);
        	
        	Attr schemaNamePreCond = document.createAttribute("schemaName");
        	schemaNamePreCond.setValue(dropTableScriptDTO.getTableSchema());
        	tableExistsElement.setAttributeNode(schemaNamePreCond);
        	
        	Attr tableNamePreCOnd = document.createAttribute("tableName");
        	tableNamePreCOnd.setValue(dropTableScriptDTO.getTableName());
        	tableExistsElement.setAttributeNode(tableNamePreCOnd);    	
        	
        	//add dropTable element
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
        	
        	//generate XML script
        	dropTableXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
		
		return dropTableXMLScript;
	}
	
	@Override
	public String generateDropColumnLiquibaseXMLScript(DropColumnScriptDTO dropColumnScriptDTO) {
		String dropColumnXMLScript = "";
		
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
	        Document document = documentBuilder.newDocument();
	        
	        //changeSet element
	        Element changeSet = document.createElement("changeSet");
	        //add changeSet element to document
	        document.appendChild(changeSet);
	        
	        //add changeSet attribute
        	Attr changeSetAuthor = document.createAttribute("author");
        	changeSetAuthor.setValue(dropColumnScriptDTO.getAuthor());
        	changeSet.setAttributeNode(changeSetAuthor);
        	
        	Attr changeSetId = document.createAttribute("id");
        	changeSetId.setValue(dropColumnScriptDTO.getIdChangeset());
        	changeSet.setAttributeNode(changeSetId);
        	
        	//add preCondition element
        	Element preConditionElement = createPreConditionElement(document, dropColumnScriptDTO);
        	//add preCondition element to changeSet
        	changeSet.appendChild(preConditionElement);
        	
        	//add preCondition child
        	Element notExists = document.createElement("not");
        	preConditionElement.appendChild(notExists);
        	
        	Element columnExistsElement = document.createElement("columnExists");
        	notExists.appendChild(columnExistsElement);
       	
        	Attr schemaNamePreCond = document.createAttribute("schemaName");
        	schemaNamePreCond.setValue(dropColumnScriptDTO.getTableSchema());
        	columnExistsElement.setAttributeNode(schemaNamePreCond);
        	
        	Attr tableNamePreCond = document.createAttribute("tableName");
        	tableNamePreCond.setValue(dropColumnScriptDTO.getTableName());
        	columnExistsElement.setAttributeNode(tableNamePreCond); 
        	
        	Attr columnNamePreCond = document.createAttribute("columnName");
        	columnNamePreCond.setValue(dropColumnScriptDTO.getColumnName());
        	columnExistsElement.setAttributeNode(columnNamePreCond); 
       	
        	//add dropColumn element
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
  
        	//generate XML script
        	dropColumnXMLScript = generateXMLScriptToString(document);
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
		
		return dropColumnXMLScript;
	}
}
