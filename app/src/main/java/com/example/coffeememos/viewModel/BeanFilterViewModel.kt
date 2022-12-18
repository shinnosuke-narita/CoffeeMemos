package com.example.coffeememos.viewModel

import android.app.Activity
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.state.MenuState

class BeanFilterViewModel : BaseFilterViewModel() {
    private val _countryMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val countryMenuState: LiveData<MenuState> = _countryMenuState

    fun setCountryMenuState(state: MenuState) {
        _countryMenuState.value = state
    }

    private val _farmMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val farmMenuState: LiveData<MenuState> = _farmMenuState

    fun setFarmMenuState(state: MenuState) {
        _farmMenuState.value = state
    }

    private val _districtMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val districtMenuState: LiveData<MenuState> = _districtMenuState

    fun setDistrictMenuState(state: MenuState) {
        _districtMenuState.value = state
    }

    private val _storeMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val storeMenuState: LiveData<MenuState> = _storeMenuState

    fun setStoreMenuState(state: MenuState) {
        _storeMenuState.value = state
    }

    private val _processMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val processMenuState: LiveData<MenuState> = _processMenuState

    private val _processBtnStateList: MutableLiveData<MutableList<Boolean>> = MutableLiveData(MutableList(Constants.processList.size) { false })
    val processBtnStateList: LiveData<MutableList<Boolean>> = _processBtnStateList

    val selectedProcessText: LiveData<String> = _processBtnStateList.map { list ->
        buildSelectedText(list) { index -> "${Constants.processList[index]},  " }
    }

    fun setProcessMenuState(state: MenuState) {
        _processMenuState.value = state
    }

    fun setProcessBtnState(selectedIndex: Int) {
        _processBtnStateList.value = updateBtnStateList(selectedIndex, _processBtnStateList.value!!)
    }

    private val _speciesMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val speciesMenuState: LiveData<MenuState> = _speciesMenuState

    fun setSpeciesMenuState(state: MenuState) {
        _speciesMenuState.value = state
    }

    private val _ratingMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val ratingMenuState: LiveData<MenuState> = _ratingMenuState

    fun setRatingMenuState(state: MenuState) {
        _ratingMenuState.value = state
    }



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
                context.getString(R.string.farm) -> _farmMenuState.value = MenuState.CLOSE
                context.getString(R.string.district) -> _districtMenuState.value = MenuState.CLOSE
                context.getString(R.string.store) -> _storeMenuState.value = MenuState.CLOSE
                context.getString(R.string.process) -> _processMenuState.value = MenuState.CLOSE
                context.getString(R.string.species) -> _speciesMenuState.value = MenuState.CLOSE
                context.getString(R.string.review) -> _ratingMenuState.value = MenuState.CLOSE
            }

            currentOpenViewTag = clickedView.tag.toString()
        }
    }
}