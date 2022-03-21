package com.abn.amro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.abn.amro.controller.RecipeController;
import com.abn.amro.model.Recipe;
import com.abn.amro.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This RecipeControllerTest class covers testcases for RecipeController class. 
 * @author Bala Susmitha Vinjamuri
 *
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(RecipeController.class)
@WithMockUser
public class RecipeControllerTest {
	
	@MockBean
	private RecipeService recipeService;
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	LocalDateTime now = LocalDateTime.now();
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private RecipeController recipeController;
	
	private Recipe recipe1 ;
	private Recipe recipe2 ;
	List<Recipe> recipeRepoList = new ArrayList<Recipe>();
	
	/**
	 * Common method which generates string for object
	 * @param obj
	 * @return string of object
	 */
	public static String asJsonString(final Object obj){
	    try{
	        return new ObjectMapper().writeValueAsString(obj);
	    }catch (Exception e){
	           throw new RuntimeException(e);
	      }
	}
	
	/**
	 * Initializes Recipe objects which can use for all test cases in this class.
	 * Specifies the return values when service methods called. 
	 */
	@BeforeEach
	public void setup() {
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
        when(recipeService.findById(1L)).thenReturn(Optional.of(recipe1));
        when(recipeService.findById(2L)).thenReturn(Optional.of(recipe2));       
        recipeRepoList.add(recipe1);
        recipeRepoList.add(recipe2);
        when(recipeService.findAll()).thenReturn(recipeRepoList);
	}

	/**
	 * Test cases for getAllRecipe method in controller
	 * Positive Test: Status, content-type and recipelist what we have set default recipe objects in above.
	 * @throws NotFoundException when recipelist null
	 */
	@Test
	public void getAllRecipeTest() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recipe/getAll");
		mockMvc.perform(requestBuilder)
				 .andExpect(status().isOk())
	             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	             .andExpect(content().json(asJsonString(recipeRepoList)))
                 .andDo(MockMvcResultHandlers.print());
		when(recipeService.findAll()).thenReturn(null);
		mockMvc.perform(requestBuilder)
			.andExpect(status().isNotFound());	            
		
	}
	
	/**
	 * Test cases for getRecipeDetails method in controller
	 * Positive Test: when passes the valid recipe id returns the respective recipe
	 * @throws NotFoundException when invalid recipe id
	 */
	@Test
	public void getRecipeDetailsTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recipe/1");
		mockMvc.perform(requestBuilder)
		 .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(asJsonString(recipe1)))
        .andDo(MockMvcResultHandlers.print());
		
		RequestBuilder requestBuilder1 = MockMvcRequestBuilders.get("/recipe/5");
		mockMvc.perform(requestBuilder1)
		 .andExpect(status().isNotFound());
       
	}
	
	/**
	 * Testcases for createNewRecipe in controller
	 * Positive Test: when new recipe is created successfully then checks the status 201 and successfull message
	 * @throws Bad Request when any invalid recipe values passed
	 */
	@Test
	public void createNewRecipeTest() throws Exception{
		ArrayList<String> muskMelonJuiceIngredients = new ArrayList<String>();
		muskMelonJuiceIngredients.add("Muskmelon");
		muskMelonJuiceIngredients.add("Water");
		muskMelonJuiceIngredients.add("Sugar");
		Recipe recipe3 = new Recipe(3L,"Muskmelon Juice",null,"Veg",100, muskMelonJuiceIngredients,"Peal and cut into pieces of Muskmelon. Grind the pieces into jar with sugar once.Later pour the water and grind again");
		
		
		when(recipeService.save(Mockito.any())).thenReturn("Saved");
		ResponseEntity<String> response=recipeController.createNewRecipe(recipe3);
		assertEquals("201 CREATED",response.getStatusCode().toString());
		assertEquals("Saved Successfully",response.getBody().toString());
		
		when(recipeService.save(Mockito.any())).thenReturn("");
		ResponseEntity<String> errorResponse=recipeController.createNewRecipe(recipe3);
		assertEquals("400 BAD_REQUEST",errorResponse.getStatusCode().toString());
		assertEquals("Save Failed",errorResponse.getBody().toString());
	}
	
	/**
	 * Testcases for updateRecipe in controller
	 * Positive Test: when existed recipe hits for update then checks for status and updated value
	 * @throws NotFoundException when non existing recipe found
	 */
	@Test
	public void updateRecipeTest() throws Exception{
		when(recipeService.update(1L,recipe1)).thenReturn(recipe1);	
		recipe1.setName("Bread Halwa");
		ResponseEntity<Recipe> response=recipeController.updateRecipe(1L,recipe1);
		assertEquals("200 OK",response.getStatusCode().toString());
		assertEquals(recipe1.getName(),response.getBody().getName());
		
		ResponseEntity<Recipe> response1=recipeController.updateRecipe(4L,recipe1);
		assertEquals("404 NOT_FOUND",response1.getStatusCode().toString());
	}
	
	/**
	 * Testcases for deleteRecipe in controller
	 * Positive Test: when existed recipe hits for delete then checks for status and deleted message
	 * @throws Deletion error when there is any error
	 */
	@Test
	public void deleteRecipeTest() throws Exception{
		
		when(recipeService.delete(1L)).thenReturn("Deleted");
		ResponseEntity<String> response=recipeController.deleteRecipe(1L);
		assertEquals("200 OK",response.getStatusCode().toString());
		assertEquals("Deleted Successfully",response.getBody().toString());
		
		when(recipeService.delete(4L)).thenReturn("Error");
		ResponseEntity<String> errorResponse=recipeController.deleteRecipe(4L);
		assertEquals("500 INTERNAL_SERVER_ERROR",errorResponse.getStatusCode().toString());
		assertEquals("Deletetion error",errorResponse.getBody().toString());
	}
}
