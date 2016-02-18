package com.scrumiverse.model.scrumFeatures;

import java.awt.Color;

public class Category {
	private Long id;
	private String name;
	private Color color;
	
	public Category(){
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
