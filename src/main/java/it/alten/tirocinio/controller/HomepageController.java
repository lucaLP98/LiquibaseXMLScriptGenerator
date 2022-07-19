package it.alten.tirocinio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomepageController {
	@RequestMapping({"", "/"})
	@ResponseStatus(HttpStatus.OK)
	public String getHomepage() {		
		return "HomePage";
	}
}
