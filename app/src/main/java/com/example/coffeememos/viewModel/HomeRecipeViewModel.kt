package com.example.coffeememos.viewModel

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.*
import com.example.coffeememos.Constants
import com.example.coffeememos.Constants.Companion.isFavoriteTagName
import com.example.coffeememos.R
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.util.Util
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.util.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeRecipeViewModel(private val beanDao: BeanDao, private val recipeDao: RecipeDao) : ViewModel() {
    private val maxDisplayItemAmount = 10

    private var beanWithRecipeList: MutableLiveData<Map<Bean, List<Recipe>>> = MutableLiveData(mapOf())

    val allSimpleRecipeList: LiveData<MutableList<SimpleRecipe>> = Transformations.map(beanWithRecipeList) { fullList ->
        val result = mutableListOf<SimpleRecipe>()

        for ((bean, recipes) in fullList) {
            for (recipe in recipes) {

                val item = SimpleRecipe(
                    recipe.id,
                    recipe.beanId,
                    bean.country,
                    DateUtil.formatEpochTimeMills(recipe.createdAt, DateUtil.pattern),
                    recipe.tool,
                    Constants.roastList[recipe.roast],
                    recipe.rating.toString(),
                    recipe.isFavorite)
                result.add(item)
            }
        }

        // 新しい順にソート
        result.sortByDescending { it.recipeId }
        return@map result
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
        allRecipe.sortByDescending { recipe: SimpleRecipe -> recipe.rating }
        return@map allRecipe
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = beanDao.getBeanAndRecipe()
            beanWithRecipeList.postValue(result)
        }
    }

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
    private val recipeDao: RecipeDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeRecipeViewModel::class.java)) {
            return HomeRecipeViewModel(beanDao, recipeDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
    }
}
