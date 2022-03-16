package com.abn.amro.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.micrometer.core.lang.NonNull;

@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "recipename")
	@NonNull
	private String name;

	@Column(name = "creationTime")
	private String creationTime;

	@Column(name = "vegorNonveg")
	@NonNull
	private String vegorNonveg;

	@Column(name = "peopleCount")
	@NonNull
	private int peopleCount;

	@Column(name = "ingredients")
	@NonNull
	private ArrayList<String> ingredients;

	@Column(name = "instructions")
	@NonNull
	private String instructions;
	
	
	
	public Recipe() {
		super();
	}

	public Recipe(Long id, String name, String creationTime, String vegorNonveg, int peopleCount,
			ArrayList<String> ingredients, String instructions) {
		super();
		this.id = id;
		this.name = name;
		this.creationTime = creationTime;
		this.vegorNonveg = vegorNonveg;
		this.peopleCount = peopleCount;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", creationTime=" + creationTime + ", vegorNonveg=" + vegorNonveg
				+ ", peopleCount=" + peopleCount + ", ingredients=" + ingredients + ", instructions=" + instructions
				+ "]";
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

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getVegorNonveg() {
		return vegorNonveg;
	}

	public void setVegorNonveg(String vegorNonveg) {
		this.vegorNonveg = vegorNonveg;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

}
