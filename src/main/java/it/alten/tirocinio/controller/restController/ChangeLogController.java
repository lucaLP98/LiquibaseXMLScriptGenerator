package it.alten.tirocinio.controller.restController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import it.alten.tirocinio.api.DTO.changeLogDTO.ChangeSetDTO;
import it.alten.tirocinio.api.DTO.changeLogDTO.ChangeSetListDTO;
import it.alten.tirocinio.services.ChangeLogService;

/*
 * Spring Controller for operation on ChangeLog
 */
@RestController
@RequestMapping("/changeLog/")
public class ChangeLogController {
	private final ChangeLogService changeLogService;
	
	/*
	 * Constructor
	 */
	public ChangeLogController(ChangeLogService changeLogService) {
		this.changeLogService = changeLogService;
	}
	
	/*
	 * GET Requests
	 */
	@GetMapping({"viewChangeLog", "viewChangeLog/"})
	@ResponseStatus(HttpStatus.OK)
	public String viewChangeLog() {
		return changeLogService.printChangeLog(true);
	}
	
	@GetMapping({"getChangeSetList", "getChangeSetList/"})
	@ResponseStatus(HttpStatus.OK)
	public ChangeSetListDTO getChangeSetList() {
		return changeLogService.getAllChangeSet();
	}
	
	@GetMapping({"downloadChangeLog", "downloadChangeLog/"})
	@ResponseStatus(HttpStatus.OK)
	public StreamingResponseBody downloadChangeLog(HttpServletResponse response) {
		File scriptFile = changeLogService.getChangeLogFile(); 
		
		response.setContentLength((int) scriptFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=" + scriptFile.getName());
		int BUFFER_SIZE = 1024;
		
		return outputStream -> {
			int bytesRead;
			byte[] buffer = new byte[BUFFER_SIZE];

			//convert the file to InputStream
			InputStream inputStream = new FileInputStream(scriptFile);
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			if (inputStream != null) inputStream.close();
			
			scriptFile.delete();
		};
	}
	
	/*
	 * POST Requests
	 */
	@PostMapping({"createChangeLog", "createChangeLog/"})
	@ResponseStatus(HttpStatus.CREATED)
	public boolean createChangeLog() {
		return changeLogService.createNewChangeLog();
	}
	
	/*
	 * DELETE Requests
	 */	
	@DeleteMapping({"closeChangeLog", "closeChangeLog/"})
	@ResponseStatus(HttpStatus.OK)
	public void closeChangeLog() {
		changeLogService.closeChangeLog();
	}
	
	@DeleteMapping({"removeChangeSet", "removeChangeSet/"})
	@ResponseStatus(HttpStatus.OK)
	public boolean removeChangeSet(@RequestBody ChangeSetDTO changeSetDTO) {
		return (changeSetDTO == null) ? false : changeLogService.removeChangeSet(changeSetDTO.getChangeSetId());
	}
}
