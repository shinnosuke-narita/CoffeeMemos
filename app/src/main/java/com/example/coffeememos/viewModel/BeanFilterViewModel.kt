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

    private val _countryValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val countryValues: LiveData<List<String>> = _countryValues

    val inputCountriesText: LiveData<String> = _countryValues.map { list ->
         if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }

    fun setCountryMenuState(state: MenuState) {
        _countryMenuState.value = state
    }

    fun addCountryValue(country: String) {
        _countryValues.value = addList(country, _countryValues.value!!)
    }
    fun removeCountryValue(country: String) {
        _countryValues.value = removeList(country, _countryValues.value!!)
    }

    private val _farmMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val farmMenuState: LiveData<MenuState> = _farmMenuState

    private val _farmValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val farmValues: LiveData<List<String>> = _farmValues

    val inputFarmText: LiveData<String> = _farmValues.map { list ->
        if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }

    fun setFarmMenuState(state: MenuState) {
        _farmMenuState.value = state
    }

    fun addFarmValue(farm: String) {
        _farmValues.value = addList(farm, _farmValues.value!!)
    }
    fun removeFarmValue(farm: String) {
        _farmValues.value = removeList(farm, _farmValues.value!!)
    }

    private val _districtMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val districtMenuState: LiveData<MenuState> = _districtMenuState

    private val _districtValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val districtValues: LiveData<List<String>> = _districtValues

    val inputDistrictText: LiveData<String> = _districtValues.map { list ->
         if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }

    fun setDistrictMenuState(state: MenuState) {
        _districtMenuState.value = state
    }

    fun addDistrictValue(district: String) {
        _districtValues.value = addList(district, _districtValues.value!!)
    }
    fun removeDistrictValue(district: String) {
        _districtValues.value = removeList(district, _districtValues.value!!)
    }

    private val _storeMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val storeMenuState: LiveData<MenuState> = _storeMenuState

    private val _storeValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val storeValues: LiveData<List<String>> = _storeValues

    val inputStoreText: LiveData<String> = _storeValues.map { list ->
         if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }

    fun setStoreMenuState(state: MenuState) {
        _storeMenuState.value = state
    }

    fun addStoreValue(store: String) {
        _storeValues.value = addList(store, _storeValues.value!!)
    }
    fun removeStoreValue(store: String) {
        _storeValues.value = removeList(store, _storeValues.value!!)
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

    private val _speciesValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val speciesValues: LiveData<List<String>> = _speciesValues

    val inputSpeciesText: LiveData<String> = _speciesValues.map { list ->
         if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }

    fun setSpeciesMenuState(state: MenuState) {
        _speciesMenuState.value = state
    }

    fun addSpeciesValue(species: String) {
        _speciesValues.value = addList(species, _speciesValues.value!!)
    }
    fun removeSpeciesValue(species: String) {
        _speciesValues.value = removeList(species, _speciesValues.value!!)
    }

    private val _ratingMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val ratingMenuState: LiveData<MenuState> = _ratingMenuState

    fun setRatingMenuState(state: MenuState) {
        _ratingMenuState.value = state
    }

    private fun addList(value: String, currentValues: List<String>): List<String> {
        val result = mutableListOf<String>()
        result.addAll(currentValues)
        result.add(value)
        return result
    }

    private fun removeList(value: String, currentValue: List<String>): List<String> {
        val result = mutableListOf<String>()
        result.addAll(currentValue)
        result.remove(value)
        return result
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