package it.alten.tirocinio.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import it.alten.tirocinio.liquibaseChangeElement.ChangeLog;

@Controller
public class ChangeLogController {
	@Resource(name = "sessionChangeLog")
	private ChangeLog sessionChangeLog;
	
	
}
