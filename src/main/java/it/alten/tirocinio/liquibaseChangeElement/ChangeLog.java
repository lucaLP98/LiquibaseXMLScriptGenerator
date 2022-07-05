package it.alten.tirocinio.liquibaseChangeElement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * Class representing a Liquibase ChangeLog
 */
//@Component
public class ChangeLog implements Cloneable{
	private Document document;
	private Element changeLog;
	private Set<ChangeSet> changeSets;
	
	private boolean created;
	
	/*
	 * Constructor
	 */
	public ChangeLog() {
		this.document = null;
		this.changeLog = null;
		created = false;
		this.changeSets = new HashSet<>();
	}
	
	/*
	 * Getter methods
	 */	
	public Document getChangeLogDocument() {
		return document;
	}
	
	public Element getChangeLogElement() {
		return changeLog;
	}
	
	public Set<ChangeSet> getChangeSets() {
		return changeSets;
	}
	
	public boolean changeLogExists() {
		return created;
	}
	
	/*
	 * Setter methods
	 */	
	public void setChangeLogDocument(Document document) {
		this.document = document;
	}
	
	public void setCreated(boolean created) {
		this.created = created;
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
		if(created) {
			Iterator<ChangeSet> iter = changeSets.iterator();
			while(iter.hasNext()) {
				iter.next();
				iter.remove();
			}
		}
	}
	
	/*
	 * Delete a changeSet from Set by his Id
	 */
	public boolean deleteChangeSetFromChangeLog(String changeSetId) {
		if(!created)	return false;
		
		ChangeSet changeSetToDelete = null;
		for(ChangeSet c : changeSets) {
			if(c.getChangeSetId().equals(changeSetId)) {
				changeSetToDelete = c;
			}
		}
		
		boolean deleted;
		if(changeSetToDelete != null) {
			deleted = changeSets.remove(changeSetToDelete);
			if(deleted)	changeLog.removeChild(changeSetToDelete.getChangeset());
		}else {
			deleted = false;
		}
		
		return deleted;
	}
	
	/*
	 * Add a new changeSet to set
	 */
	public boolean addChangeSetToChangeLog(ChangeSet newChangeSet) {
		if(!created)	return false;
		
		boolean added = changeSets.add(newChangeSet);
		if(added) {
			changeLog.appendChild(newChangeSet.getChangeset());
		}
		return added;
	}
}
