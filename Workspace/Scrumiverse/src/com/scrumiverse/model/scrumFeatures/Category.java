package com.scrumiverse.model.scrumFeatures;

import java.awt.Color;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Category Model for Scrum Projects.
 * 
 * @author Lasse Jacobs, Kevin Jolitz
 * @version 25.03.16
 *
 */

@Entity
public class Category implements Comparable<Category>{
	private int id;
	private String name;
	private String colorCode;
	
	public Category(){
		name = "New category";
		colorCode = "#000000";
	}
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	@Transient
	public Color getColor() {
		return Color.decode(colorCode);
	}

	@Override
	public int compareTo(Category o) {
		if(this.name.compareTo(o.getName()) == 0) {
			return new Integer(id).compareTo(o.getId());
		} else {
			return this.name.compareTo(o.getName());
		}
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", colorCode=" + colorCode + "]";
	}
	
	
}
