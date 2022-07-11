package it.alten.tirocinio.DAO;

import java.sql.ResultSet;

public interface GenericEntityDAO {
	/*
	 * Method for execute SELECT query without an Entity Class related
	 */
	ResultSet selectQuery(String query);
}
