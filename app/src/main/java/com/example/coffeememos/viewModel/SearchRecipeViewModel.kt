package com.example.coffeememos.viewModel

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.*
import com.example.coffeememos.Constants
import com.example.coffeememos.CustomRecipe
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.search.RecipeSortType
import com.example.coffeememos.search.SearchFilterManager
import com.example.coffeememos.search.SearchKeyWord
import com.example.coffeememos.search.SearchType
import com.example.coffeememos.utilities.ViewUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchRecipeViewModel(val recipeDao: RecipeDao) : ViewModel() {
    // 検索結果
    private val _searchResult: MutableLiveData<List<CustomRecipe>> = MutableLiveData(listOf())
    val searchResult: LiveData<List<CustomRecipe>> = _searchResult

    // 絞り込み結果
    private val _filteringResult: MutableLiveData<List<CustomRecipe>?> = MutableLiveData(null)
    val filteringResult: LiveData<List<CustomRecipe>?> = _filteringResult

    val recipeCount: MediatorLiveData<Int?> = MediatorLiveData<Int?>().apply {
        addSource(_searchResult) { list ->
            if (_filteringResult.value != null) return@addSource
            value = list.size
        }

        addSource(_filteringResult) { list ->
            if (list == null) return@addSource

            value = list.size
        }
    }

    // Sort 状態
    private val _currentSortType: MutableLiveData<RecipeSortType> = MutableLiveData(RecipeSortType.NEW)
    val currentSortType: LiveData<RecipeSortType> = _currentSortType

    // filter 管理
    var filterManager: SearchFilterManager = SearchFilterManager()

    fun resetFilterManager() {
        filterManager = SearchFilterManager()
    }

    // BottomSheet 状態監視
    private val _isOpened: MutableLiveData<Boolean> = MutableLiveData(false)
    val isOpened: LiveData<Boolean> = _isOpened

    fun changeBottomSheetState() {
        _isOpened.value = !(_isOpened.value!!)
    }

    // キーワード検索 検索処理が走れば、true
    fun freeWordSearch(keyWord: SearchKeyWord): Boolean {
        if (keyWord.type == SearchType.BEAN) return false
        if (keyWord.keyWord == "") return false

        viewModelScope.launch {
            val result: List<RecipeWithTaste> = recipeDao.getRecipeWithTasteByKeyword(keyWord.keyWord)
            val mappedRes: List<CustomRecipe> = makeCustomRecipeList(result)

            _searchResult.postValue(
                sortList(_currentSortType.value!!, mappedRes)
            )
        }

        return true
    }

    // 並び替え処理
    fun sortSearchResult(sortType: RecipeSortType) {
        // currentSortTypeの更新処理
        _currentSortType.value = sortType

        if (_filteringResult.value == null) {
            val sortedResult: List<CustomRecipe> = sortList(sortType, _searchResult.value!!)
            _searchResult.value = sortedResult
        } else {
            val sortedResult: List<CustomRecipe> = sortList(sortType, _filteringResult.value!!)
            _filteringResult.value = sortedResult
        }
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

    private var updateFavorite: Boolean = false

    fun updateFavoriteIcon(clickedFavoriteIcon: View, recipeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (clickedFavoriteIcon.tag.equals(ViewUtil.IS_FAVORITE_TAG_NAME)) {
                updateFavorite = true
                // db更新
                recipeDao.updateFavoriteByRecipeId(recipeId, false)
                // view 更新
                if (clickedFavoriteIcon is ImageView) ViewUtil.setTagAndFavoriteIcon(clickedFavoriteIcon, false)
            } else {
                updateFavorite = true
                // db更新
                recipeDao.updateFavoriteByRecipeId(recipeId, true)
                // view 更新
                if (clickedFavoriteIcon is ImageView) ViewUtil.setTagAndFavoriteIcon(clickedFavoriteIcon, true)
            }
        }
    }

    // filter
    fun filterSearchResult() {
        val filteringList = filterManager.makeList(_searchResult.value!!)

        val result = sortList(_currentSortType.value!!, filteringList)

        _filteringResult.value = result
    }

    fun resetResult() {
        // 最初に filteringResult を null にする
        // count数が正常に動作しない
        _filteringResult.value = null
        filterManager = SearchFilterManager()
        _currentSortType.value = RecipeSortType.NEW
        initSearchResult()
    }

    init {
        initSearchResult()
    }

    private fun initSearchResult() {
        viewModelScope.launch {
            // Recipe と Taste
            val recipeWithTasteList: List<RecipeWithTaste> = recipeDao.getRecipeWithTaste()
            val mappedRes: List<CustomRecipe> = makeCustomRecipeList(recipeWithTasteList)

            _searchResult.postValue(
                sortList(_currentSortType.value!!, mappedRes)
            )
        }
    }

    // 簡易レシピリスト作成メソッド
    private fun makeCustomRecipeList(recipeWithTasteList: List<RecipeWithTaste>): List<CustomRecipe> {
        if (recipeWithTasteList.isEmpty()) return listOf()

        val result = mutableListOf<CustomRecipe>()
        for (recipeWithTaste in recipeWithTasteList) {
            val recipe = recipeWithTaste.recipe
            val taste = recipeWithTaste.taste

            result.add(
                CustomRecipe(
                    recipe.id,
                    recipe.beanId,
                    taste.id,
                    recipe.country,
                    recipe.tool,
                    recipe.roast,
                    recipe.grindSize,
                    recipe.createdAt,
                    taste.sour,
                    taste.bitter,
                    taste.sweet,
                    taste.flavor,
                    taste.rich,
                    recipe.rating,
                    recipe.isFavorite
                )
            )
        }

        return result
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