package it.alten.tirocinio.services.concrete;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import it.alten.tirocinio.liquibaseChangeElement.ChangeLog;
import it.alten.tirocinio.services.ChangeLogService;

@Service
public class ChangeLogServiceConcrete implements ChangeLogService {
	@Autowired
	private ApplicationContext context;

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

}
