package com.abn.amro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * This class handles all exceptions which were thrown across the application 
 * @author Bala Susmitha Vinjamuri
 *
 */
@ControllerAdvice
public class RecipeExceptionController {
	
	/**
	 * Handles RecipeNotFoundException and override the message and status
	 * @param RecipeNotFoundException
	 * @return ResponseEntity by custom message and code
	 */
	@ExceptionHandler(value = RecipeNotFoundException.class)
	public ResponseEntity<Object> exception(RecipeNotFoundException exception){
		 return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles DuplicateRecipeException and override the message and status
	 * @param DuplicateRecipeException
	 * @return ResponseEntity by custom message and code
	 */
	@ExceptionHandler(value = DuplicateRecipeException.class)
	public ResponseEntity<Object> exception(DuplicateRecipeException exception){
		 return new ResponseEntity<>("Recipe already there.Please update the recipe", HttpStatus.NOT_ACCEPTABLE);
	}

}
