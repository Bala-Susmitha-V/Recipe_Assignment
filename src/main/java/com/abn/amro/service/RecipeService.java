package com.abn.amro.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abn.amro.exception.DuplicateRecipeException;
import com.abn.amro.exception.RecipeNotFoundException;
import com.abn.amro.model.Recipe;
import com.abn.amro.repository.RecipeRepository;

/**
 * The Service class for Recipe crud operations.
 * <p>
 * This class implements the business logics for Recipe REST end points.
 * </p>
 * @author Bala Susmitha Vinjamuri
 *
 */
@Service
public class RecipeService {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	/**
	 * Get all the Recipes.
	 * @return List of Recipes
	 */
	public List<Recipe> findAll() {
		return recipeRepository.findAll();
	}
	
	/**
	 * Finds the requested Recipe. 
	 * @param Recipe id
	 * @return Recipe if found and  null if Recipe not found
	 */
    public Recipe findById(long id) {
        return recipeRepository.findById(id).orElseThrow(()->new RecipeNotFoundException("Recipe Not Found. You can add as new recipe."));
    }

    /**
     * Saves the Recipe.
     * <p>
     * sets date and time to dd-MM-yyyy HH:mm format.
     * </p>
     * <p>
     * checks the recipe for duplicates by recipe name.
     * </p>
     * @exception throws Duplicate Recipe custom exception
     * @param JSON representation of recipe object
     * @return Saved if recipe saved and Error if recipe null
     */
    public String save(Recipe recipe) {
    	if(recipe!=null) {
	    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	    	LocalDateTime now = LocalDateTime.now(); 
	    	recipe.setCreationTime(dtf.format(now));	    	
	    	 boolean checkDup = this.getDuplicates(findAll(),recipe.getName());
	    	if(checkDup==false) {   	
	    	  Recipe addedrecipe =recipeRepository.save(recipe);
	    		return "Saved";
	    	}
	    	else {
	    		throw new DuplicateRecipeException("Duplicate Recipe Exception");
	    	}
    	}
    	else {
    		return "Error";
    	}
    }
    
    /**
     * Updates the Recipe.
     * <p>
     * Firstly it will check if Recipe is existed with given data.
     * </p>
     * @exception if recipe does not exists
     * <p>
     * If recipe exists then updates the recipe
     * </p>
     * @param Recipe id
     * @param JSON representation of recipe object
     * @return updated recipe
     */
    public Recipe update(Long id,Recipe recipe) {
    	
    	Optional<Recipe> recipeData = recipeRepository.findById(id);
		if (recipeData.isPresent()) {
			Recipe updatedRecipeData = recipeData.get();
			updatedRecipeData.setName(recipe.getName());
			updatedRecipeData.setVegorNonveg(recipe.getVegorNonveg());
			updatedRecipeData.setIngredients(recipe.getIngredients());
			updatedRecipeData.setInstructions(recipe.getInstructions());
			updatedRecipeData.setPeopleCount(recipe.getPeopleCount());
			return recipeRepository.save(updatedRecipeData);
		}
		else {
			 throw new RecipeNotFoundException("Recipe Not Found. You can add as new recipe.");
		}
    }

    /**
     * Deletes the Recipe
     * <p>
     * Firstly it will check if Recipe is existed with given data.
     * </p>
     * @exception if recipe does not exists
     * <p>
     * If recipe exists then deletes the recipe
     * </p>
     * @param Recipe id
     * @return Deleted message if deleted and Error message if not deleted
     */
    public String delete(long id) {
    	Optional<Recipe> recipe = recipeRepository.findById(id);
    	if(recipe.isPresent()) {
	    	recipeRepository.deleteById(id);
	    	Optional<Recipe> checkRecipeAftDel = recipeRepository.findById(id);
	    	if(checkRecipeAftDel.isEmpty()) {
	    		return "Deleted";
	    	}
	    	else {
	    		return "Error";
	    	}
    	}
    	else {
    		 throw new RecipeNotFoundException("Recipe Not Found.");
    	}
    }
    
    /**
     * Checks the duplicates of recipes by name
     * @param recipeList
     * @param recipe name
     * @return true if Recipe name already existed and false if Recipe name not existed
     */
    public boolean getDuplicates(final List<Recipe> recipeList,String name) {

    	   return recipeList.stream().map(Recipe::getName).filter(name::equals).findFirst().isPresent();

    	}
   

}
