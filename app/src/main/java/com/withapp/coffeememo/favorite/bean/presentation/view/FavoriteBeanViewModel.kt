package com.withapp.coffeememo.favorite.bean.presentation.view

import android.view.View
import androidx.lifecycle.*
import com.withapp.coffeememo.favorite.bean.domain.model.BeanSortType
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.favorite.bean.presentation.controller.FavoriteBeanController
import com.withapp.coffeememo.favorite.recipe.domain.model.SortDialogOutput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteBeanViewModel @Inject constructor()
    : ViewModel() {
    @Inject
    lateinit var controller: FavoriteBeanController

    // お気に入りコーヒー豆 リスト
    private val _favoriteBeans: MutableLiveData<List<FavoriteBeanModel>> =
        MutableLiveData(listOf())

    // お気に入り登録数
    val favoriteBeanCount: LiveData<String> =
        _favoriteBeans.map { beans ->
            return@map String.format("%d件", beans.count())
        }

    // 現在のソート
    private val _currentSort: MutableLiveData<BeanSortType> =
        MutableLiveData(BeanSortType.NEW)
    val currentSort: LiveData<BeanSortType> = _currentSort

    // ソートされたお気に入りリスト
    val sortedFavoriteBeans: LiveData<List<FavoriteBeanModel>> =
        MediatorLiveData<List<FavoriteBeanModel>>().apply {
            // お気に入りリストが更新されたら、ソート
            addSource(_favoriteBeans) { favoriteBeans ->
                value = controller.sortBean(
                    _currentSort.value!!,
                    favoriteBeans
                )
            }
            // 現在のソートが更新されたら、ソート
            addSource(_currentSort) { sortType ->
                value = controller.sortBean(
                    sortType,
                    _favoriteBeans.value!!
                )
            }
        }

    // 連打防止
    fun disableFavoriteBtn(favoriteIcon: View) {
        favoriteIcon.isEnabled = false

        viewModelScope.launch {
            delay(800L)
            withContext(Dispatchers.Main) {
                favoriteIcon.isEnabled = true
            }
        }
    }

    // お気に入り削除
    fun deleteFavoriteBean(bean: FavoriteBeanModel) {
        val currentRecipe: List<FavoriteBeanModel> = _favoriteBeans.value!!
        _favoriteBeans.value =
            currentRecipe.filter { it.id != bean.id  }

        viewModelScope.launch {
            controller.deleteFavorite(bean.id)
        }
    }

    // sort更新
    fun updateCurrentSort(index: Int) {
        val sortType: BeanSortType = controller.getSortType(index)

        _currentSort.value = sortType
    }


    // 初期化処理
    fun initialize() {
        viewModelScope.launch {
            _favoriteBeans.postValue(
                controller.getFavoriteBean()
            )
        }
    }
}