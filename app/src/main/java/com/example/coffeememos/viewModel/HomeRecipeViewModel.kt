package com.example.coffeememos.viewModel

import android.view.View
import androidx.lifecycle.*
import com.example.coffeememos.Constants
import com.example.coffeememos.Constants.Companion.isFavoriteTagName
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.dao.TasteDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste
import com.example.coffeememos.util.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeRecipeViewModel(private val beanDao: BeanDao, private val recipeDao: RecipeDao, private val tasteDao: TasteDao) : ViewModel() {
    private val maxDisplayItemAmount = 10

    private var beanWithRecipeList: MutableLiveData<Map<Bean, List<Recipe>>> = MutableLiveData(mapOf())

    private var tasteList: MutableLiveData<List<Taste>> = MutableLiveData(listOf())

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
        allRecipe.sortedByDescending { recipe: SimpleRecipe -> recipe.rating }
        return@map allRecipe
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            beanWithRecipeList.postValue(beanDao.getBeanAndRecipe())
            tasteList.postValue(tasteDao.getAll())
        }
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

    //
    private fun beanWithRecipeListOrTasteListIsEmpty(): Boolean = beanWithRecipeList.value!!.isEmpty() || tasteList.value!!.isEmpty()

    fun updateFavoriteIcon(clickedFavoriteIcon: View, recipeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (clickedFavoriteIcon.tag.equals(isFavoriteTagName)) {
                // isFavorite 更新
                recipeDao.updateFavoriteByRecipeId(recipeId, false)

                // リスト更新
                beanWithRecipeList.postValue(beanDao.getBeanAndRecipe())
            } else {
                // isFavorite 更新
                recipeDao.updateFavoriteByRecipeId(recipeId, true)

                // リスト更新
                beanWithRecipeList.postValue(beanDao.getBeanAndRecipe())
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
