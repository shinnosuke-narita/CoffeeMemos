package com.example.coffeememos.home.recipe.presentation.presenter

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeSource
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeOutput
import com.example.coffeememos.home.recipe.presentation.mapper.HomeRecipeInfoMapper
import javax.inject.Inject

class HomeRecipePresenterImpl @Inject constructor()
    : HomeRecipePresenter {
    @Inject
    lateinit var mapper: HomeRecipeInfoMapper

    override fun presentHomeRecipeData(
        homeRecipeData: HomeRecipeSource
    ): HomeRecipeOutput {
        val newRecipes =
            mapper.execute(homeRecipeData.newRecipes)
        val highRatingRecipes =
            mapper.execute(homeRecipeData.highRatingRecipes)
        val favoriteRecipes =
            mapper.execute(homeRecipeData.favoriteRecipes)

        return HomeRecipeOutput(
            newRecipes,
            highRatingRecipes,
            favoriteRecipes,
            homeRecipeData.totalCount,
            homeRecipeData.todayCount,
            favoriteRecipes.size
        )
    }
}