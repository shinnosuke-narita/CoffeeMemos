package com.example.coffeememos.home.recipe.domain.interactor

import com.example.coffeememos.home.recipe.domain.model.HomeRecipeSource
import com.example.coffeememos.home.recipe.domain.model.HomeRecipeModel
import com.example.coffeememos.home.recipe.domain.repository.StorageRepository
import com.example.coffeememos.home.recipe.domain.use_case.GetHomeRecipeDataUseCase
import java.time.LocalDate
import javax.inject.Inject

class GetHomeRecipeDataUseCaseInteractor @Inject constructor()
    : GetHomeRecipeDataUseCase {
    private val maxDisplayNum = 15

    @Inject
    lateinit var repository: StorageRepository

    override suspend fun handle(): HomeRecipeSource {
        val recipes = repository.getHomeRecipeModel()
        val sortedRecipe =
            recipes.sortedByDescending { recipe -> recipe.recipeId }

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