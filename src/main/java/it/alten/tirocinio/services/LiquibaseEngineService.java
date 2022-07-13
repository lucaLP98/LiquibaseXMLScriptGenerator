package it.alten.tirocinio.services;

public interface LiquibaseEngineService {
	/*
	 * Method that allow to run the current open ChangeLog script
	 */
	boolean runChangeLogScript();
}
