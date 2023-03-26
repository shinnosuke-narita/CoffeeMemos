package com.withapp.coffeememo.favorite.bean.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.favorite.bean.presentation.controller.FavoriteBeanController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteBeanViewModel @Inject constructor()
    : ViewModel() {
    @Inject
    lateinit var controller: FavoriteBeanController

    private val _favoriteBeans: MutableLiveData<List<FavoriteBeanModel>> =
        MutableLiveData(listOf())
    val favoriteBeans: LiveData<List<FavoriteBeanModel>> = _favoriteBeans

    // 初期化処理
    fun initialize() {
        viewModelScope.launch {
            _favoriteBeans.postValue(
                controller.getFavoriteBean()
            )
        }
    }
}