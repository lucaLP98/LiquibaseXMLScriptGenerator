/*
 * Set of function for manage the Liquibase ChangeLog
 */
 
sessionStorage.setItem('changeLogExists', 'false');
 
/*
 * AJAX function for send asynch request to create Liquibase changeLog
 */
function sendCreateChangeLogRequest(){
	let formData = new FormData(document.getElementById("createChangeLogForm"));
	let alertMsg = document.getElementById("alertMsg");
	
	let notEmpty = checkNotEmptyField("createChangeLogForm");
	if(notEmpty == true){
		let object = {};
		object.changelog_id = formData.get("changelog_id");
		let json = JSON.stringify(object);
	
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
    		if(this.response == "true"){
				alertMsg.innerHTML = "<div class=\"alert alert-success alert-dismissible\" role=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button><strong>OK!</strong> ChangeLog successfully created!</div>";
				sessionStorage.setItem('changeLogExists', 'true');
			}else{
				alertMsg.innerHTML = "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button><strong>Warning!</strong> ChangeLog not created. Close the currently open ChangeLog before creating a new one.</div>";				
			}
   		}
  		xhttp.open("POST", "/changeLog/createChangeLog/", 'true');
  		xhttp.setRequestHeader("Content-type", "application/json");
  		xhttp.send(json);
	}else{
		alertMsg.innerHTML = "<div class=\"alert alert-warning alert-dismissible\" role=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button><strong>Warning!</strong> Fill in all fields to generate the script.</div>";
	}
}

/*
 * AJAX function for send asynch request to close (delete) Liquibase changeLog
 */
function sendCloseChangeLogRequest(){
	let formData = new FormData(document.getElementById("closeChangeLogForm"));
	let alertMsg = document.getElementById("alertMsg");
	
	if(formData.get("closeChangeLog") == "true"){
		const xhttp = new XMLHttpRequest();
		xhttp.onload = function() {
			alertMsg.innerHTML = "<div class=\"alert alert-success alert-dismissible\" role=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button><strong>OK!</strong> ChangeLog successfully closed!</div>";		
   			sessionStorage.setItem('changeLogExists', 'false');
   		}
  		xhttp.open("DELETE", "/changeLog/closeChangeLog/", true);
  		xhttp.send();
	}
}

/*
 * AJAX function for send asynch request to view the current Liquibase changeLog
 */
function sendViewChangeLogRequest(){	
		const xhttp = new XMLHttpRequest();
				
		xhttp.onload = function() {
			document.getElementById("scriptTextArea").innerHTML = this.responseText;   		
		}
  		xhttp.open("GET", "/changeLog/viewChangeLog/", true);
  		xhttp.send();
}

/*
 * AJAX function for send asynch request to remove a changeset by its ID from current Liquibase changeLog
 */
function sendRemoveChangeSetFromChangeLogRequest(){
	let formData = new FormData(document.getElementById("removeChangeSetFromChangeLogForm"));
	let alertMsg = document.getElementById("alertMsg");
	
	let notEmpty = checkNotEmptyField("removeChangeSetFromChangeLogForm");
	if(notEmpty == true){
		if(formData.get("removeChangeSet") == "true"){
			let object = {};
			object.changeset_id = formData.get("changeset_id");
			let json = JSON.stringify(object);
				
			const xhttp = new XMLHttpRequest();
			xhttp.onload = function() {
				if(this.response == "true"){
					alertMsg.innerHTML = "<div class=\"alert alert-success alert-dismissible\" role=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button><strong>OK!</strong> ChangeSet successfully removed!</div>";		
   					sendViewChangeLogRequest();
   					loadChangeSetOption("changeset_id_select");
				}else{
					alertMsg.innerHTML = "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button><strong>Warning!</strong> ChangeSet not removed.</div>";				
				}
				
   			}
  			xhttp.open("DELETE", "/changeLog/removeChangeSet/", true);
  			xhttp.setRequestHeader("Content-type", "application/json");
  			xhttp.send(json);
		}
	}else{
		alertMsg.innerHTML = "<div class=\"alert alert-warning alert-dismissible\" role=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button><strong>Warning!</strong> Fill in all fields to generate the script.</div>";
	}
}