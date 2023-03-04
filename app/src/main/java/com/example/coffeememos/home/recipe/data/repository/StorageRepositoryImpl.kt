package com.example.coffeememos.home.recipe.data.repository

import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.home.recipe.data.mapper.HomeRecipeModelMapper
import com.example.coffeememos.home.recipe.domain.model.HomeRecipeModel
import com.example.coffeememos.home.recipe.domain.repository.StorageRepository
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor()
    : StorageRepository {
    @Inject
    lateinit var recipeDao: RecipeDao
    @Inject
    lateinit var beanDao: BeanDao
    @Inject
    lateinit var mapper: HomeRecipeModelMapper

    override suspend fun getHomeRecipeModel(): List<HomeRecipeModel> {
        val beanWithRecipeList = beanDao.getBeanWithRecipe()
        if (beanWithRecipeList.isEmpty()) return listOf()

        return mapper.execute(beanWithRecipeList)
    }

    override suspend fun updateFavorite(
        recipeId: Long,
        isFavorite: Boolean
    ) {
        recipeDao.updateFavoriteByRecipeId(recipeId, isFavorite)
    }
}