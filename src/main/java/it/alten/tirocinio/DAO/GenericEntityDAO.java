package it.alten.tirocinio.DAO;

import java.util.Map;
import java.util.Set;

public interface GenericEntityDAO {
	/*
	 * Method for execute SELECT query without an Entity Class related
	 */
	Set<Map<String, String>> selectQuery(String query);
}
