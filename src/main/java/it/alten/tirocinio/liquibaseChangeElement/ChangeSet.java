package it.alten.tirocinio.liquibaseChangeElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ChangeSet{
	private final String changeSetId;
	private final Document document;
	private Element changeSet;
	
	/*
	 * Constructors
	 */
	public ChangeSet(Document document, String changeSetId) {
		this.document = document;
		this.changeSetId = changeSetId;
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
	public void setChangeSet(Element changeSet) {
		this.changeSet = changeSet;
	}
	
	/*
	 * Methods
	 */	
	@Override
	public boolean equals(Object o) {
		if(o == null)	return false;
		if(!(o instanceof ChangeSet))	return false;
		
		ChangeSet c = (ChangeSet)o;
		return (this.changeSetId.equals(c.changeSetId) && this.document==c.document);
	}
	
	@Override 
	public int hashCode() {
		return this.changeSetId.hashCode() ^ this.document.hashCode();
	}
}
