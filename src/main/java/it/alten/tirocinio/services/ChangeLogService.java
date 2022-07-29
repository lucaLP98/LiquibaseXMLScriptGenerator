package it.alten.tirocinio.services;


import java.io.File;

import it.alten.tirocinio.api.DTO.changeLogDTO.ChangeSetListDTO;

public interface ChangeLogService {
	/*
	 * Method which allow to create new changeLog
	 */
	boolean createNewChangeLog();
	
	/*
	 * Method which allow to close a existing changeLog
	 */
	void closeChangeLog();
	
	/*
	 * Method which return changelog in String format
	 */
	String printChangeLog(boolean indent);
	
	/*
	 * Method which return list of all ChangeSet contained in the ChangeLog
	 */
	ChangeSetListDTO getAllChangeSet();
	
	/*
	 * Method which remove a ChangeSet from ChangeLog
	 */
	boolean removeChangeSet(String changeSetId);
	
	/*
	 * Method that creates the script xml file 
	 */
	File getChangeLogFile() ;
}
