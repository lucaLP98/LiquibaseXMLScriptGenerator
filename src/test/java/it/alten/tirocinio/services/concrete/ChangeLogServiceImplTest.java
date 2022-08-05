package it.alten.tirocinio.services.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import it.alten.tirocinio.api.DTO.changeLogDTO.ChangeSetListDTO;
import it.alten.tirocinio.liquibaseChangeElement.ChangeLog;
import it.alten.tirocinio.liquibaseChangeElement.ChangeSet;

public class ChangeLogServiceImplTest {
	@Mock
	private ChangeLog changeLog;
	
	@InjectMocks
	private ChangeLogServiceImpl service;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	/*
	 * method removeChangeSet
	 */
	@Test
	public void removeChangeSetTest_notEmptyChangeSetId_changeLogCreated() {
		String changeSetIdToDelete = "cs1";
		
		when(changeLog.changeLogExists()).thenReturn(true);
		when(changeLog.deleteChangeSetFromChangeLog(changeSetIdToDelete)).thenReturn(true);
		
		assertTrue(service.removeChangeSet(changeSetIdToDelete));
	}
	
	@Test
	public void removeChangeSetTest_notEmptyChangeSetId_changeLogNotCreated() {
		String changeSetIdToDelete = "cs1";
		
		when(changeLog.changeLogExists()).thenReturn(false);
		when(changeLog.deleteChangeSetFromChangeLog(changeSetIdToDelete)).thenReturn(true);
		
		assertFalse(service.removeChangeSet(changeSetIdToDelete));
	}
	
	@Test
	public void removeChangeSetTest_nullChangeSetId_changeLogNotCreated() {
		String changeSetIdToDelete = null;
		
		when(changeLog.changeLogExists()).thenReturn(false);
		when(changeLog.deleteChangeSetFromChangeLog(changeSetIdToDelete)).thenReturn(true);
		
		assertFalse(service.removeChangeSet(changeSetIdToDelete));
	}
	
	@Test
	public void removeChangeSetTest_nullChangeSetId_changeLogCreated() {
		String changeSetIdToDelete = null;
		
		when(changeLog.changeLogExists()).thenReturn(true);
		when(changeLog.deleteChangeSetFromChangeLog(changeSetIdToDelete)).thenReturn(true);
		
		assertFalse(service.removeChangeSet(changeSetIdToDelete));
	}
	
	@Test
	public void removeChangeSetTest_emptyChangeSetId_changeLogCreated() {
		String changeSetIdToDelete = "";
		
		when(changeLog.changeLogExists()).thenReturn(true);
		when(changeLog.deleteChangeSetFromChangeLog(changeSetIdToDelete)).thenReturn(true);
		
		assertFalse(service.removeChangeSet(changeSetIdToDelete));
	}
	
	@Test
	public void removeChangeSetTest_emptyChangeSetId_changeLogNotCreated() {
		String changeSetIdToDelete = "";
		
		when(changeLog.changeLogExists()).thenReturn(false);
		when(changeLog.deleteChangeSetFromChangeLog(changeSetIdToDelete)).thenReturn(true);
		
		assertFalse(service.removeChangeSet(changeSetIdToDelete));
	}
	
	/*
	 * Method getAllChangeSet
	 */
	@Test
	public void getAllChangeSetTest_changeLogNotCreated() {
		when(changeLog.changeLogExists()).thenReturn(false);
		ChangeSetListDTO list = service.getAllChangeSet();
		assertEquals(0, list.getChangeSetList().size());
	}
	
	@Test
	public void getAllChangeSetTest_changeLogCreated() throws ParserConfigurationException {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		ChangeSet cs1 = new ChangeSet(document, "cs1");
		ChangeSet cs2 = new ChangeSet(document, "cs2");
		ChangeSet cs3 = new ChangeSet(document, "cs3");
		
		Set<ChangeSet> setTest = new HashSet<>();
		setTest.add(cs1);
		setTest.add(cs2);
		setTest.add(cs3);
		
		when(changeLog.changeLogExists()).thenReturn(true);
		when(changeLog.getChangeSets()).thenReturn(setTest);
		
		ChangeSetListDTO list = service.getAllChangeSet();
		assertEquals(3, list.getChangeSetList().size());
	}
	
	/*
	 * Method printChangeLog
	 */
	@Test
	public void printChangeLog_changeLogNotCreated() {
		String result = "There isn't a open LiquibaseChangeLog.";
		
		when(changeLog.changeLogExists()).thenReturn(false);
		
		assertEquals(result, service.printChangeLog(false));
	}
	
	@Test
	public void printChangeLog_changeLogCreated() throws ParserConfigurationException {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		
		Element changeLogElement =  document.createElement("databaseChangeLog");
		document.appendChild(changeLogElement);		
		Element changeSetElement = document.createElement("changeSet");
		changeLogElement.appendChild(changeSetElement);
    	Attr changeSetAuthorAttr = document.createAttribute("author");
    	changeSetAuthorAttr.setValue("test");
    	changeSetElement.setAttributeNode(changeSetAuthorAttr);
    	Attr changeSetIdAttr = document.createAttribute("id");
    	changeSetIdAttr.setValue("testId");
    	changeSetElement.setAttributeNode(changeSetIdAttr);
		
    	String result = "<databaseChangeLog><changeSet author=\"test\" id=\"testId\"/></databaseChangeLog>";

    	when(changeLog.changeLogExists()).thenReturn(true);
    	when(changeLog.getChangeLogDocument()).thenReturn(document);
    	
    	assertEquals(result, service.printChangeLog(false));
	}
	
	/*
	 * Method createNewChangeLog
	 */
	@Test
	public void createNewChangeLogTest_changeLogCreated() {
		when(changeLog.changeLogExists()).thenReturn(true);
		assertFalse(service.createNewChangeLog());
	}
	
	@Test
	public void createNewChangeLogTest_changeLogNotCreated() throws ParserConfigurationException {		
		when(changeLog.changeLogExists()).thenReturn(false);
		assertTrue(service.createNewChangeLog());
	}
}
