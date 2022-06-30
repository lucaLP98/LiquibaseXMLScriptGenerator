package it.alten.tirocinio.services.concrete;

import java.io.StringWriter;

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

import it.alten.tirocinio.liquibaseChangeElement.ChangeLog;
import it.alten.tirocinio.liquibaseChangeElement.ChangeSet;
import it.alten.tirocinio.services.ChangeLogService;

@Service
public class ChangeLogServiceConcrete implements ChangeLogService {
	@Autowired
	private ApplicationContext context;
	
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
	 * Method which allow to create new changeLog
	 */
	@Override
	public boolean createNewChangeLog(String changeLogId) {
		Document document;
		ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
		
		if(changeLog.getChangeLogDocument() != null) {
			System.out.println("changelog not created - already exists");
			return false;
		}
		
		boolean ret;
		try {
	        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	        changeLog.setChangeLogDocument(document);
	        changeLog.setChangeLogId(changeLogId);
	           
	        //create ChangeLog element
	        Element newChangeLog;
	        newChangeLog =  document.createElement("databaseChangeLog");
			
			//add changeLog's attributes
			Attr xmlns = document.createAttribute("xmlns");
			xmlns.setValue("http://www.liquibase.org/xml/ns/dbchangelog");
			newChangeLog.setAttributeNode(xmlns);
			
			Attr xsi = document.createAttribute("xmlns:xsi");
			xsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
			newChangeLog.setAttributeNode(xsi);
			
			Attr pro = document.createAttribute("xmlns:pro");
			pro.setValue("http://www.liquibase.org/xml/ns/pro");
			newChangeLog.setAttributeNode(pro);
			
			Attr schemaLocation = document.createAttribute("xsi:schemaLocation");
			schemaLocation.setValue("http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.6.xsd\r\n http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-4.6.xsd");
			newChangeLog.setAttributeNode(schemaLocation);
			
			Attr id = document.createAttribute("changeLogId");
			id.setValue(changeLogId);
			newChangeLog.setAttributeNode(id);
	        
			changeLog.createChangeLog(newChangeLog);
			changeLog.getChangeLogDocument().appendChild(changeLog.getChangeLogElement());
			
			System.out.println("changelog created");
			
	        ret = true;
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            System.out.println("changelog not created");
            ret = false;
        }
		
		return ret;
	}

	/*
	 * Method which allow to close a existing changeLog
	 */
	@Override
	public void closeChangeLog() {
		ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
		
		changeLog.setChangeLogDocument(null);
		changeLog.setChangeLogId(null);
		changeLog.deletAllChangeSet();
		
		System.out.println("changelog closed");
	}

	/*
	 * Method which return changelog in String format
	 */
	@Override
	public String printChangeLog() {
		ChangeLog changeLog = (ChangeLog)context.getBean("sessionChangeLog");
		String script;

		if(changeLog != null && changeLog.getChangeLogDocument() != null) {
			script = generateXMLScriptToString(changeLog.getChangeLogDocument());
		}else {
			script = "There isn't a open LiquibaseChangeLog.";
		}
		
		return script;	
	}
}
