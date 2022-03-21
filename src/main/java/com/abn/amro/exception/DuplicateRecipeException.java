package com.abn.amro.exception;

/**
 * Custom Exception class for checking Duplicate Recipes.
 * Throws error message if duplicates came
 * @author Bala Susmitha Vinjamuri
 *
 */
public class DuplicateRecipeException extends RuntimeException{
	 private static final long serialVersionUID = 2L;
	 
	 /**
	  * Constructor which constructs and initializes with message
	  * @param message
	  */
	 public DuplicateRecipeException(String message) {
		 super(message);
	 }
}
