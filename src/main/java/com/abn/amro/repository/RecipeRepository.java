package com.abn.amro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abn.amro.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{

}
