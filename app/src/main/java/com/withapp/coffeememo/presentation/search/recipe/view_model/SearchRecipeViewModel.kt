package com.withapp.coffeememo.presentation.search.recipe.view_model

import android.view.View
import androidx.lifecycle.*
import com.withapp.coffeememo.domain.model.recipe.FilterRecipeInputData
import com.withapp.coffeememo.domain.model.recipe.RecipeSortType
import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel
import com.withapp.coffeememo.domain.usecase.recipe.deletefilterrecipe.DeleteFilterRecipeInputDataUseCase
import com.withapp.coffeememo.domain.usecase.recipe.filter.FilterRecipeUseCase
import com.withapp.coffeememo.domain.usecase.recipe.freewordsearch.FreeWordSearchUseCase
import com.withapp.coffeememo.domain.usecase.recipe.getall.GetAllRecipeUseCase
import com.withapp.coffeememo.domain.usecase.recipe.getfilterelement.GetFilterRecipeOutputDataUseCase
import com.withapp.coffeememo.domain.usecase.recipe.savefilterelement.SetFilterRecipeInputDataUseCase
import com.withapp.coffeememo.domain.usecase.recipe.sort.SortRecipeUseCase
import com.withapp.coffeememo.domain.usecase.recipe.updatefavorite.UpdateFavoriteUseCase
import com.withapp.coffeememo.presentation.search.recipe.model.SearchKeyWord
import com.withapp.coffeememo.presentation.search.recipe.model.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchRecipeViewModel @Inject constructor(
    private val freeWordSearchUseCase: FreeWordSearchUseCase,
    private val sortRecipeUseCase: SortRecipeUseCase,
    private val filterRecipeUseCase: FilterRecipeUseCase,
    private val setRecipeInputDataUseCase: SetFilterRecipeInputDataUseCase,
    val getRecipeOutputDataUseCase: GetFilterRecipeOutputDataUseCase,
    private val getAllRecipeUseCase: GetAllRecipeUseCase,
    private val deleteFilterInputDataUseCase: DeleteFilterRecipeInputDataUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
) : ViewModel() {
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
    val sortedSearchResult: LiveData<List<SearchRecipeModel>> = MediatorLiveData<List<SearchRecipeModel>>().apply {
        // 検索結果が更新されたら、ソート
        addSource(_searchResult) { searchResult ->
            value = sortRecipeUseCase.sort(_currentSortType.value!!, searchResult)
        }
        // 現在のソートが更新されたら、ソート
        addSource(_currentSortType) { sortType ->
            value = sortRecipeUseCase.sort(sortType, _searchResult.value!!)
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
            if (keyWord.keyWord.isEmpty()) return@launch
            if (keyWord.type == SearchType.BEAN) return@launch

            val result: List<SearchRecipeModel> = freeWordSearchUseCase.handle(keyWord.keyWord)
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
                updateFavoriteUseCase.handle(recipeId, false)
            } else {
                // db更新
                updateFavoriteUseCase.handle(recipeId, true)
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
       roastValues: List<Boolean>,
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
            val roastData = mutableListOf<Int>()
            for ((i, isSelected) in roastValues.withIndex()) {
                if (isSelected) { roastData.add(i) }
            }

            val grindSizeData = mutableListOf<Int>()
            for ((i, isSelected) in grindSizeValues.withIndex()) {
                if (isSelected) { grindSizeData.add(i) }
            }

            val ratingData = mutableListOf<Int>()
            for ((i, isSelected) in ratingValues.withIndex()) {
                if (isSelected) { ratingData.add(i + 1) }
            }

            val sourData = mutableListOf<Int>()
            for ((i, isSelected) in sourValues.withIndex()) {
                if (isSelected) { sourData.add(i + 1) }
            }

            val bitterData = mutableListOf<Int>()
            for ((i, isSelected) in bitterValues.withIndex()) {
                if (isSelected) { bitterData.add(i + 1) }
            }

            val sweetData = mutableListOf<Int>()
            for ((i, isSelected) in sweetValues.withIndex()) {
                if (isSelected) { sweetData.add(i + 1) }
            }

            val flavorData = mutableListOf<Int>()
            for ((i, isSelected) in flavorValues.withIndex()) {
                if (isSelected) { flavorData.add(i + 1) }
            }

            val richData = mutableListOf<Int>()
            for ((i, isSelected) in richValues.withIndex()) {
                if (isSelected) { richData.add(i + 1) }
            }

            val inputData = FilterRecipeInputData(
                keyWord = _currentSearchWord,
                countries = countryValues,
                tools = toolValues,
                roasts = roastData,
                grindSizes = grindSizeData,
                rating = ratingData,
                sour = sourData,
                bitter = bitterData,
                sweet = sweetData,
                flavor = flavorData,
                rich = richData
            )

            // 絞り込み要素を保存
            setRecipeInputDataUseCase.execute(inputData)

            _searchResult.postValue(filterRecipeUseCase.filterRecipe(inputData))
        }
    }

    // 検索結果、条件の削除
    fun resetResult() {
        deleteFilterInputDataUseCase.handle("filterRecipeInputData")
        _currentSearchWord = ""
        _currentSortType.value = RecipeSortType.NEW
        initSearchResult()
    }

    // フィルタリング要素の削除
    fun deleteFilteringInputData() {
        deleteFilterInputDataUseCase.handle("filterRecipeInputData")
    }


    // viewModelのイニシャライザから、実行するとアプリが落ちる。
    // Hiltの仕様のよう、、
    fun initSearchResult() {
        viewModelScope.launch {
            _searchResult.postValue(
                getAllRecipeUseCase.getAllRecipe()
            )
        }
    }

    // 検索結果更新
    fun updateSearchResult() {
        if (!_shouldUpdate) return

        viewModelScope.launch {
            _searchResult.postValue(
                getAllRecipeUseCase.getAllRecipe()
            )
        }
        _shouldUpdate = false
    }
}