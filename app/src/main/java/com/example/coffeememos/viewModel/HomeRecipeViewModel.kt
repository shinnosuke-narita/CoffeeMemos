package com.example.coffeememos.viewModel

import android.view.View
import androidx.lifecycle.*
import com.example.coffeememos.Constants
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.dao.TasteDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste
import com.example.coffeememos.utilities.DateUtil
import com.example.coffeememos.utilities.ViewUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeRecipeViewModel(private val beanDao: BeanDao, private val recipeDao: RecipeDao, private val tasteDao: TasteDao) : ViewModel() {
    private val maxDisplayItemAmount = 10

    private val beanWithRecipeList: LiveData<Map<Bean, List<Recipe>>> = beanDao.getBeanAndRecipe().asLiveData()

    private val tasteList: LiveData<List<Taste>> = tasteDao.getAll().asLiveData()

    // beanWithRecipeListとtasteListを監視
    val allSimpleRecipeList = MediatorLiveData<List<SimpleRecipe>>().apply {
        addSource(beanWithRecipeList) {
            value = makeSimpleRecipe()
        }
        addSource(tasteList) {
            value = makeSimpleRecipe()
        }
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


    // 簡易レシピリスト作成メソッド
    private fun makeSimpleRecipe(): List<SimpleRecipe> {
        if (beanWithRecipeListOrTasteListIsEmpty()) return listOf()

        val result = mutableListOf<SimpleRecipe>()
        for ((bean, recipes) in beanWithRecipeList.value!!) {
            for (recipe in recipes) {
                for (taste in tasteList.value!!) {
                    if (recipe.id != taste.recipeId) continue

                    val item = SimpleRecipe(
                        recipe.id,
                        recipe.beanId,
                        taste.id,
                        bean.country,
                        DateUtil.formatEpochTimeMills(recipe.createdAt, DateUtil.pattern),
                        recipe.tool,
                        Constants.roastList[recipe.roast],
                        recipe.rating.toString(),
                        recipe.isFavorite)
                    result.add(item)
                }
            }
        }

        // 新しい順にソート
        result.sortByDescending { it.recipeId }
        return result
    }

    private fun beanWithRecipeListOrTasteListIsEmpty(): Boolean {
        return beanWithRecipeList.value.isNullOrEmpty() || tasteList.value.isNullOrEmpty()
    }

    fun updateFavoriteIcon(clickedFavoriteIcon: View, recipeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (clickedFavoriteIcon.tag.equals(ViewUtil.IS_FAVORITE_TAG_NAME)) {
                // isFavorite 更新
                recipeDao.updateFavoriteByRecipeId(recipeId, false)
            } else {
                // isFavorite 更新
                recipeDao.updateFavoriteByRecipeId(recipeId, true)
            }
        }
    }
}


class HomeRecipeViewModelFactory(
    private val beanDao: BeanDao,
    private val recipeDao: RecipeDao,
    private val tasteDao: TasteDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeRecipeViewModel::class.java)) {
            return HomeRecipeViewModel(beanDao, recipeDao, tasteDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
    }
}
