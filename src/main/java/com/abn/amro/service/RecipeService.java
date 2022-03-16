package com.abn.amro.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abn.amro.model.Recipe;
import com.abn.amro.repository.RecipeRepository;

@Service
public class RecipeService {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	public List<Recipe> findAll() {
		return recipeRepository.findAll();
	}
	
    public Optional <Recipe> findById(long id) {
        return recipeRepository.findById(id);
    }

   
    public String save(Recipe recipe) {
    	if(recipe!=null) {
    	if(recipe.getCreationTime()==null) {
    		System.out.println("In Time");
    		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    		LocalDateTime now = LocalDateTime.now();
    		System.out.println(dtf.format(now)); 
    		recipe.setCreationTime(dtf.format(now));
    	}
    	if(recipe.getName()==null || recipe.getInstructions()==null || recipe.getPeopleCount()==0 || recipe.getVegorNonveg()==null || recipe.getIngredients()==null) {
    		throw new IllegalArgumentException("ERROR: Might any of the imputs missed");
    	}
    	System.out.println("Recipe in Service"+recipe);
    	boolean checkDup = this.getDuplicates(findAll(),recipe.getName());
    	System.out.println(checkDup);
    	Recipe addedrecipe ;
    	if(checkDup==false) {   	
    		 addedrecipe =recipeRepository.save(recipe);
    	}
    	else {
    		throw new DuplicateRecipeException("Duplicate Recipe Exception");
    	}
    		return "Saved";
    	}
    	else {
    		return "Error";
    	}
    }
    
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

    
    public String delete(long id) {
    	Optional<Recipe> checkBefDel = recipeRepository.findById(id);
    	if(checkBefDel.isPresent()) {
	    	recipeRepository.deleteById(id);
	    	Optional<Recipe> checkAftDel = recipeRepository.findById(id);
	    	if(checkAftDel.isEmpty()) {
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
    
    
    public boolean getDuplicates(final List<Recipe> recipeList,String name) {

    	   return recipeList.stream().map(Recipe::getName).filter(name::equals).findFirst().isPresent();

    	}
   

}
