package com.withapp.coffeememo.favorite.recipe.presentation.view

import android.view.View
import androidx.lifecycle.*
import com.withapp.coffeememo.favorite.recipe.domain.model.RecipeSortType
import com.withapp.coffeememo.favorite.recipe.presentation.controller.FavoriteRecipeController
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeViewModel @Inject constructor()
    : ViewModel() {
    @Inject
    lateinit var controller: FavoriteRecipeController

    // お気に入りレシピリスト
    private val _favoriteRecipes: MutableLiveData<List<FavoriteRecipeModel>> =
        MutableLiveData(listOf())
    val favoriteRecipes: LiveData<List<FavoriteRecipeModel>> = _favoriteRecipes

    // お気に入り登録数
    val favoriteRecipeCount: LiveData<Int> =
        _favoriteRecipes.map { recipes ->
            return@map recipes.count()
        }

    // 現在のソート
    private val _currentSort: MutableLiveData<RecipeSortType> =
        MutableLiveData(RecipeSortType.NEW)
    val currentSort: LiveData<RecipeSortType> = _currentSort


    // 連打防止
    fun disableFavoriteBtn(favoriteIcon: View) {
        favoriteIcon.isEnabled = false

        viewModelScope.launch {
            delay(800L)
            withContext(Dispatchers.Main) {
                favoriteIcon.isEnabled = true
            }
        }
    }

    // お気に入り削除
    fun deleteFavoriteRecipe(recipe: FavoriteRecipeModel) {
        val currentRecipe: List<FavoriteRecipeModel> = _favoriteRecipes.value!!
        _favoriteRecipes.value =
            currentRecipe.filter { it.id != recipe.id  }

        viewModelScope.launch {
            controller.deleteFavorite(recipe.id)
        }
    }

    // 初期化処理
    fun initialize() {
        viewModelScope.launch {
            _favoriteRecipes.postValue(
                controller.getFavoriteRecipe()
            )
        }
    }
}