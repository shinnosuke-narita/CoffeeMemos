package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.home.recipe.presentation.model.HomeRecipeInfo
import com.example.coffeememos.home.recipe.presentation.controller.HomeRecipeController
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val favoriteRecipeCount: LiveData<Int> = _favoriteRecipes.map { recipes ->
        return@map recipes.size
    }

    // お気に入り更新
    fun updateFavoriteIcon(recipe: HomeRecipeInfo) {
//        viewModelScope.launch(Dispatchers.IO) {
//            if (recipe.isFavorite) {
//                // isFavorite 更新
//                recipeDao.updateFavoriteByRecipeId(recipe.recipeId, false)
//            } else {
//                // isFavorite 更新
//                recipeDao.updateFavoriteByRecipeId(recipe.recipeId, true)
//            }
//        }
    }

    fun initialize() {
        viewModelScope.launch {
            val homeRecipeData = controller.getHomeRecipeData()

            _newRecipes.value = homeRecipeData.newRecipes
            _favoriteRecipes.value = homeRecipeData.favoriteRecipes
            _highRatingRecipes.value = homeRecipeData.highRatingRecipes
            _totalRecipeCount.value = homeRecipeData.totalCount
            _todayRecipeCount.value = homeRecipeData.todayCount
        }
    }
}
