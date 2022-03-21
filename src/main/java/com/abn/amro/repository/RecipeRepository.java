package com.abn.amro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abn.amro.model.Recipe;

/**
 * Interface for Recipe Repository
 * <p>
 * Extends the inbuilt JpaRepository interface with Recipe object and primary key type
 * </p>
 * @author BALA SUSMITHA VINJAMURI
 *
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{

}
