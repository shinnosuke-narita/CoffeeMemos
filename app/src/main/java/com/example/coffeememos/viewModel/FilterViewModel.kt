package com.example.coffeememos.viewModel

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeememos.R
import com.example.coffeememos.search.FilterMenuInfo
import com.example.coffeememos.state.MenuState

class FilterViewModel : ViewModel() {
    private val _countryMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val countryMenuState: LiveData<MenuState> = _countryMenuState

    fun setCountryState(isOpen: MenuState) {
        _countryMenuState.value = isOpen
    }

    private val _toolMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val toolMenuState: LiveData<MenuState> = _toolMenuState

    fun setToolState(isOpen: MenuState) {
        _toolMenuState.value = isOpen
    }

    private val _ratingMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val ratingMenuState: LiveData<MenuState> = _ratingMenuState

    fun setRatingState(isOpen: MenuState) {
        _ratingMenuState.value = isOpen
    }

    private val _sourMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val sourMenuState: LiveData<MenuState> = _sourMenuState

    fun setSourState(isOpen: MenuState) {
        _sourMenuState.value = isOpen
    }

    private val _bitterMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val bitterMenuState: LiveData<MenuState> = _bitterMenuState

    fun setBitterState(isOpen: MenuState) {
        _bitterMenuState.value = isOpen
    }

    private val _sweetMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val sweetMenuState: LiveData<MenuState> = _sweetMenuState

    fun setSweetState(isOpen: MenuState) {
        _sweetMenuState.value = isOpen
    }

    private val _flavorMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val flavorMenuState: LiveData<MenuState> = _flavorMenuState

    fun setFlavorState(isOpen: MenuState) {
        _flavorMenuState.value = isOpen
    }

    private val _richMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val richMenuState: LiveData<MenuState> = _richMenuState

    fun setRichState(isOpen: MenuState) {
        _richMenuState.value = isOpen
    }


    // 現在開いているメニューのタグを保持する
    private var currentOpenViewTag: String? = null

    fun updateMenuState(clickedView: View, context: Activity) {
        if (notExistCurrentOpenedView()) {
            currentOpenViewTag = clickedView.tag.toString()
            return
        }

        if (isSameAsCurrentOpenedView(clickedView)) {
            currentOpenViewTag = null
        } else {
            // 現在開かれているViewとは別のViewがクリックされたとき
            when (currentOpenViewTag) {
                context.getString(R.string.country) -> _countryMenuState.value = MenuState.CLOSE
                context.getString(R.string.tool)   -> _toolMenuState.value = MenuState.CLOSE
                context.getString(R.string.review) -> _ratingMenuState.value = MenuState.CLOSE
                context.getString(R.string.sour)   -> _sourMenuState.value = MenuState.CLOSE
                context.getString(R.string.bitter) -> _bitterMenuState.value = MenuState.CLOSE
                context.getString(R.string.sweet)  -> _sweetMenuState.value = MenuState.CLOSE
                context.getString(R.string.flavor) -> _flavorMenuState.value = MenuState.CLOSE
                context.getString(R.string.rich)   -> _richMenuState.value = MenuState.CLOSE
            }

            currentOpenViewTag = clickedView.tag.toString()
        }
    }

    private fun isSameAsCurrentOpenedView(clickedView: View): Boolean {
        return clickedView.tag.toString() == currentOpenViewTag
    }

    private fun notExistCurrentOpenedView(): Boolean {
        return currentOpenViewTag == null
    }
}