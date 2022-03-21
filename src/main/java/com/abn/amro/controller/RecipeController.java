package com.abn.amro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abn.amro.model.Recipe;
import com.abn.amro.service.RecipeService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

/**
 * The Controller for Recipe REST endpoints.
 * <p>
 * This class handles the crud operations of Recipe via HTTP actions.
 * </p>
 * @author Bala Susmitha Vinjamuri
 *
 */
@RestController
@RequestMapping("recipe")
@SecurityRequirement(name="recipeapi")
@Api(value="Recipepage",description = "Adding, updating and deleting recipe list")
@RequiredArgsConstructor
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	
	/**
	 * Gets all the recipes.
	 * @return Recipes with HTTP status code OK if recipes exists and HTTP status code NOT_FOUND if there are no recipies.
	 */	
	@GetMapping("/getAll")
	public ResponseEntity<List<Recipe>> getAllRecipes(){
		List<Recipe> recipies = recipeService.findAll();
		if(recipies!=null) {
			return new ResponseEntity<>(recipies,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	/**
	 * Gets the requested recipe by RecipeId.
	 * @param Recipe id
	 * @return Requested Recipe with status code OK if recipe exists and Recipe Not Found message with HTTP status code NOT_FOUND if requested recipe does not exists.
	 */
	@GetMapping(path="/{id}")
	public Recipe getRecipeDetails(@PathVariable Long id) {
		return recipeService.findById(id);		
	}
	
	/**
	 * Create a Recipe with the given data
	 * @param A JSON respresentation of recipe object
	 * @return Saved Successfully message with status code CREATED if recipe created and Save Failed message with status code BAD_REQUEST if recipe does not saved due to any issues.
	 */
	@PostMapping(value="/create")
	public ResponseEntity<String> createNewRecipe(@RequestBody Recipe recipe) {
		String message = recipeService.save(recipe);
		if(message.equals("Saved")) {
		 return new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Updates a Recipe with the given data
	 * @param Recipe id
	 * @param A JSON respresentation of recipe object
	 * @return Updated recipe with status code OK and HTTP status code NOT_FOUND if there is no such Recipe.
	 */
	@PutMapping("/update/{id}")
	public 	ResponseEntity<Recipe> updateRecipe(@PathVariable Long id,@RequestBody Recipe recipe) {
		Recipe updatedRecipe=recipeService.update(id,recipe);
		if(updatedRecipe!=null) {
			return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Deletes a Recipe with the given data
	 * @param Recipe id
	 * @return Deleted Successfully message with status code OK if deleted and  Deletion error with status code INTERNAL_SERVER_ERROR if any error
	 */
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
			String message =  recipeService.delete(id);
			if(message.equals("Deleted")) {
		      return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
			}
			else {
				 return new ResponseEntity<>("Deletetion error",HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
}
