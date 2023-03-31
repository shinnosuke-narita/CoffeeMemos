package com.withapp.coffeememo.search.bean.presentation.view_model

import android.view.View
import androidx.lifecycle.*
import com.withapp.coffeememo.search.bean.domain.model.BeanSortType
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.search.bean.presentation.controller.SearchBeanController
import com.withapp.coffeememo.search.recipe.presentation.model.SearchKeyWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchBeanViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var controller: SearchBeanController

    // Sort 状態
    private val _currentSortType: MutableLiveData<BeanSortType> = MutableLiveData(BeanSortType.NEW)
    val currentSortType: LiveData<BeanSortType> = _currentSortType

    // 検索結果
    private val _searchResult: MutableLiveData<List<SearchBeanModel>> = MutableLiveData(listOf())

    // ソートされた検索結果
    val sortedSearchResult: LiveData<List<SearchBeanModel>> =
        MediatorLiveData<List<SearchBeanModel>>().apply {
        addSource(_searchResult) { searchResult ->
            value = controller.sortBean(_currentSortType.value!!, searchResult)
        }

        addSource(_currentSortType) { sortType ->
            value = controller.sortBean(sortType, _searchResult.value!!)
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
            val result = controller.freeWordSearch(keyWord)
                ?: return@launch

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
        processValues: List<Boolean>) {

        viewModelScope.launch {
            _searchResult.postValue(
                controller.filter(
                    _currentKeyWord,
                    countryValues,
                    farmValues,
                    districtValues,
                    storeValues,
                    speciesValues,
                    ratingValues,
                    processValues
                )
            )
        }
    }

    // 検索条件クリア
    fun resetResult() {
        controller.deleteBeanInputData("filterBeanInputData")
        _currentKeyWord = ""
        _currentSortType.value = BeanSortType.NEW
        initSearchResult()
    }

    fun deleteInputData() {
        controller.deleteBeanInputData("filterBeanInputData")
    }

    // 検索結果更新
    fun updateSearchResult() {
        if (!_shouldUpdate) return

        viewModelScope.launch {
            _searchResult.postValue(
                controller.getAllBean()
            )
        }
        _shouldUpdate = false
    }


    // お気に入りアイコン 更新
    fun updateFavoriteData(bean: SearchBeanModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (bean.isFavorite) {
                controller.updateFavorite(bean.id, false)
            } else {
                controller.updateFavorite(bean.id, true)
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
                controller.getAllBean()
            )
        }
    }
}