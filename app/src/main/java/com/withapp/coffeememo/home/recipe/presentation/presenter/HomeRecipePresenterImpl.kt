package com.withapp.coffeememo.home.recipe.presentation.presenter

import com.withapp.coffeememo.home.recipe.domain.model.HomeRecipeSource
import com.withapp.coffeememo.home.recipe.presentation.model.HomeRecipeOutput
import com.withapp.coffeememo.home.recipe.presentation.mapper.HomeRecipeCardModelMapper
import javax.inject.Inject

class HomeRecipePresenterImpl @Inject constructor()
    : HomeRecipePresenter {
    @Inject
    lateinit var mapper: HomeRecipeCardModelMapper

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
            homeRecipeData.todayCount
        )
    }
}