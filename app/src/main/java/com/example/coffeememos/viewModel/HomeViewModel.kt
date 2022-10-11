package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.Constants
import com.example.coffeememos.SimpleRecipe
import com.example.coffeememos.Util
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.dao.TasteDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.RecipeWithBeans
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class HomeViewModel(private val beanDao: BeanDao) : ViewModel() {
    private var beanWithRecipeList: MutableLiveData<Map<Bean, List<Recipe>>> = MutableLiveData(mapOf())

    val simpleRecipeList: LiveData<MutableList<SimpleRecipe>> = Transformations.map(beanWithRecipeList) { fullList ->
        val result = mutableListOf<SimpleRecipe>()

        for ((bean, recipes) in fullList) {
            for (recipe in recipes) {
                val item = SimpleRecipe(
                    recipe.id,
                    bean.country,
                    Util.formatEpochTimeMills(recipe.createdAt),
                    recipe.tool,
                    Constants.roastList[recipe.roast])
                result.add(item)
            }
        }

        return@map result
    }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = beanDao.getBeanAndRecipe()
            beanWithRecipeList.postValue(result)
        }
    }
}


class HomeViewModelFactory(
    private val beanDao: BeanDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(beanDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
    }
}
