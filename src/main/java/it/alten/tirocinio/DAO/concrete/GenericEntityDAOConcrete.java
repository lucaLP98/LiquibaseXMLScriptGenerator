package it.alten.tirocinio.DAO.concrete;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import it.alten.tirocinio.DAO.GenericEntityDAO;

@Service
public class GenericEntityDAOConcrete implements GenericEntityDAO {
	@Resource(name = "connection")
	private Connection connection;
	
	/*
	 * Method for execute SELECT query without an Entity Class related
	 */
	@Override
	public Set<Map<String, String>> selectQuery(String query) {
		query = query.toLowerCase();
		Set<Map<String, String>> set = null;
		
		if(query.startsWith("select")) {
			try {
				Statement stm = connection.createStatement();
				ResultSet resultSet = stm.executeQuery(query);
				set = resultSetToMap(resultSet);
				resultSet.close();
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return set;
	}

	/*
	 * Method for convert a ResultSet in Set of Map
	 */
	private Set<Map<String, String>> resultSetToMap(ResultSet resultSet){
		Set<Map<String, String>> set = new HashSet<>();
		
		try {
			ResultSetMetaData metadata = resultSet.getMetaData();
			while(resultSet.next()) {
				Map<String, String> element = new HashMap<>();
				for(int i=1;i<=metadata.getColumnCount();i++) {
					element.put(metadata.getColumnName(i), resultSet.getString(metadata.getColumnName(i)));
				}
				set.add(element);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return set;
	}
}
