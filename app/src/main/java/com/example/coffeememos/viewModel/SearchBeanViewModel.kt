package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.Constants
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.CustomBean
import com.example.coffeememos.search.*

class SearchBeanViewModel(beanDao: BeanDao) : ViewModel() {
    // filter 管理
    var filterManager: BeanFilterManager = BeanFilterManager()

    val allCustomBean: LiveData<List<CustomBean>> = beanDao.getCustomBean().asLiveData()

    private val _searchResult: MutableLiveData<List<CustomBean>> = MutableLiveData(null)
    val searchResult: LiveData<List<CustomBean>> = _searchResult

    fun setSearchResult(list: List<CustomBean>) {
        val result = sortList(_currentSortType.value!!, list)
        _searchResult.value = result
    }

    // 絞り込み結果
    private val _filteringResult: MutableLiveData<List<CustomBean>?> = MutableLiveData(null)
    val filteringResult: LiveData<List<CustomBean>?> = _filteringResult

    // Sort 状態
    private val _currentSortType: MutableLiveData<BeanSortType> = MutableLiveData(BeanSortType.NEW)
    val currentSortType: LiveData<BeanSortType> = _currentSortType

    val recipeCount: MediatorLiveData<Int?> = MediatorLiveData<Int?>().apply {
        addSource(_searchResult) { list ->
            if (list == null) return@addSource
            value = list.size
        }

        addSource(_filteringResult) { list ->
            if (list == null) return@addSource

            value = list.size
        }
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

        val result: MutableList<CustomBean> = mutableListOf()
        val _keyWord: String = keyWord.keyWord

        for(bean in allCustomBean.value!!) {
            if (bean.country.contains(_keyWord)) {
                result.add(bean)
                continue
            }
            if(bean.farm.contains(_keyWord)) {
                result.add(bean)
                continue
            }
            if (bean.district.contains(_keyWord)) {
                result.add(bean)
                continue
            }
            if (bean.store.contains(_keyWord)) {
                result.add(bean)
                continue
            }
            if (bean.species.contains(_keyWord)) {
                result.add(bean)
                continue
            }
            if (Constants.processList[bean.process].contains(_keyWord)) {
                result.add(bean)
                continue
            }
        }

        _searchResult.value = result
    }

    // 並び替え処理
    fun sortSearchResult(sortType: BeanSortType) {
        // currentSortTypeの更新処理
        _currentSortType.value = sortType

        if (_filteringResult.value == null) {
            val sortedResult: List<CustomBean> = sortList(sortType, _searchResult.value!!)
            _searchResult.value = sortedResult
        } else {
            val sortedResult: List<CustomBean> = sortList(sortType, _filteringResult.value!!)
            _filteringResult.value = sortedResult
        }
    }

    private fun sortList(sortType: BeanSortType, list: List<CustomBean>): List<CustomBean> {
        val result = when(sortType) {
            BeanSortType.OLD        -> list.sortedBy { bean -> bean.id }
            BeanSortType.NEW        -> list.sortedByDescending { bean -> bean.id }
            BeanSortType.RATING     -> list.sortedByDescending { bean -> bean.rating }
        }

        return result
    }

    // filter
    fun filterSearchResult() {
        val filteringList = filterManager.makeList(_searchResult.value!!)

        val result = sortList(_currentSortType.value!!, filteringList)

        _filteringResult.value = result
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