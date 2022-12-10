package com.example.coffeememos.viewModel

import android.view.View
import androidx.lifecycle.*
import com.example.coffeememos.*
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.dao.TasteDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste
import com.example.coffeememos.manager.SearchFilterManager
import com.example.coffeememos.search.SearchKeyWord
import com.example.coffeememos.search.SearchType
import com.example.coffeememos.search.RecipeSortType
import com.example.coffeememos.utilities.ViewUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchRecipeViewModel(val beanDao: BeanDao, val recipeDao: RecipeDao, val tasteDao: TasteDao) : ViewModel() {
    private val beanWithRecipeList: LiveData<Map<Bean, List<Recipe>>> =
        beanDao.getBeanAndRecipe().asLiveData()

    private val tasteList: LiveData<List<Taste>> = tasteDao.getAll().asLiveData()

    // beanWithRecipeListとtasteListを監視
    val customRecipeList = MediatorLiveData<List<CustomRecipe>>().apply {
        addSource(beanWithRecipeList) {
            if (updateFavorite) {
                updateFavorite = false
                return@addSource
            }

            value = makeCustomRecipeList()
        }
        addSource(tasteList) {
            value = makeCustomRecipeList()
        }
    }

    // 簡易レシピリスト作成メソッド
    private fun makeCustomRecipeList(): List<CustomRecipe> {
        if (beanWithRecipeListOrTasteListIsEmpty()) return listOf()

        val result = mutableListOf<CustomRecipe>()
        for ((bean, recipes) in beanWithRecipeList.value!!) {
            for (recipe in recipes) {
                for (taste in tasteList.value!!) {
                    if (recipe.id != taste.recipeId) continue

                    val item = CustomRecipe(
                        recipe.id,
                        recipe.beanId,
                        taste.id,
                        bean.country,
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
                    result.add(item)
                }
            }
        }

        // 新しい順にソート
        result.sortByDescending { it.recipeId }
        return result
    }

    private fun beanWithRecipeListOrTasteListIsEmpty(): Boolean {
        return beanWithRecipeList.value.isNullOrEmpty() || tasteList.value.isNullOrEmpty()
    }


    // 検索結果
    private val _searchResult: MutableLiveData<List<CustomRecipe>> = MutableLiveData(listOf())
    val searchResult: LiveData<List<CustomRecipe>> = _searchResult


    // 絞り込み結果
    private val _filteringResult: MutableLiveData<List<CustomRecipe>?> = MutableLiveData(null)
    val filteringResult: LiveData<List<CustomRecipe>?> = _filteringResult

    fun setSearchResult(list: List<CustomRecipe>) {
        _searchResult.value = list
    }

    val recipeCount: MediatorLiveData<Int?> = MediatorLiveData<Int?>().apply {
        addSource(_searchResult) { list ->
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

    // BottomSheet 状態監視
    private val _isOpened: MutableLiveData<Boolean> = MutableLiveData(false)
    val isOpened: LiveData<Boolean> = _isOpened

    fun changeBottomSheetState() {
        _isOpened.value = !(_isOpened.value!!)
    }

    // キーワード検索
    fun freeWordSearch(keyWord: SearchKeyWord) {
        if (keyWord.type == SearchType.BEAN) return
        if (keyWord.keyWord == "") return

        val result: MutableList<CustomRecipe> = mutableListOf()
        val _keyWord: String = keyWord.keyWord

        for(recipe in customRecipeList.value!!) {
            if (recipe.country.contains(_keyWord)) {
                result.add(recipe)
                continue
            }
            if(recipe.tool.contains(_keyWord)) {
                result.add(recipe)
                continue
            }
            if (Constants.roastList[recipe.roast].contains(_keyWord)) {
                result.add(recipe)
                continue
            }
            if (Constants.grindSizeList[recipe.grindSize].contains(_keyWord)) {
                result.add(recipe)
                continue
            }
        }

        _searchResult.value = result
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
                // isFavorite 更新
                recipeDao.updateFavoriteByRecipeId(recipeId, false)

                updateFavorite = true
                for (recipe in customRecipeList.value!!) {
                    if (recipe.recipeId == recipeId) {
                        recipe.isFavorite = false
                        break
                    }
                }


            } else {
                // isFavorite 更新
                // todo 更新処理 上記と同じループ処理とフラグ更新が必要
                recipeDao.updateFavoriteByRecipeId(recipeId, true)
            }
        }
    }

    // filter
    fun filterSearchResult() {
        val filteringList = filterManager.filerList(_searchResult.value!!)

        val result = sortList(_currentSortType.value!!, filteringList)

        _filteringResult.value = result
    }

    fun resetResult() {
        _searchResult.value = customRecipeList.value
        _filteringResult.value = null
        filterManager = SearchFilterManager()
        _currentSortType.value = RecipeSortType.NEW
    }



    class SearchRecipeViewModelFactory(
        private val beanDao: BeanDao,
        private val recipeDao: RecipeDao,
        private val tasteDao: TasteDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchRecipeViewModel::class.java)) {
                return SearchRecipeViewModel(beanDao, recipeDao, tasteDao) as T
            }
            throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
        }
    }
}