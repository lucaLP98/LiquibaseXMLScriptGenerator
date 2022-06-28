package it.alten.tirocinio.liquibaseChangeElement;

import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ChangeLog {
	private String changeLogId;
	private Document document;
	private Element changeLog;
	private Set<ChangeSet> changeSets;
	
	/*
	 * Constructor
	 */
	public ChangeLog() {
		try {
	        this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
		this.changeSets = new HashSet<>();
	}
	
	/*
	 * Getter methods
	 */
	public String getChangeLogId() {
		return changeLogId;
	}
	
	public Document getChangeLogDocument() {
		return document;
	}
	
	public Element getChangeLogElement() {
		return changeLog;
	}
	
	public Set<ChangeSet> getChangeSets() {
		return changeSets;
	}
	
	/*
	 * Create the changeLog element
	 */
	public void createChangeLog(String changeLogId) {
		this.changeLogId = changeLogId;
		
		//create ChangeLog element
		changeLog =  document.createElement("databaseChangeLog");
		
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
		
		Attr id = document.createAttribute("changeLogId");
		id.setValue(changeLogId);
		changeLog.setAttributeNode(id);
	}
	
	/*
	 * Delete a changeSet from Set by his Id
	 */
	public boolean deleteChangeSetFromChangeLog(String changeSetId) {
		ChangeSet toDelete = null;
		for(ChangeSet c : changeSets) {
			if(c.getChangeSetId().equals(changeSetId)) {
				toDelete = c;
			}
		}
		
		return changeSets.remove(toDelete);
	}
	
	/*
	 * Add a new changeSet to set
	 */
}
