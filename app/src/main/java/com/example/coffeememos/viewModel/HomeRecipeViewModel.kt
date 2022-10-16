package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.Constants
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.Util
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeRecipeViewModel(private val beanDao: BeanDao) : ViewModel() {
    private val maxDisplayItemAmount = 10

    private var beanWithRecipeList: MutableLiveData<Map<Bean, List<Recipe>>> = MutableLiveData(mapOf())

    val allSimpleRecipeList: LiveData<MutableList<SimpleRecipe>> = Transformations.map(beanWithRecipeList) { fullList ->
        val result = mutableListOf<SimpleRecipe>()

        for ((bean, recipes) in fullList) {
            for (recipe in recipes) {

                val item = SimpleRecipe(
                    recipe.id,
                    bean.country,
                    Util.formatEpochTimeMills(recipe.createdAt, "yyyy/MM/dd HH:mm"),
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
        return@map allRecipe.filter { recipe: SimpleRecipe -> recipe.createdAt.contains(Constants.today) }
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
}


class HomeRecipeViewModelFactory(
    private val beanDao: BeanDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeRecipeViewModel::class.java)) {
            return HomeRecipeViewModel(beanDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
    }
}
