package it.alten.tirocinio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomepageController {
	@RequestMapping({"", "/"})
	public String getHomepage(Model model) {		
		return "HomePage";
	}
}