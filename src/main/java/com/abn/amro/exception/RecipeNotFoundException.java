package com.abn.amro.exception;
/**
 * This is a custom exception class for Recipe Not Found 
 * @author Bala Susmitha Vinjamuri
 *
 */
public class RecipeNotFoundException extends RuntimeException{
	
	 private static final long serialVersionUID = 1L;
	 
	 /**
	  * Constructor which constructs and initializes with message
	  * @param message
	  */
	 public RecipeNotFoundException(String message) {
		 super(message);
	 }

}
