package com.abn.amro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.abn.amro.service.DuplicateRecipeException;
import com.abn.amro.service.RecipeNotFoundException;

@ControllerAdvice
public class RecipeExceptionController {
	
	@ExceptionHandler(value = RecipeNotFoundException.class)
	public ResponseEntity<Object> exception(RecipeNotFoundException exception){
		 return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = DuplicateRecipeException.class)
	public ResponseEntity<Object> exception(DuplicateRecipeException exception){
		 return new ResponseEntity<>("Recipe already there.Please update the recipe", HttpStatus.NOT_ACCEPTABLE);
	}

}
