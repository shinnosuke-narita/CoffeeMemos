package com.withapp.coffeememo.presentation.favorite.recipe.view

import android.view.View
import androidx.lifecycle.*
import com.withapp.coffeememo.domain.usecase.recipe.deletefavorite.DeleteFavoriteUseCase
import com.withapp.coffeememo.domain.usecase.recipe.getfavoriterecipe.GetFavoriteRecipeUseCase
import com.withapp.coffeememo.domain.usecase.recipe.getsorttype.GetSortTypeUseCase
import com.withapp.coffeememo.domain.usecase.recipe.sortfavoriterecipe.SortRecipeUseCase
import com.withapp.coffeememo.presentation.favorite.recipe.model.FavoriteRecipeModel
import com.withapp.coffeememo.search.recipe.domain.model.RecipeSortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteRecipeUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val sortRecipeUseCase: SortRecipeUseCase,
    private val getSortTypeUseCase: GetSortTypeUseCase
) : ViewModel() {
    // お気に入りレシピリスト
    private val _favoriteRecipes: MutableLiveData<List<FavoriteRecipeModel>> =
        MutableLiveData(listOf())

    // お気に入り登録数
    val favoriteRecipeCount: LiveData<String> =
        _favoriteRecipes.map { recipes ->
            return@map String.format("%d件", recipes.count())
        }

    // 現在のソート
    private val _currentSort: MutableLiveData<RecipeSortType> =
        MutableLiveData(RecipeSortType.NEW)
    val currentSort: LiveData<RecipeSortType> = _currentSort

    // ソートされた検索結果
    val sortedFavoriteRecipes: LiveData<List<FavoriteRecipeModel>> =
        MediatorLiveData<List<FavoriteRecipeModel>>().apply {
        // お気に入りリストが更新されたら、ソート
        addSource(_favoriteRecipes) { favoriteRecipes ->
             value = sortRecipeUseCase.handle(
                _currentSort.value!!,
                favoriteRecipes
            )
        }
        // 現在のソートが更新されたら、ソート
        addSource(_currentSort) { sortType ->
            value = sortRecipeUseCase.handle(
                sortType,
                _favoriteRecipes.value!!
            )
        }
    }

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
            deleteFavoriteUseCase.handle(recipe.id)
        }
    }

    fun updateSortType(index: Int) {
        val sortType: RecipeSortType = getSortTypeUseCase.handle(index)

        _currentSort.value = sortType
    }

    // 初期化処理
    fun initialize() {
        viewModelScope.launch {
            _favoriteRecipes.postValue(getFavoriteUseCase.handle())
        }
    }
}