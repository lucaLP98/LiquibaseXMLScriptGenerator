package it.alten.tirocinio.services.concrete;

import java.io.File;
import java.sql.Connection;

import javax.annotation.Resource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;

import it.alten.tirocinio.liquibaseChangeElement.ChangeLog;
import it.alten.tirocinio.services.LiquibaseEngineService;


import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.util.HashMap;
import java.util.Map;


@Service
public class LiquibaseEngineServiceConcrete implements LiquibaseEngineService {
	@Resource(name = "sessionChangeLog")
	private ChangeLog changeLog;
	
	@Resource(name = "connection")
	private Connection connection;
	
	/*
	 * Method that allow to run the current open ChangeLog script
	 */
	@Override
	public synchronized boolean runChangeLogScript() {
		if(!changeLog.changeLogExists())		return false;
		
		boolean ret;
		File scriptFile = new File("src/main/resources/dbchangelog/changelog.xml");
		try {
			//create the xml file
	        //transform the DOM Object to an XML File
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
		
			//indentation for the XML script
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        
	        DOMSource domSource = new DOMSource(changeLog.getChangeLogDocument());
	        
	        //creating the XML file whit the script
	        StreamResult streamResultFile = new StreamResult(scriptFile);
	        transformer.transform(domSource, streamResultFile);
	        
	        Map<String, Object> config = new HashMap<>();
	        Scope.child(config, () -> {
				String path = "classpath:dbchangelog/changelog.xml";
				Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
				Liquibase liquibase = new liquibase.Liquibase(path, new ClassLoaderResourceAccessor(), database);
				liquibase.update(new Contexts(), new LabelExpression());
				liquibase.close();
			});	    
	        
	        ret = true;
		} catch ( TransformerFactoryConfigurationError | TransformerException e) {
			e.printStackTrace();
			ret = false;
		} catch(Exception e) {
			e.printStackTrace();
			ret = false;
		}
		
		return ret;
	}

}
