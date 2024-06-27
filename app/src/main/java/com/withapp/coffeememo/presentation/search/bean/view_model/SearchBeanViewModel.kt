package com.withapp.coffeememo.presentation.search.bean.view_model

import android.view.View
import androidx.lifecycle.*
import com.withapp.coffeememo.search.bean.domain.model.BeanSortType
import com.withapp.coffeememo.search.bean.domain.model.FilterBeanInputData
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.domain.usecase.bean.deletefilterelement.DeleteFilterBeanInputDataUseCase
import com.withapp.coffeememo.domain.usecase.bean.filter.FilterBeanUseCase
import com.withapp.coffeememo.domain.usecase.bean.freewordsearch.FreeWordSearchUseCase
import com.withapp.coffeememo.domain.usecase.bean.getall.GetAllBeanUseCase
import com.withapp.coffeememo.domain.usecase.bean.getFilterElements.GetFilterBeanOutputDataUseCase
import com.withapp.coffeememo.domain.usecase.bean.saveFilterElement.SetFilterBeanInputDataUseCase
import com.withapp.coffeememo.domain.usecase.bean.sort.SortBeanUseCase
import com.withapp.coffeememo.domain.usecase.bean.updatefavorite.UpdateFavoriteBeanUseCase
import com.withapp.coffeememo.presentation.search.recipe.model.SearchKeyWord
import com.withapp.coffeememo.presentation.search.recipe.model.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchBeanViewModel @Inject constructor(
    private val freeWordSearchUseCase: FreeWordSearchUseCase,
    private val sortBeanUseCase: SortBeanUseCase,
    private val filterBeanUseCase: FilterBeanUseCase,
    private val setBeanInputDataUseCase: SetFilterBeanInputDataUseCase,
    val getBeanOutputDataUseCase: GetFilterBeanOutputDataUseCase,
    private val getAllBeanUseCase: GetAllBeanUseCase,
    private val deleteFilterInputDataUseCase: DeleteFilterBeanInputDataUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteBeanUseCase
) : ViewModel() {
    // Sort 状態
    private val _currentSortType: MutableLiveData<BeanSortType> = MutableLiveData(BeanSortType.NEW)
    val currentSortType: LiveData<BeanSortType> = _currentSortType

    // 検索結果
    private val _searchResult: MutableLiveData<List<SearchBeanModel>> = MutableLiveData(listOf())

    // ソートされた検索結果
    val sortedSearchResult: LiveData<List<SearchBeanModel>> =
        MediatorLiveData<List<SearchBeanModel>>().apply {
        addSource(_searchResult) { searchResult ->
            value = sortBeanUseCase.sort(_currentSortType.value!!, searchResult)
        }

        addSource(_currentSortType) { sortType ->
            value = sortBeanUseCase.sort(sortType, _searchResult.value!!)
        }
    }

    // 検索結果件数
    val beanCount: LiveData<Int> = _searchResult.map { searchResult ->
        return@map searchResult.size
    }

    // 現在の検索キーワード
    private var _currentKeyWord: String = ""

    // 検索結果がアップデートすべきか
    private var _shouldUpdate: Boolean = false

    fun setShouldUpdate(shouldUpdate: Boolean) {
        _shouldUpdate = shouldUpdate
    }

    // BottomSheet 状態監視
    private val _isOpened: MutableLiveData<Boolean> = MutableLiveData(false)
    val isOpened: LiveData<Boolean> = _isOpened


    // ソート更新
    fun setCurrentSortType(sortType: BeanSortType) {
        // currentSortTypeの更新処理
        _currentSortType.value = sortType
    }

    // フィルターメニュー状態更新
    fun changeBottomSheetState() {
        _isOpened.value = !(_isOpened.value!!)
    }

    // キーワード検索
    fun freeWordSearch(keyWord: SearchKeyWord) {
        viewModelScope.launch {
            if (keyWord.keyWord.isEmpty()) return@launch
            if (keyWord.type != SearchType.BEAN) return@launch

            val result = freeWordSearchUseCase.handle(keyWord.keyWord)

            _currentKeyWord = keyWord.keyWord
            _searchResult.postValue(result)
        }
    }

    // filter
    fun filterSearchResult(
        countryValues: List<String>,
        farmValues: List<String>,
        districtValues: List<String>,
        storeValues: List<String>,
        speciesValues: List<String>,
        ratingValues: List<Boolean>,
        processValues: List<Boolean>
    ) {
        viewModelScope.launch {
            val ratingData = mutableListOf<Int>()
            for ((i, isSelected) in ratingValues.withIndex()) {
                if (isSelected) { ratingData.add(i + 1) }
            }

            val processData = mutableListOf<Int>()
            for ((i, isSelected) in processValues.withIndex()) {
                if (isSelected) { processData.add(i) }
            }

            val inputData = FilterBeanInputData(
                keyWord = _currentKeyWord,
                countries = countryValues,
                farms = farmValues,
                districts = districtValues,
                stores = storeValues,
                species = speciesValues,
                rating = ratingData,
                process = processData
            )

            // 絞り込み要素を保存
            setBeanInputDataUseCase.execute("filterBeanInputData", inputData)

            // 絞り込み実行
            _searchResult.postValue(filterBeanUseCase.filterBean(inputData))
        }
    }

    // 検索条件クリア
    fun resetResult() {
        deleteFilterInputDataUseCase.handle("filterBeanInputData")
        _currentKeyWord = ""
        _currentSortType.value = BeanSortType.NEW
        initSearchResult()
    }

    fun deleteInputData() {
        deleteFilterInputDataUseCase.handle("filterBeanInputData")
    }

    // 検索結果更新
    fun updateSearchResult() {
        if (!_shouldUpdate) return

        viewModelScope.launch {
            _searchResult.postValue(
                getAllBeanUseCase.getAllBean()
            )
        }
        _shouldUpdate = false
    }


    // お気に入りアイコン 更新
    fun updateFavoriteData(bean: SearchBeanModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (bean.isFavorite) {
                updateFavoriteUseCase.handle(bean.id, false)
            } else {
                updateFavoriteUseCase.handle(bean.id, true)
            }
        }
    }

    // お気に入りアイコンの連打防止
    fun disableFavoriteIcon(favoriteIcon: View) {
        favoriteIcon.isEnabled = false
        viewModelScope.launch {
            delay(1000L)
            withContext(Dispatchers.Main) {
                favoriteIcon.isEnabled = true
            }
        }
    }

    fun initSearchResult() {
        viewModelScope.launch {
            _searchResult.postValue(
                getAllBeanUseCase.getAllBean()
            )
        }
    }
}