package com.withapp.coffeememo.presentation.search.bean.view_model

import android.app.Activity
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.withapp.coffeememo.R
import com.withapp.coffeememo.infra.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.domain.usecase.bean.getFilterElements.GetFilterBeanOutputDataUseCase
import com.withapp.coffeememo.presentation.state.MenuState
import com.withapp.coffeememo.presentation.search.common.view_model.BaseFilterViewModel

class BeanFilterViewModel : BaseFilterViewModel() {
    // メニュー開閉状態管理
    private val _countryMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val countryMenuState: LiveData<MenuState> = _countryMenuState

    private val _farmMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val farmMenuState: LiveData<MenuState> = _farmMenuState

    private val _districtMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val districtMenuState: LiveData<MenuState> = _districtMenuState

    private val _storeMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val storeMenuState: LiveData<MenuState> = _storeMenuState

    private val _processMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val processMenuState: LiveData<MenuState> = _processMenuState

    private val _speciesMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val speciesMenuState: LiveData<MenuState> = _speciesMenuState

    private val _ratingMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val ratingMenuState: LiveData<MenuState> = _ratingMenuState

    fun setCountryMenuState(state: MenuState) {
        _countryMenuState.value = state
    }
    fun setFarmMenuState(state: MenuState) {
        _farmMenuState.value = state
    }
    fun setDistrictMenuState(state: MenuState) {
        _districtMenuState.value = state
    }
    fun setStoreMenuState(state: MenuState) {
        _storeMenuState.value = state
    }
    fun setSpeciesMenuState(state: MenuState) {
        _speciesMenuState.value = state
    }
    fun setProcessMenuState(state: MenuState) {
        _processMenuState.value = state
    }
    fun setRatingMenuState(state: MenuState) {
        _ratingMenuState.value = state
    }
    // 他に開いているメニューがあれば閉じる処理
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

    // 絞り込み要素 管理
    private val _countryValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val countryValues: LiveData<List<String>> = _countryValues

    private val _farmValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val farmValues: LiveData<List<String>> = _farmValues

    private val _districtValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val districtValues: LiveData<List<String>> = _districtValues

    private val _storeValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val storeValues: LiveData<List<String>> = _storeValues

    private val _speciesValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val speciesValues: LiveData<List<String>> = _speciesValues

    fun addCountryValue(country: String) {
        _countryValues.value = addList(country, _countryValues.value!!)
    }
    fun removeCountryValue(country: String) {
        _countryValues.value = removeList(country, _countryValues.value!!)
    }
    fun addFarmValue(farm: String) {
        _farmValues.value = addList(farm, _farmValues.value!!)
    }
    fun removeFarmValue(farm: String) {
        _farmValues.value = removeList(farm, _farmValues.value!!)
    }
    fun addDistrictValue(district: String) {
        _districtValues.value = addList(district, _districtValues.value!!)
    }
    fun removeDistrictValue(district: String) {
        _districtValues.value = removeList(district, _districtValues.value!!)
    }
    fun addStoreValue(store: String) {
        _storeValues.value = addList(store, _storeValues.value!!)
    }
    fun removeStoreValue(store: String) {
        _storeValues.value = removeList(store, _storeValues.value!!)
    }
    fun addSpeciesValue(species: String) {
        _speciesValues.value = addList(species, _speciesValues.value!!)
    }
    fun removeSpeciesValue(species: String) {
        _speciesValues.value = removeList(species, _speciesValues.value!!)
    }

    // ラジオボタンの状態管理( 選択されたvalue 管理)
    private val _ratingRadioBtnState: MutableLiveData<List<Boolean>> = MutableLiveData(List(5) {false})
    val ratingRadioBtnState: LiveData<List<Boolean>> = _ratingRadioBtnState

    private val _processRadioBtnState: MutableLiveData<List<Boolean>> = MutableLiveData(List(
        LocalizationManager.processListSize) { false })
    val processBtnStateList: LiveData<List<Boolean>> = _processRadioBtnState

    fun setProcessBtnState(selectedIndex: Int) {
        _processRadioBtnState.value = updateBtnStateList(selectedIndex, _processRadioBtnState.value!!)
    }
    fun setRatingRadioBtnState(selectedIndex: Int) {
        _ratingRadioBtnState.value = updateBtnStateList(selectedIndex, _ratingRadioBtnState.value!!)
    }

    // 絞り込み要素表示テキスト 管理
    val inputCountriesText: LiveData<String> = _countryValues.map { list ->
         if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }
    val inputFarmText: LiveData<String> = _farmValues.map { list ->
        if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }
    val inputDistrictText: LiveData<String> = _districtValues.map { list ->
         if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }
    val inputStoreText: LiveData<String> = _storeValues.map { list ->
         if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }
    val selectedProcessText: LiveData<String> = _processRadioBtnState.map { list ->
        val processList: List<String> =
            LocalizationManager.getProcessList()
        buildSelectedText(list) { index -> "${processList[index]},  " }
    }
    val inputSpeciesText: LiveData<String> = _speciesValues.map { list ->
        if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }
    val selectedRatingText: LiveData<String> = _ratingRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatIndex(index) }
    }

    // viewModel 初期化処理
    fun initialize(useCase: GetFilterBeanOutputDataUseCase) {
        val inputData = useCase.execute("filterBeanInputData") ?: return

        _countryValues.value = inputData.countries
        _farmValues.value = inputData.farms
        _districtValues.value = inputData.districts
        _storeValues.value = inputData.stores
        _speciesValues.value = inputData.species
        _ratingRadioBtnState.value = inputData.rating
        _processRadioBtnState.value = inputData.process
    }
}