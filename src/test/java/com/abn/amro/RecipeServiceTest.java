package com.abn.amro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.abn.amro.model.Recipe;
import com.abn.amro.repository.RecipeRepository;
import com.abn.amro.service.DuplicateRecipeException;
import com.abn.amro.service.RecipeNotFoundException;
import com.abn.amro.service.RecipeService;

@SpringBootTest
public class RecipeServiceTest {
	
	@Mock
	private RecipeRepository recipeRepository;
	
	@InjectMocks
	private RecipeService recipeService;
	
	private Recipe recipe1 ;
	private Recipe recipe2 ;
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	LocalDateTime now = LocalDateTime.now();
	
	@BeforeEach
    void setup() {
		ArrayList<String> halwaIngredients = new ArrayList<String>();
		halwaIngredients.add("Sugar");
		halwaIngredients.add("Bread");
		halwaIngredients.add("Ghee");
		halwaIngredients.add("cinnamon");
		ArrayList<String> maggiIngredients = new ArrayList<String>();
		maggiIngredients.add("Maggi");
		maggiIngredients.add("Water");
		maggiIngredients.add("Maggi Masala");
		recipe1 = new Recipe(1L,"Halwa",dtf.format(now),"Veg",100, halwaIngredients,"Fry the Bread, Dip into sugar syrup, add cinnamon");
		recipe2 = new Recipe(2L,"Maggi",dtf.format(now),"Veg",10, maggiIngredients,"Boil the water, Dip Maggi slice into water, add Maggi Masala after water evapourated");
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe1));
        when(recipeRepository.findById(2L)).thenReturn(Optional.of(recipe2));
        List<Recipe> recipeRepoList = new ArrayList<Recipe>();
        recipeRepoList.add(recipe1);
        recipeRepoList.add(recipe2);
        when(recipeRepository.findAll()).thenReturn(recipeRepoList);
    }
	
	
	    @Test
	    public void findByIdTest() {
	    	 assertEquals(Optional.of(recipe1), recipeService.findById(1L));
	    	 assertEquals(Optional.of(recipe2), recipeService.findById(2L));
	    	 assertEquals(Optional.empty(), recipeService.findById(3L));
	    }
	    
	    @Test
	    public void findAllTest() {
	    	List<Recipe> recipeList = recipeService.findAll();
	    	System.out.println("List" +recipeService.findAll());
	    	assertTrue(recipeList.contains(recipe2));
	    	assertEquals(2, recipeList.size());
	    }
	    
	    @Test
	    public void exception_saveDuplicateRecipeTest() {
	    	Exception exception = assertThrows(DuplicateRecipeException.class, () -> {
	    		recipeService.save(recipe1);
	        });
	    	String expectedMessage = "Duplicate Recipe Exception";
	        String actualMessage = exception.getMessage();
	    	assertEquals(expectedMessage,actualMessage);
	    }
	   
	    @Test
	    public void saveRecipeTest() {
	    	assertEquals("Error",recipeService.save(null));
	    	ArrayList<String> breadOmletteIngredients = new ArrayList<String>();
	    	breadOmletteIngredients.add("Onions");
	    	breadOmletteIngredients.add("Eggs");
	    	breadOmletteIngredients.add("Bread");
	    	breadOmletteIngredients.add("Oil");
	    	breadOmletteIngredients.add("Mirchi");
	    	breadOmletteIngredients.add("Salt");
			Recipe recipe3 = new Recipe(3L,"BreadOmlette",dtf.format(now),"NonVeg",200, breadOmletteIngredients,"1)Fry the Bread on Pan. 2)Pour the eggs into boul,add onions,mirchi,salt. 3)Stir the ingredients. 4)Add oil on Pan and pour the mixture on the pan like omlette  5) Then keep the Bread on omlette and fry aside. ");
			when(recipeRepository.save(Mockito.any())).thenReturn(recipe3);
			assertEquals("Saved",recipeService.save(recipe3));
			ArrayList<String> muskMelonJuiceIngredients = new ArrayList<String>();
			muskMelonJuiceIngredients.add("Muskmelon");
			muskMelonJuiceIngredients.add("Water");
			muskMelonJuiceIngredients.add("Sugar");
			Recipe recipe4 = new Recipe(4L,"Muskmelon Juice",null,"Veg",100, muskMelonJuiceIngredients,"Peal and cut into piences of Muskmelon. Grind the pieces into jar with sugar once.Later pour the water and grind again");
			when(recipeRepository.save(Mockito.any())).thenReturn(recipe4);
			assertEquals("Saved",recipeService.save(recipe4));
			recipe4.setInstructions(null);
			Exception argsException = assertThrows(IllegalArgumentException.class, () -> {
	    		recipeService.save(recipe4);
	        });
	    	String expectedArgsMessage = "ERROR: Might any of the imputs missed";
	        String actualArgsMessage = argsException.getMessage();
	    	assertEquals(expectedArgsMessage,actualArgsMessage);
	    }
	    
	    @Test
	    public void exception_updateRecipeNotFoundTest() {
	    	ArrayList<String> muskMelonJuiceIngredients = new ArrayList<String>();
			muskMelonJuiceIngredients.add("Muskmelon");
			muskMelonJuiceIngredients.add("Water");
			muskMelonJuiceIngredients.add("Sugar");
			Recipe recipe5 = new Recipe(4L,"Muskmelon Juice",null,"Veg",100, muskMelonJuiceIngredients,"Peal and cut into piences of Muskmelon. Grind the pieces into jar with sugar once.Later pour the water and grind again");
			Exception exception = assertThrows(RecipeNotFoundException.class, () -> {
	    		recipeService.update(4L, recipe5);
	        });
	    	String expectedMessage = "Recipe Not Found. You can add as new recipe.";
	        String actualMessage = exception.getMessage();
	    	assertEquals(expectedMessage,actualMessage);
			
	    }
	    
	    @Test
	    public void updateRecipeTest() {
	    	when(recipeRepository.save(Mockito.any())).thenReturn(recipe1);
	    	assertNotEquals("Bread Halwa",recipeService.update(1L, recipe1).getName());
	    	recipe1.setName("Bread Halwa");
	    	assertEquals(recipe1.getName(),recipeService.update(1L, recipe1).getName());
	    }
	    
	    @Test
	    public void deleteRecipeTest() {
	    	 recipeService.delete(1L);
	    	 when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
	    	 Exception exception = assertThrows(RecipeNotFoundException.class, () -> {
		    		recipeService.delete(1L);
		        });
		    	String expectedMessage = "Recipe Not Found.";
		        String actualMessage = exception.getMessage();
		    	assertEquals(expectedMessage,actualMessage);
	    	 
			 verify(recipeRepository, times(1)).deleteById(1L);
			 
	    	  
	    }

}
