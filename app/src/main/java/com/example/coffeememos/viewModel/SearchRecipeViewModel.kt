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
import com.example.coffeememos.search.SearchKeyWord
import com.example.coffeememos.search.SearchType
import com.example.coffeememos.search.SortType
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

    fun setSearchResult(list: List<CustomRecipe>) {
        _searchResult.value = list
    }


    // Sort 状態
    private val _currentSortType: MutableLiveData<SortType> = MutableLiveData(SortType.NEW)
    val currentSortType: LiveData<SortType> = _currentSortType


    // BottomSheet 状態監視
    private val _isOpened: MutableLiveData<Boolean> = MutableLiveData(false)
    val isOpened: LiveData<Boolean> = _isOpened

    fun changeBottomSheetState() {
        _isOpened.value = !(_isOpened.value!!)
    }


    // キーワード検索
    fun freeWordSearch(keyWord: SearchKeyWord) {
        if (keyWord.type == SearchType.BEAN) return

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
    fun sortSearchResult(sortType: SortType) {
        val currentSearchResult = _searchResult.value!!

        val sortedResult: List<CustomRecipe> = when(sortType) {
            SortType.OLD        -> currentSearchResult.sortedBy { recipe -> recipe.recipeId}
            SortType.NEW        -> currentSearchResult.sortedByDescending { recipe -> recipe.recipeId }
            SortType.ROAST      -> currentSearchResult.sortedByDescending { recipe -> recipe.roast}
            SortType.GRIND_SIZE -> currentSearchResult.sortedByDescending { recipe -> recipe.grindSize }
            SortType.RATING     -> currentSearchResult.sortedByDescending { recipe -> recipe.rating }
            SortType.SOUR       -> currentSearchResult.sortedByDescending { recipe -> recipe.sour }
            SortType.BITTER     -> currentSearchResult.sortedByDescending { recipe -> recipe.bitter }
            SortType.SWEET      -> currentSearchResult.sortedByDescending { recipe -> recipe.sweet }
            SortType.FLAVOR     -> currentSearchResult.sortedByDescending { recipe -> recipe.flavor }
            SortType.RICH       -> currentSearchResult.sortedByDescending { recipe -> recipe.rich }
        }

        // ソートされた結果をセット
        _searchResult.value = sortedResult
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
                recipeDao.updateFavoriteByRecipeId(recipeId, true)
            }
        }
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