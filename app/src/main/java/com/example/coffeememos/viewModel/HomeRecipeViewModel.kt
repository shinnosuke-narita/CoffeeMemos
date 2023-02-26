package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.Constants
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeInfo
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.utilities.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeRecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {
    private val maxDisplayItemAmount = 10

    private val recipeWithTasteList: LiveData<List<RecipeWithTaste>> = recipeDao.getRecipeWithTasteByFlow().asLiveData()

    // beanWithRecipeListとtasteListを監視
    val allSimpleRecipeList = recipeWithTasteList.map { recipeWithTasteList ->
        makeSimpleRecipe(recipeWithTasteList)
    }

    // 今日のレシピ
    val todayRecipeList = Transformations.map(allSimpleRecipeList) { allRecipe ->
        return@map allRecipe
            .filter { recipe: HomeRecipeInfo -> recipe.createdAt.contains(DateUtil.today) }
            .take(maxDisplayItemAmount)
    }

    // 新しい順レシピ
    val newRecipeList: LiveData<List<HomeRecipeInfo>> = Transformations.map(allSimpleRecipeList) { allRecipe ->
        return@map allRecipe.take(maxDisplayItemAmount)
    }

    // お気に入りレシピ
    val favoriteRecipeList: LiveData<List<HomeRecipeInfo>> = Transformations.map(allSimpleRecipeList) { allRecipe ->
        return@map allRecipe
            .filter { recipe: HomeRecipeInfo -> recipe.isFavorite }
            .take(maxDisplayItemAmount)
    }

    // 高評価順レシピ
    val highRatingRecipeList: LiveData<List<HomeRecipeInfo>> = Transformations.map(allSimpleRecipeList) { allRecipe ->
        return@map allRecipe
            .sortedByDescending { it.rating }
            .take(maxDisplayItemAmount)
    }

    val recipeCountIsZero: LiveData<Boolean> = Transformations.map(allSimpleRecipeList) { list ->
        if (list.isEmpty()) return@map true
        return@map false
    }

    // 簡易レシピリスト作成メソッド
    private fun makeSimpleRecipe(recipeWithTasteList: List<RecipeWithTaste>): List<HomeRecipeInfo> {
        if (recipeWithTasteList.isEmpty()) return listOf()

        val result = mutableListOf<HomeRecipeInfo>()
        for (recipeWithTaste in recipeWithTasteList) {
            val recipe = recipeWithTaste.recipe
            val taste =  recipeWithTaste.taste

            result.add(
                HomeRecipeInfo(
                    recipe.id,
                    recipe.beanId,
                    taste.id,
                    recipe.country,
                    DateUtil.formatEpochTimeMills(recipe.createdAt, DateUtil.pattern),
                    recipe.tool,
                    Constants.roastList[recipe.roast],
                    recipe.rating.toString(),
                    recipe.isFavorite
                )
            )
        }

        // 新しい順にソート
        result.sortByDescending { it.recipeId }
        return result
    }

    // お気に入り更新
    fun updateFavoriteIcon(recipe: HomeRecipeInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            if (recipe.isFavorite) {
                // isFavorite 更新
                recipeDao.updateFavoriteByRecipeId(recipe.recipeId, false)
            } else {
                // isFavorite 更新
                recipeDao.updateFavoriteByRecipeId(recipe.recipeId, true)
            }
        }
    }


    class HomeRecipeViewModelFactory(
        private val recipeDao: RecipeDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeRecipeViewModel::class.java)) {
                return HomeRecipeViewModel(recipeDao) as T
            }
            throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
        }
    }
}
