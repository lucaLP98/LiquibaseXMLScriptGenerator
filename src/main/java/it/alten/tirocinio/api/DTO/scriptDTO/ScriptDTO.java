package it.alten.tirocinio.api.DTO.scriptDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ScriptDTO {
	@JsonProperty("id_changeset")
	private String idChangeset;
	
	@JsonProperty("author")
	private String author;
	
	/*
	 * Getter methods
	 */
	public String getIdChangeset() {
		return idChangeset;
	}
	
	public String getAuthor() {
		return author;
	}
	
	/*
	 * Setter methods
	 */
	public void setIdChangeset(String idChangeset) {
		this.idChangeset = idChangeset;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
}
