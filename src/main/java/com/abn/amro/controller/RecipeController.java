package com.abn.amro.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.abn.amro.service.RecipeNotFoundException;
import com.abn.amro.service.RecipeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("recipe")
@RequiredArgsConstructor
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Recipe>> getAllRecipes(){
		List<Recipe> recipies = recipeService.findAll();
		if(recipies!=null) {
			List<Recipe> sortedRecipies = recipies.stream().sorted(Comparator.comparingLong(Recipe::getId)).collect(Collectors.toList());
			return new ResponseEntity<>(sortedRecipies,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping(path="/{id}")
	public Recipe getRecipeDetails(@PathVariable Long id) {
		return recipeService.findById(id).orElseThrow(()->new RecipeNotFoundException("Recipe Not Found. You can add as new recipe."));		
	}
	
	@PostMapping(value="/create",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createNewRecipe(@RequestBody Recipe recipe) {
		String message = recipeService.save(recipe);
		if(message.equals("Saved")) {
		 return new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
		}
	}
	
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
	
	@DeleteMapping(value="/delete/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
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
