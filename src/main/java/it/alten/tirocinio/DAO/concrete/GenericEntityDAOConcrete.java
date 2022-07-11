package it.alten.tirocinio.DAO.concrete;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import it.alten.tirocinio.DAO.GenericEntityDAO;

@Service
public class GenericEntityDAOConcrete implements GenericEntityDAO {
	@Resource(name = "connection")
	private Connection connection;
	
	@Override
	public ResultSet selectQuery(String query) {
		query = query.toLowerCase();
		ResultSet resultSet = null;;
		
		if(query.startsWith("select")) {
			try {
				Statement stm = connection.createStatement();
				resultSet = stm.executeQuery(query);
				//stm.close(); //tovare un modo per chiudere lo statement
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultSet;
	}

}
