package com.abn.amro.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class for Recipe
 * @author Bala Susmitha Vinjamuri
 *
 */
@Table(name="recipe")
@Entity
public class Recipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "recipename", nullable = false)
	private String name;

	@Column(name = "creationTime")
	private String creationTime;

	@Column(name = "vegorNonveg", nullable = false)
	private String vegorNonveg;

	@Column(name = "peopleCount", nullable = false)
	private int peopleCount;

	@Column(name = "ingredients", nullable = false)
	private ArrayList<String> ingredients;

	@Column(name = "instructions", nullable = false)
	private String instructions;
	
	
	/**
	 * Default Empty Constructor which initializes and constructs when a class loads
	 */
	public Recipe() {
		super();
	}

	/**
	 * Parameterized constructor which constructs and initializes on below parameters
	 * @param id
	 * @param name
	 * @param creationTime
	 * @param vegorNonveg
	 * @param peopleCount
	 * @param ingredients
	 * @param instructions
	 */
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

	/**
	 * Generates tostring of Recipe object
	 * @return Recipe in string format
	 */
	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", creationTime=" + creationTime + ", vegorNonveg=" + vegorNonveg
				+ ", peopleCount=" + peopleCount + ", ingredients=" + ingredients + ", instructions=" + instructions
				+ "]";
	}

	/**
	 * Auto-generated value of primary key
	 * @return Recipe Id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * sets primary key value
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get Recipe Name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets recipe name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get Recipe Creation time
	 * @return timestamp
	 */
	public String getCreationTime() {
		return creationTime;
	}

	/**
	 * Sets Recipe Creation time
	 * @param creationTime
	 */
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * Get Recipe is VegorNonveg
	 * @return vegorNonveg value
	 */
	public String getVegorNonveg() {
		return vegorNonveg;
	}

	/**
	 * Sets Recipe is VegorNonveg
	 * @param vegorNonveg
	 */
	public void setVegorNonveg(String vegorNonveg) {
		this.vegorNonveg = vegorNonveg;
	}

	/**
	 * Get no of people who prefer the recipe
	 * @return peopleCount
	 */
	public int getPeopleCount() {
		return peopleCount;
	}

	/**
	 * Sets no of people who prefer the recipe
	 * @param peopleCount
	 */
	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	/**
	 * Get the list of ingredients for the recipe
	 * @return Ingredients list
	 */
	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	/**
	 * Sets the list of ingredients for the recipe
	 * @param ingredients
	 */
	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * Get the instructions of how to prepare the recipe
	 * @return instructions
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * Sets the instructions of recipe
	 * @param instructions
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

}
