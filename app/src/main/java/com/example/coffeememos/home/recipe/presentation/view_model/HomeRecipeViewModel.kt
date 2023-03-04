package com.example.coffeememos.home.recipe.presentation.view_model

import androidx.lifecycle.*
import com.example.coffeememos.home.recipe.domain.presentation_model.HomeRecipeOutPut
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeInfo
import com.example.coffeememos.home.recipe.presentation.controller.HomeRecipeController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeRecipeViewModel @Inject constructor()
    : ViewModel() {
    @Inject
    lateinit var controller: HomeRecipeController

    // 新しい順レシピ
    private val _newRecipes: MutableLiveData<List<HomeRecipeInfo>> = MutableLiveData(listOf())
    val newRecipes: LiveData<List<HomeRecipeInfo>> = _newRecipes

    // お気に入りレシピ
    private val _favoriteRecipes: MutableLiveData<List<HomeRecipeInfo>> = MutableLiveData(listOf())
    val favoriteRecipes: LiveData<List<HomeRecipeInfo>> = _favoriteRecipes

    // 高評価順レシピ
    private val _highRatingRecipes: MutableLiveData<List<HomeRecipeInfo>> = MutableLiveData(listOf())
    val highRatingRecipes: LiveData<List<HomeRecipeInfo>> = _highRatingRecipes

    // レシピ総数
    private val _totalRecipeCount: MutableLiveData<Int> = MutableLiveData(0)
    val totalRecipeCount: LiveData<Int> = _totalRecipeCount

    // 今日のレシピ数
    private val _todayRecipeCount: MutableLiveData<Int> = MutableLiveData(0)
    val todayRecipeCount: LiveData<Int> = _todayRecipeCount

    // お気に入りレシピ数
    val favoriteRecipeCount: LiveData<String> = _favoriteRecipes.map { recipes ->
        return@map recipes.size.toString()
    }

    // お気に入り更新
    fun updateHomeData(recipeId: Long, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedFlag: Boolean = !isFavorite
            val homeRecipeData: HomeRecipeOutPut =
                controller.updateRecipeData(recipeId, updatedFlag)

            setRecipeData(homeRecipeData)
        }
    }

    // レシピデータ取得
    fun getHomeRecipeData() {
        viewModelScope.launch {
            val homeRecipeData: HomeRecipeOutPut =
                controller.getHomeRecipeData()

            setRecipeData(homeRecipeData)
        }
    }

    // LiveDataにセット
    private fun setRecipeData(homeRecipeData: HomeRecipeOutPut) {
        _newRecipes.postValue(homeRecipeData.newRecipes)
        _favoriteRecipes.postValue(homeRecipeData.favoriteRecipes)
        _highRatingRecipes.postValue(homeRecipeData.highRatingRecipes)
        _totalRecipeCount.postValue(homeRecipeData.totalCount)
        _todayRecipeCount.postValue(homeRecipeData.todayCount)
    }
}
