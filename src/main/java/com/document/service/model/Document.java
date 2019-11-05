package com.document.service.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Document implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id	
	private String id;
	private String docId;
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
