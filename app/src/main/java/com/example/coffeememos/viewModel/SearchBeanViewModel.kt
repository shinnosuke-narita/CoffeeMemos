package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.CustomRecipe
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.CustomBean
import com.example.coffeememos.search.BeanSortType
import com.example.coffeememos.search.RecipeSortType

class SearchBeanViewModel(beanDao: BeanDao) : ViewModel() {
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