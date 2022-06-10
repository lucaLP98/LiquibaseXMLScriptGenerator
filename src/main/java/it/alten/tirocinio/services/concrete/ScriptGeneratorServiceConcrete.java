package it.alten.tirocinio.services.concrete;

import java.io.StringWriter;

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

import it.alten.tirocinio.api.DTO.scriptDTO.DropTableScriptDTO;
import it.alten.tirocinio.services.ScriptGeneratorService;

@Service
public class ScriptGeneratorServiceConcrete implements ScriptGeneratorService {
	
	@Override
	public String generateDropTableLiquibaseXMLScript(DropTableScriptDTO dropTableScriptDTO) {
		String dropTableXMLScript = "";
		
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
	        Document document = documentBuilder.newDocument();
	        
	        //changeSet element
	        Element changeSet = document.createElement("changeSet");
	        document.appendChild(changeSet);
	        
	        //add changeSet attribute
        	Attr changeSetAuthor = document.createAttribute("author");
        	changeSetAuthor.setValue(dropTableScriptDTO.getAuthor());
        	changeSet.setAttributeNode(changeSetAuthor);
        	
        	Attr changeSetId = document.createAttribute("id");
        	changeSetId.setValue(dropTableScriptDTO.getIdChangeset());
        	changeSet.setAttributeNode(changeSetId);
        	
        	//add dropTable element
        	Element dropTableElement = document.createElement("dropTable");
        	
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
        	
        	//DropTable element to changeSet element
        	changeSet.appendChild(dropTableElement);
        	
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
            dropTableXMLScript = streamResultString.getWriter().toString(); //it contain the XML script in format of String          
            /*
             * End script generation
             */
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
		
		return dropTableXMLScript;
	}

}
