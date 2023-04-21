package com.withapp.coffeememo.create.recipe.data.repository

import com.withapp.coffeememo.create.recipe.domain.repository.StorageRepository
import com.withapp.coffeememo.core.data.dao.BeanDao
import com.withapp.coffeememo.core.data.dao.RecipeDao
import com.withapp.coffeememo.core.data.dao.TasteDao
import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.core.data.entity.Taste
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