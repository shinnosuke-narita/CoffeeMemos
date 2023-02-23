package com.example.coffeememos.viewModel

import android.view.View
import androidx.lifecycle.*
import com.example.coffeememos.Constants
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.utilities.DateUtil
import com.example.coffeememos.utilities.ViewUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeRecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {
    private val maxDisplayItemAmount = 10

    private val recipeWithTasteList: LiveData<List<RecipeWithTaste>> = recipeDao.getRecipeWithTasteByFlow().asLiveData()

    // beanWithRecipeListとtasteListを監視
    val allSimpleRecipeList = recipeWithTasteList.map { recipeWithTastelist ->
        makeSimpleRecipe(recipeWithTastelist)
    }

    val todayRecipeList = Transformations.map(allSimpleRecipeList) { allRecipe ->
        return@map allRecipe
            .filter { recipe: SimpleRecipe -> recipe.createdAt.contains(DateUtil.today) }
            .take(maxDisplayItemAmount)
    }

    val newRecipeList: LiveData<List<SimpleRecipe>> = Transformations.map(allSimpleRecipeList) { allRecipe ->
        return@map allRecipe.take(maxDisplayItemAmount)
    }

    val favoriteRecipeList: LiveData<List<SimpleRecipe>> = Transformations.map(allSimpleRecipeList) { allRecipe ->
        return@map allRecipe
            .filter { recipe: SimpleRecipe -> recipe.isFavorite }
            .take(maxDisplayItemAmount)
    }

    val highRatingRecipeList: LiveData<List<SimpleRecipe>> = Transformations.map(allSimpleRecipeList) { allRecipe ->
        return@map allRecipe
            .sortedByDescending { it.rating }
            .take(maxDisplayItemAmount)
    }

    val recipeCountIsZero: LiveData<Boolean> = Transformations.map(allSimpleRecipeList) { list ->
        if (list.isEmpty()) return@map true
        return@map false
    }


    // 簡易レシピリスト作成メソッド
    private fun makeSimpleRecipe(recipeWithTasteList: List<RecipeWithTaste>): List<SimpleRecipe> {
        if (recipeWithTasteList.isNullOrEmpty()) return listOf()

        val result = mutableListOf<SimpleRecipe>()
        for (recipeWithTaste in recipeWithTasteList) {
            val recipe = recipeWithTaste.recipe
            val taste =  recipeWithTaste.taste

            result.add(
                SimpleRecipe(
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

    fun updateFavoriteIcon(recipe: SimpleRecipe) {
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
