package com.withapp.coffeememo.presentation.search.common.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.withapp.coffeememo.presentation.search.recipe.model.SearchKeyWord

class MainSearchViewModel : ViewModel() {
    // 検索キーワード
    private val _searchKeyWord: MutableLiveData<SearchKeyWord> = MutableLiveData()
    val searchKeyWord: LiveData<SearchKeyWord> = _searchKeyWord

    fun setSearchKeyWord(keyWord: SearchKeyWord) {
        _searchKeyWord.value = keyWord
    }
}