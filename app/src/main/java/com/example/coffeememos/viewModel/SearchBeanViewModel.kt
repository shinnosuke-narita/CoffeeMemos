package com.example.coffeememos.viewModel

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.*
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.CustomBean
import com.example.coffeememos.search.*
import com.example.coffeememos.search.presentation.model.BeanSortType
import com.example.coffeememos.search.presentation.model.SearchKeyWord
import com.example.coffeememos.search.presentation.model.SearchType
import com.example.coffeememos.utilities.ViewUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchBeanViewModel(val beanDao: BeanDao) : ViewModel() {
    // filter 管理
    var filterManager: BeanFilterManager = BeanFilterManager(beanDao)

    // Sort 状態
    private val _currentSortType: MutableLiveData<BeanSortType> = MutableLiveData(BeanSortType.NEW)
    val currentSortType: LiveData<BeanSortType> = _currentSortType

    // 並び替え処理
    fun setCurrentSortType(sortType: BeanSortType) {
        // currentSortTypeの更新処理
        _currentSortType.value = sortType
    }

    private fun sortList(sortType: BeanSortType, list: List<CustomBean>): List<CustomBean> {
        val result = when(sortType) {
            BeanSortType.OLD        -> list.sortedBy { bean -> bean.id }
            BeanSortType.NEW        -> list.sortedByDescending { bean -> bean.id }
            BeanSortType.RATING     -> list.sortedByDescending { bean -> bean.rating }
        }

        return result
    }

    // 検索結果
    private val _searchResult: MutableLiveData<List<CustomBean>> = MutableLiveData(listOf())
    val searchResult: LiveData<List<CustomBean>> = _searchResult

    val sortedSearchResult: LiveData<List<CustomBean>> = MediatorLiveData<List<CustomBean>>().apply {
        addSource(_searchResult) { searchResult ->
            value = sortList(currentSortType.value!!, searchResult)
        }

        addSource(_currentSortType) { sortType ->
            value = sortList(sortType, _searchResult.value!!)
        }
    }

    // 検索結果件数
    val beanCount: LiveData<Int> = _searchResult.map { searchResult ->
        return@map searchResult.size
    }

    // BottomSheet 状態監視
    private val _isOpened: MutableLiveData<Boolean> = MutableLiveData(false)
    val isOpened: LiveData<Boolean> = _isOpened

    fun changeBottomSheetState() {
        _isOpened.value = !(_isOpened.value!!)
    }

    fun freeWordSearch(keyWord: SearchKeyWord) {
        if (keyWord.type == SearchType.RECIPE) return
        if (keyWord.keyWord == "") return

        viewModelScope.launch {
            _searchResult.postValue(
                filterManager.freeWordSearch(keyWord.keyWord)
            )
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

    // 検索条件クリア
    fun resetResult() {
        filterManager = BeanFilterManager(beanDao)
        _currentSortType.value = BeanSortType.NEW
        initSearchResult()
    }

    // お気に入りアイコン 更新
    fun updateFavoriteIcon(clickedFavoriteIcon: View, bean: CustomBean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (bean.isFavorite) {
                // db更新
                beanDao.updateFavoriteByBeanId(bean.id, false)
                // view 更新
                if (clickedFavoriteIcon is ImageView) {
                    ViewUtil.setFavoriteIcon(clickedFavoriteIcon, false)
                }
            } else {
                // db更新
                beanDao.updateFavoriteByBeanId(bean.id, true)
                // view 更新
                if (clickedFavoriteIcon is ImageView) {
                    ViewUtil.setFavoriteIcon(clickedFavoriteIcon, true)
                }
            }
        }
    }

    init {
        initSearchResult()
    }

    private fun initSearchResult() {
        viewModelScope.launch {
            _searchResult.postValue(
                filterManager.initSearchResult()
            )
        }
    }

    class SearchBeanViewModelFactory(
        private val beanDao: BeanDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchBeanViewModel::class.java)) {
                return SearchBeanViewModel(beanDao) as T
            }
            throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
        }
    }
}