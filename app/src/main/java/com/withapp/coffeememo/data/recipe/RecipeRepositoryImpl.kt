package com.withapp.coffeememo.data.recipe

import com.withapp.coffeememo.core.data.dao.RecipeDao
import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.core.data.entity.RecipeWithTaste
import com.withapp.coffeememo.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val dao: RecipeDao
) : RecipeRepository {
    override suspend fun insert(recipe: Recipe) {
        dao.insert(recipe)
    }

    override suspend fun insertAll(recipes: List<Recipe>) {
        dao.insertAll(recipes)
    }

    override suspend fun update(recipe: Recipe) {
        dao.update(recipe)
    }

    override suspend fun updateFavoriteByRecipeId(id: Long, favoriteFlag: Boolean) {
        dao.updateFavoriteByRecipeId(id, favoriteFlag)
    }

    override suspend fun updateCountryById(id: Long, country: String) {
        dao.updateCountryById(id, country)
    }

    override suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    override suspend fun getNewestRecipeId(): Long {
        return dao.getNewestRecipeId()
    }

    override suspend fun getRecipeById(id: Long): Recipe {
        return dao.getRecipeById(id)
    }

    override suspend fun getRecipeIdsByBeanId(beanId: Long): List<Long> {
        return dao.getRecipeIdsByBeanId(beanId)
    }

    override fun getTotalCount(): Int {
        return dao.getTotalCount()
    }

    override fun getRecipeWithTasteByFlow(): Flow<List<RecipeWithTaste>> {
        return dao.getRecipeWithTasteByFlow()
    }

    override suspend fun getRecipeWithTaste(): List<RecipeWithTaste> {
        return dao.getRecipeWithTaste()
    }

    override suspend fun getRecipeWithTasteByKeyword(keyword: String): List<RecipeWithTaste> {
        return dao.getRecipeWithTasteByKeyword(keyword)
    }

    override suspend fun getFavoriteRecipe(): List<Recipe> {
        return dao.getFavoriteRecipe()
    }
}