package com.withapp.coffeememo.domain.interactor.recipe

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.home.recipe.data.mapper.HomeRecipeModelMapper
import com.withapp.coffeememo.domain.model.recipe.HomeRecipeModel
import com.withapp.coffeememo.domain.model.recipe.HomeRecipeSource
import com.withapp.coffeememo.domain.usecase.recipe.GetHomeRecipeDataUseCase
import java.time.LocalDate
import javax.inject.Inject

class GetHomeRecipeDataUseCaseInteractor @Inject constructor(
    private val beanRepo: BeanRepository,
    private val mapper: HomeRecipeModelMapper
) : GetHomeRecipeDataUseCase {
    private val maxDisplayNum = 15

    override suspend fun handle(): HomeRecipeSource {
        val recipes = beanRepo.getBeanWithRecipe().let {
            mapper.execute(it)
        }
        val sortedRecipe = recipes.sortedByDescending { recipe -> recipe.recipeId }

        // 新しい順レシピ
        val newRecipes = sortedRecipe.take(maxDisplayNum)
        // 高評価レシピ
        val highRatingRecipes = getHighRatingRecipes(sortedRecipe)
        // お気に入りレシピ
        val favoriteRecipes = getFavoriteRecipes(sortedRecipe)
        // 総レシピ数
        val totalCount = recipes.size
        // 今日のレシピ数
        val todayRecipesCount = getTodayRecipeCount(recipes)

        return HomeRecipeSource(
                newRecipes,
                highRatingRecipes,
                favoriteRecipes,
                totalCount,
                todayRecipesCount)
    }

    private fun getTodayRecipeCount(recipes: List<HomeRecipeModel>)
        : Int {
        if (recipes.isEmpty()) return 0

        var result = 0
        val today: LocalDate = LocalDate.now()
        for (recipe in recipes) {
            val year = recipe.createdAt.year
            val month = recipe.createdAt.month
            val day = recipe.createdAt.dayOfMonth
            val createdAt = LocalDate.of(year, month, day)

            if (createdAt == today) {
                result++
            }
        }

        return result
    }

    private fun getFavoriteRecipes(recipes: List<HomeRecipeModel>)
        : List<HomeRecipeModel> {
        if (recipes.isEmpty()) return listOf()

        return recipes
            .filter { recipe -> recipe.isFavorite }
            .take(maxDisplayNum)
    }

    private fun getHighRatingRecipes(recipes: List<HomeRecipeModel>)
        : List<HomeRecipeModel> {
        if (recipes.isEmpty()) return listOf()

        return recipes
            .sortedByDescending { recipe -> recipe.rating }
            .take(maxDisplayNum)
    }
}