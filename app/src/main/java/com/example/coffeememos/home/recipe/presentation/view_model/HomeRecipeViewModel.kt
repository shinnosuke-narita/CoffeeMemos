package com.example.coffeememos.home.recipe.presentation.view_model

import androidx.lifecycle.*
import com.example.coffeememos.home.recipe.domain.model.HomeRecipeData
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeOutPut
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeInfo
import com.example.coffeememos.home.recipe.presentation.controller.HomeRecipeController
import com.example.coffeememos.home.recipe.presentation.presenter.HomeRecipePresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeRecipeViewModel @Inject constructor()
    : ViewModel() {
    @Inject
    lateinit var controller: HomeRecipeController
    @Inject
    lateinit var presenter: HomeRecipePresenter

    // 新しい順レシピ
    private val _newRecipes = MutableLiveData<List<HomeRecipeInfo>>(listOf())
    val newRecipes: LiveData<List<HomeRecipeInfo>> = _newRecipes

    // お気に入りレシピ
    private val _favoriteRecipes =
        MutableLiveData<List<HomeRecipeInfo>>(listOf())
    val favoriteRecipes: LiveData<List<HomeRecipeInfo>> = _favoriteRecipes

    // 高評価順レシピ
    private val _highRatingRecipes = MutableLiveData<List<HomeRecipeInfo>>(listOf())
    val highRatingRecipes: LiveData<List<HomeRecipeInfo>> = _highRatingRecipes

    // レシピ総数
    private val _totalRecipeCount = MutableLiveData(0)
    val totalRecipeCount: LiveData<Int> = _totalRecipeCount

    // 今日のレシピ数
    private val _todayRecipeCount = MutableLiveData(0)
    val todayRecipeCount: LiveData<Int> = _todayRecipeCount

    // お気に入りレシピ数
    val favoriteRecipeCount: LiveData<String> =
        _favoriteRecipes.map { recipes ->
        return@map recipes.size.toString()
    }

    // お気に入り更新
    fun updateHomeData(recipeId: Long, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedFlag: Boolean = !isFavorite
            val homeRecipeData: HomeRecipeData =
                controller.updateRecipeData(recipeId, updatedFlag)

            val homeRecipeInfo: HomeRecipeOutPut =
                presenter.presentHomeRecipeData(homeRecipeData)

            setRecipeData(homeRecipeInfo)
        }
    }

    // レシピデータ取得
    fun getHomeRecipeData() {
        viewModelScope.launch {
            val homeRecipeData: HomeRecipeData =
                controller.getHomeRecipeData()

           val homeRecipeInfo: HomeRecipeOutPut =
               presenter.presentHomeRecipeData(homeRecipeData)

            setRecipeData(homeRecipeInfo)
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
