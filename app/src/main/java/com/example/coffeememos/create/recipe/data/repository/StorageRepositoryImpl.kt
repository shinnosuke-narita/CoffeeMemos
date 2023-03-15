package com.example.coffeememos.create.recipe.data.repository

import com.example.coffeememos.create.recipe.domain.repository.StorageRepository
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.dao.TasteDao
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor()
    : StorageRepository {
    @Inject
    lateinit var recipeDao: RecipeDao
    @Inject
    lateinit var tasteDao: TasteDao
    @Inject
    lateinit var beanDao: BeanDao

    override suspend fun createRecipe(recipe: Recipe) {
        recipeDao.insert(recipe)
    }

    override suspend fun getNewestRecipeId(): Long {
        return recipeDao.getNewestRecipeId()
    }

    override suspend fun createTaste(taste: Taste) {
        tasteDao.insert(taste)
    }

    override suspend fun getBeanCount(): Int {
        return beanDao.getBeanCount()
    }
}