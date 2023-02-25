package com.example.coffeememos.search.recipe.presentation.view_model

import android.view.View
import androidx.lifecycle.*
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.search.recipe.domain.model.RecipeSortType
import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel
import com.example.coffeememos.search.recipe.presentation.controller.SearchRecipeController
import com.example.coffeememos.search.recipe.presentation.model.SearchKeyWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchRecipeViewModel @Inject constructor(val recipeDao: RecipeDao) : ViewModel() {
    @Inject
    lateinit var searchRecipeController: SearchRecipeController

    // 検索結果
    private val _searchResult: MutableLiveData<List<SearchRecipeModel>> = MutableLiveData(listOf())

    // 検索結果件数
    val recipeCount: LiveData<Int> = _searchResult.map { list ->
        return@map list.size
    }

    // Sort 状態
    private val _currentSortType: MutableLiveData<RecipeSortType> = MutableLiveData(RecipeSortType.NEW)
    val currentSortType: LiveData<RecipeSortType> = _currentSortType

    // 現在の検索キーワード
    private var _currentSearchWord: String = ""
    // 検索結果がアップデートすべきか
    private var _shouldUpdate: Boolean = false

    fun setShouldUpdate(shouldUpdate: Boolean) {
        _shouldUpdate = shouldUpdate
    }


    // ソートされた検索結果
    val sortedSearchResult: MutableLiveData<List<SearchRecipeModel>> = MediatorLiveData<List<SearchRecipeModel>>().apply {
        // 検索結果が更新されたら、ソート
        addSource(_searchResult) { searchResult ->
            value = searchRecipeController.sortRecipe(_currentSortType.value!!, searchResult)
        }
        // 現在のソートが更新されたら、ソート
        addSource(_currentSortType) { sortType ->
            value = searchRecipeController.sortRecipe(sortType, _searchResult.value!!)
        }
    }

    // BottomSheet 状態監視
    private val _isOpened: MutableLiveData<Boolean> = MutableLiveData(false)
    val isOpened: LiveData<Boolean> = _isOpened

    fun changeBottomSheetState() {
        _isOpened.value = !(_isOpened.value!!)
    }

    // キーワード検索
    fun freeWordSearch(keyWord: SearchKeyWord) {
        viewModelScope.launch {
            val result: List<SearchRecipeModel> =
                searchRecipeController.freeWordSearch(keyWord) ?:
                return@launch

            _currentSearchWord = keyWord.keyWord
            _searchResult.postValue(result)
        }
    }

    // sortセット
    fun setCurrentSortType(sortType: RecipeSortType) {
        _currentSortType.value = sortType
    }

    // お気に入り更新
    fun updateFavoriteIcon(recipeId: Long, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavorite) {
                // db更新
                searchRecipeController.updateFavorite(recipeId, false)
            } else {
                // db更新
                searchRecipeController.updateFavorite(recipeId, true)
            }
        }
    }

    // お気に入りアイコン連打防止
    fun disableFavoriteIcon(favoriteIcon: View) {
        favoriteIcon.isEnabled = false

        viewModelScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                favoriteIcon.isEnabled = true
            }
        }
    }

    // filter
    fun filterSearchResult(
       roastValue: List<Boolean>,
       grindSizeValues: List<Boolean>,
       ratingValues: List<Boolean>,
       sourValues: List<Boolean>,
       bitterValues: List<Boolean>,
       sweetValues: List<Boolean>,
       flavorValues: List<Boolean>,
       richValues: List<Boolean>,
       countryValues: List<String>,
       toolValues: List<String> ) {
        viewModelScope.launch {
            val result = searchRecipeController.filter(
                _currentSearchWord,
                roastValue,
                grindSizeValues,
                ratingValues,
                sourValues,
                bitterValues,
                sweetValues,
                flavorValues,
                richValues,
                countryValues,
                toolValues
            )

            _searchResult.postValue(result)
        }
    }

    // 検索結果、条件の削除
    fun resetResult() {
        searchRecipeController.deleteRecipeInputData("filterRecipeInputData")
        _currentSearchWord = ""
        _currentSortType.value = RecipeSortType.NEW
        initSearchResult()
    }

    // フィルタリング要素の削除
    fun deleteFilteringInputData() {
        searchRecipeController.deleteRecipeInputData("filterRecipeInputData")
    }


    // viewModelのイニシャライザから、実行するとアプリが落ちる。
    // Hiltの仕様のよう、、
    fun initSearchResult() {
        viewModelScope.launch {
            _searchResult.postValue(
                searchRecipeController.getAllRecipe()
            )
        }
    }

    // 検索結果更新
    fun updateSearchResult() {
        if (!_shouldUpdate) return

        viewModelScope.launch {
            _searchResult.postValue(
                searchRecipeController.getAllRecipe()
            )
        }
        _shouldUpdate = false
    }


    class SearchRecipeViewModelFactory(
        private val recipeDao: RecipeDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchRecipeViewModel::class.java)) {
                return SearchRecipeViewModel(recipeDao) as T
            }
            throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
        }
    }
}