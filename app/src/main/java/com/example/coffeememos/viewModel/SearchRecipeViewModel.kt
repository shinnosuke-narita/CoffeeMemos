package com.example.coffeememos.viewModel

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.*
import com.example.coffeememos.CustomRecipe
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.search.*
import com.example.coffeememos.utilities.ViewUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchRecipeViewModel(val recipeDao: RecipeDao) : ViewModel() {
    // 検索結果
    private val _searchResult: MutableLiveData<List<CustomRecipe>> = MutableLiveData(listOf())

    val recipeCount: LiveData<Int> = _searchResult.map { list ->
        return@map list.size
    }

    // Sort 状態
    private val _currentSortType: MutableLiveData<RecipeSortType> = MutableLiveData(RecipeSortType.NEW)
    val currentSortType: LiveData<RecipeSortType> = _currentSortType

    val sortedSearchResult: MutableLiveData<List<CustomRecipe>> = MediatorLiveData<List<CustomRecipe>>().apply {
        // 検索結果が更新されたら、ソート
        addSource(_searchResult) { searchResult ->
            value = sortList(_currentSortType.value!!, searchResult)
        }
        // 現在のソートが更新されたら、ソート
        addSource(_currentSortType) { sortType ->
            value = sortList(sortType, _searchResult.value!!)
        }
    }

    // filter 管理
    lateinit var filterManager: SearchFilterManager

    // BottomSheet 状態監視
    private val _isOpened: MutableLiveData<Boolean> = MutableLiveData(false)
    val isOpened: LiveData<Boolean> = _isOpened

    fun changeBottomSheetState() {
        _isOpened.value = !(_isOpened.value!!)
    }

    // キーワード検索 検索処理が走れば、true
    fun freeWordSearch(keyWord: SearchKeyWord) {
        if (keyWord.type == SearchType.BEAN) return
        if (keyWord.keyWord == "") return

        viewModelScope.launch {
            _searchResult.postValue(
                filterManager.freeWordSearch(keyWord.keyWord)
            )
        }
    }

    // sortセット
    fun setCurrentSortType(sortType: RecipeSortType) {
        _currentSortType.value = sortType
    }

    private fun sortList(sortType: RecipeSortType, list: List<CustomRecipe>): List<CustomRecipe> {
        val result = when(sortType) {
            RecipeSortType.OLD        -> list.sortedBy { recipe -> recipe.recipeId}
            RecipeSortType.NEW        -> list.sortedByDescending { recipe -> recipe.recipeId }
            RecipeSortType.ROAST      -> list.sortedByDescending { recipe -> recipe.roast}
            RecipeSortType.GRIND_SIZE -> list.sortedByDescending { recipe -> recipe.grindSize }
            RecipeSortType.RATING     -> list.sortedByDescending { recipe -> recipe.rating }
            RecipeSortType.SOUR       -> list.sortedByDescending { recipe -> recipe.sour }
            RecipeSortType.BITTER     -> list.sortedByDescending { recipe -> recipe.bitter }
            RecipeSortType.SWEET      -> list.sortedByDescending { recipe -> recipe.sweet }
            RecipeSortType.FLAVOR     -> list.sortedByDescending { recipe -> recipe.flavor }
            RecipeSortType.RICH       -> list.sortedByDescending { recipe -> recipe.rich }
        }

        return result
    }

    fun updateFavoriteIcon(clickedFavoriteIcon: View, recipeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (clickedFavoriteIcon.tag.equals(ViewUtil.IS_FAVORITE_TAG_NAME)) {
                // db更新
                recipeDao.updateFavoriteByRecipeId(recipeId, false)
                // view 更新
                if (clickedFavoriteIcon is ImageView) ViewUtil.setTagAndFavoriteIcon(clickedFavoriteIcon, false)
            } else {
                // db更新
                recipeDao.updateFavoriteByRecipeId(recipeId, true)
                // view 更新
                if (clickedFavoriteIcon is ImageView) ViewUtil.setTagAndFavoriteIcon(clickedFavoriteIcon, true)
            }
        }
    }

    // filter
    fun filterSearchResult() {
        viewModelScope.launch {
            _searchResult.postValue(
                filterManager.searchAndFilter()
            )
        }
    }

    fun resetResult() {
        filterManager = SearchFilterManager(recipeDao, CustomRecipeMapper())
        _currentSortType.value = RecipeSortType.NEW
        initSearchResult()
    }

    init {
        filterManager = SearchFilterManager(recipeDao, CustomRecipeMapper())

        initSearchResult()
    }

    private fun initSearchResult() {
        viewModelScope.launch {
            _searchResult.postValue(
                filterManager.initSearchResult()
            )
        }
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