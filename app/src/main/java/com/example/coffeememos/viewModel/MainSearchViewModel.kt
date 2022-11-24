package com.example.coffeememos.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeememos.SearchKeyWord

class MainSearchViewModel : ViewModel() {
    // 検索キーワード
    private val _searchKeyWord: MutableLiveData<SearchKeyWord> = MutableLiveData()
    val searchKeyWord: LiveData<SearchKeyWord> = _searchKeyWord

    fun setSearchKeyWord(keyWord: SearchKeyWord) {
        _searchKeyWord.value = keyWord
    }
}