package com.retroexchanges.rest.json;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = -5254148708568680819L;
	private String name;
	private String description;
	private MultipartFile logo;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getLogo() {
		return logo;
	}
	public void setLogo(MultipartFile logo) {
		this.logo= logo;
	}
	
}
