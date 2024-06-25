package com.withapp.coffeememo.presentation.favorite.bean.view

import android.view.View
import androidx.lifecycle.*
import com.withapp.coffeememo.domain.model.bean.BeanSortType
import com.withapp.coffeememo.domain.model.bean.FavoriteBeanModel
import com.withapp.coffeememo.domain.usecase.bean.DeleteFavoriteUseCase
import com.withapp.coffeememo.domain.usecase.bean.GetFavoriteBeanUseCase
import com.withapp.coffeememo.domain.usecase.bean.GetSortTypeUseCase
import com.withapp.coffeememo.domain.usecase.bean.SortBeanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteBeanViewModel @Inject constructor(
    private val getFavoriteBeanUseCase: GetFavoriteBeanUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val sortBeanUseCase: SortBeanUseCase,
    private val getSortTypeUseCase: GetSortTypeUseCase
) : ViewModel() {
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
                value = sortBeanUseCase.handle(
                    _currentSort.value!!,
                    favoriteBeans
                )
            }
            // 現在のソートが更新されたら、ソート
            addSource(_currentSort) { sortType ->
                value = sortBeanUseCase.handle(
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
            deleteFavoriteUseCase.handle(bean.id)
        }
    }

    // sort更新
    fun updateCurrentSort(index: Int) {
        val sortType: BeanSortType = getSortTypeUseCase.handle(index)

        _currentSort.value = sortType
    }

    // 初期化処理
    fun initialize() {
        viewModelScope.launch {
            _favoriteBeans.postValue(
               getFavoriteBeanUseCase.handle()
            )
        }
    }
}