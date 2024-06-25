package com.withapp.coffeememo.presentation.home.recipe.view_model

import androidx.lifecycle.*
import com.withapp.coffeememo.domain.model.recipe.HomeRecipeSource
import com.withapp.coffeememo.domain.usecase.recipe.GetHomeRecipeDataUseCase
import com.withapp.coffeememo.domain.usecase.recipe.UpdateFavoriteUseCase
import com.withapp.coffeememo.presentation.home.recipe.mapper.HomeRecipeCardModelMapper
import com.withapp.coffeememo.presentation.home.recipe.model.HomeRecipeOutput
import com.withapp.coffeememo.presentation.home.recipe.model.HomeRecipeCardData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeRecipeViewModel @Inject constructor(
    private val getHomeRecipeDataUseCase: GetHomeRecipeDataUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val mapper: HomeRecipeCardModelMapper
) : ViewModel() {
    // 新しい順レシピ
    private val _newRecipes = MutableLiveData<List<HomeRecipeCardData>>(listOf())
    val newRecipes: LiveData<List<HomeRecipeCardData>> = _newRecipes

    // お気に入りレシピ
    private val _favoriteRecipes =
        MutableLiveData<List<HomeRecipeCardData>>(listOf())
    val favoriteRecipes: LiveData<List<HomeRecipeCardData>> = _favoriteRecipes

    // 高評価順レシピ
    private val _highRatingRecipes = MutableLiveData<List<HomeRecipeCardData>>(listOf())
    val highRatingRecipes: LiveData<List<HomeRecipeCardData>> = _highRatingRecipes

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
            updateFavoriteUseCase.handle(recipeId, updatedFlag)

            val homeRecipeInfo: HomeRecipeOutput =
                convertHomeRecipeData(getHomeRecipeDataUseCase.handle())
            setRecipeData(homeRecipeInfo)
        }
    }

    // レシピデータ取得
    fun getHomeRecipeData() {
        viewModelScope.launch(Dispatchers.IO) {
            val homeRecipeInfo: HomeRecipeOutput =
                convertHomeRecipeData(getHomeRecipeDataUseCase.handle())
            setRecipeData(homeRecipeInfo)
        }
    }

    // LiveDataにセット
    private fun setRecipeData(homeRecipeData: HomeRecipeOutput) {
        _newRecipes.postValue(homeRecipeData.newRecipes)
        _favoriteRecipes.postValue(homeRecipeData.favoriteRecipes)
        _highRatingRecipes.postValue(homeRecipeData.highRatingRecipes)
        _totalRecipeCount.postValue(homeRecipeData.totalCount)
        _todayRecipeCount.postValue(homeRecipeData.todayCount)
    }

    private fun convertHomeRecipeData(
        homeRecipeData: HomeRecipeSource
    ): HomeRecipeOutput {
        val newRecipes =
            mapper.execute(homeRecipeData.newRecipes)
        val highRatingRecipes =
            mapper.execute(homeRecipeData.highRatingRecipes)
        val favoriteRecipes =
            mapper.execute(homeRecipeData.favoriteRecipes)

        return HomeRecipeOutput(
            newRecipes,
            highRatingRecipes,
            favoriteRecipes,
            homeRecipeData.totalCount,
            homeRecipeData.todayCount
        )
    }
}
