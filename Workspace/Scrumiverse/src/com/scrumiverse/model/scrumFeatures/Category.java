package com.scrumiverse.model.scrumFeatures;

import java.awt.Color;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Category Model for Scrum Projects.
 * 
 * @author Lasse Jacobs
 * @version 18.02.16
 *
 */

@Entity
public class Category {
	private Long id;
	private String name;
	private Color color;
	
	public Category(){
		name = "";
		color = Color.pink;
	}
	
	@Id
	@GeneratedValue
	@Column(name="ID")
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
