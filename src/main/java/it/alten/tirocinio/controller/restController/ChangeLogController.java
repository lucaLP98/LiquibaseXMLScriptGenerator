package it.alten.tirocinio.controller.restController;

import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.alten.tirocinio.api.DTO.changeLogDTO.ChangeSetDTO;
import it.alten.tirocinio.api.DTO.changeLogDTO.ChangeSetListDTO;
import it.alten.tirocinio.api.DTO.changeLogDTO.CreateChangeLogRequestDTO;
import it.alten.tirocinio.liquibaseChangeElement.ChangeLog;
import it.alten.tirocinio.services.ChangeLogService;

/*
 * Spring Controller for operation on ChangeLog
 */
@RestController
@RequestMapping("/changeLog/")
public class ChangeLogController {
	@Resource(name = "sessionChangeLog")
	private ChangeLog sessionChangeLog;
	
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
		return changeLogService.printChangeLog();
	}
	
	@GetMapping({"getChangeSetList", "getChangeSetList/"})
	@ResponseStatus(HttpStatus.OK)
	public ChangeSetListDTO getChangeSetList() {
		ChangeSetListDTO x = changeLogService.getAllChangeSet();
		return x;
	}
	
	/*
	 * POST Requests
	 */
	@PostMapping({"createChangeLog", "createChangeLog/"})
	@ResponseStatus(HttpStatus.CREATED)
	public boolean createChangeLog(@RequestBody CreateChangeLogRequestDTO createChangeLogRequestDTO) {
		return changeLogService.createNewChangeLog(createChangeLogRequestDTO.getChangeLogId());
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
		return changeLogService.removeChangeSet(changeSetDTO.getChangeSetId());
	}
}
