package it.alten.tirocinio.liquibaseChangeElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ChangeSet {
	private String changeSetId;
	private Document document;
	private Element changeSet;
	
	public ChangeSet(Document document) {
		this.document = document;
	}
	
	/*
	 * Getter methods
	 */
	public String getChangeSetId() {
		return changeSetId;
	}
	
	public Element getChangeset() {
		return changeSet;
	}
	
	/*
	 * Setter methods
	 */
	public void setChangeSetId(String changeSetId) {
		this.changeSetId = changeSetId;
	}
	
	public void setChangeSet(Element changeSet) {
		this.changeSet = changeSet;
	}
}
