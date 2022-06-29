package it.alten.tirocinio.liquibaseChangeElement;

import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * Class representing a Liquibase ChangeLog
 */
public class ChangeLog {
	private String changeLogId;
	private Document document;
	private Element changeLog;
	private Set<ChangeSet> changeSets;
	
	/*
	 * Constructor
	 */
	public ChangeLog() {
		this.document = null;
		this.changeLog = null;
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
	 * Setter methods
	 */
	public void setChangeLogId(String changeLogId) {
		this.changeLogId = changeLogId;
	}
	
	public void setChangeLogDocument(Document document) {
		this.document = document;
	}
	
	/*
	 * Create the changeLog element
	 */
	public void createChangeLog(Element newChangeLog) {
		this.changeLog = newChangeLog;
	}
	
	
	/*
	 * Remove all ChangeSet from set
	 */
	public void deletAllChangeSet() {
		for(ChangeSet c : changeSets) {
			changeSets.remove(c);
		}
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
	public boolean addChangeSetToChangeLog(ChangeSet changeSet) {
		return changeSets.add(changeSet);
	}
}
