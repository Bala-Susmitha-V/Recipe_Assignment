package com.abn.amro;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.abn.amro.model.Recipe;
import com.abn.amro.repository.RecipeRepository;

@SpringBootTest
public class RecipRepositoryTest {

	@Autowired
	private RecipeRepository recipeRepository;
	
	@Test
	public void saveRecipe() {
		ArrayList<String> ingredients = new ArrayList<String>();
		ingredients.add("Sugar");
		ingredients.add("Bread");
		ingredients.add("Ghee");
		ingredients.add("cinnamon");
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		recipe.setName("Halwa");
		recipe.setPeopleCount(100);
		recipe.setVegorNonveg("Veg");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now)); 
		recipe.setCreationTime(dtf.format(now));
		recipe.setIngredients(ingredients);
		recipe.setInstructions("Fry the Bread, Dip into sugar syrup, add cinnamon");
		Recipe newrecipe=  recipeRepository.save(recipe);
		System.out.println(" Recipe repo test"+newrecipe);
		Assertions.assertThat(newrecipe).isNotNull();
		Iterable<Recipe> recipeList = recipeRepository.findAll();
		Assertions.assertThat(recipeList).extracting(Recipe::getName).contains("Halwa");
		recipe.setName("Bread Halwa");
		recipeRepository.save(recipe);
		Iterable<Recipe> recipeList1 = recipeRepository.findAll();
		Assertions.assertThat(recipeList1).extracting(Recipe::getName).contains("Bread Halwa");
		recipeRepository.deleteAll();
		Assertions.assertThat(recipeRepository.findAll()).isEmpty();
	}
}
