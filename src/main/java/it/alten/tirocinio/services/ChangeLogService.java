package it.alten.tirocinio.services;

public interface ChangeLogService {
	/*
	 * Method which allow to create new changeLog
	 */
	boolean createNewChangeLog(String changeLogId);
	
	/*
	 * Method which allow to close a existing changeLog
	 */
	void closeChangeLog();
}
