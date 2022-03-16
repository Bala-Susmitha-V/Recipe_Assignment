package com.abn.amro.service;

public class DuplicateRecipeException extends RuntimeException{
	 private static final long serialVersionUID = 2L;
	 
	 public DuplicateRecipeException(String message) {
		 super(message);
	 }
}
