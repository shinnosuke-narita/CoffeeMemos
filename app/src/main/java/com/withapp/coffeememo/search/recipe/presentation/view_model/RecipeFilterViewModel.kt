package com.withapp.coffeememo.search.recipe.presentation.view_model

import android.app.Activity
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.withapp.coffeememo.R
import com.withapp.coffeememo.core.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.state.MenuState
import com.withapp.coffeememo.search.common.presentation.view_model.BaseFilterViewModel
import com.withapp.coffeememo.search.recipe.domain.use_case.GetFilterRecipeOutputDataUseCase

class RecipeFilterViewModel : BaseFilterViewModel() {
    // menu開閉状態 管理
    private val _roastMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val roastMenuState: LiveData<MenuState> = _roastMenuState

    private val _grindSizeMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val grindSizeMenuState: LiveData<MenuState> = _grindSizeMenuState

    private val _countryMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val countryMenuState: LiveData<MenuState> = _countryMenuState

    private val _toolMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val toolMenuState: LiveData<MenuState> = _toolMenuState

    private val _ratingMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val ratingMenuState: LiveData<MenuState> = _ratingMenuState

    private val _sourMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val sourMenuState: LiveData<MenuState> = _sourMenuState

    private val _bitterMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val bitterMenuState: LiveData<MenuState> = _bitterMenuState

    private val _sweetMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val sweetMenuState: LiveData<MenuState> = _sweetMenuState

    private val _flavorMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val flavorMenuState: LiveData<MenuState> = _flavorMenuState

    private val _richMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val richMenuState: LiveData<MenuState> = _richMenuState

    fun setRoastState(isOpen: MenuState) {
        _roastMenuState.value = isOpen
    }
    fun setGrindSizeState(isOpen: MenuState) {
        _grindSizeMenuState.value = isOpen
    }
    fun setCountryState(isOpen: MenuState) {
        _countryMenuState.value = isOpen
    }
    fun setToolState(isOpen: MenuState) {
        _toolMenuState.value = isOpen
    }
    fun setRatingMenuState(isOpen: MenuState) {
        _ratingMenuState.value = isOpen
    }
    fun setSourState(isOpen: MenuState) {
        _sourMenuState.value = isOpen
    }
    fun setSweetState(isOpen: MenuState) {
        _sweetMenuState.value = isOpen
    }
    fun setBitterState(isOpen: MenuState) {
        _bitterMenuState.value = isOpen
    }
    fun setFlavorState(isOpen: MenuState) {
        _flavorMenuState.value = isOpen
    }
    fun setRichState(isOpen: MenuState) {
        _richMenuState.value = isOpen
    }
    // 開いている他のメニューを閉じる処理
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
                context.getString(R.string.roast) -> _roastMenuState.value = MenuState.CLOSE
                context.getString(R.string.grind_size) -> _grindSizeMenuState.value = MenuState.CLOSE
                context.getString(R.string.country) -> _countryMenuState.value = MenuState.CLOSE
                context.getString(R.string.tool) -> _toolMenuState.value = MenuState.CLOSE
                context.getString(R.string.review) -> _ratingMenuState.value = MenuState.CLOSE
                context.getString(R.string.sour) -> _sourMenuState.value = MenuState.CLOSE
                context.getString(R.string.bitter) -> _bitterMenuState.value = MenuState.CLOSE
                context.getString(R.string.sweet) -> _sweetMenuState.value = MenuState.CLOSE
                context.getString(R.string.flavor) -> _flavorMenuState.value = MenuState.CLOSE
                context.getString(R.string.rich) -> _richMenuState.value = MenuState.CLOSE
            }

            currentOpenViewTag = clickedView.tag.toString()
        }
    }

    // ラジオボタン状態管理
    private val  _roastRadioBtnState: MutableLiveData<List<Boolean>> =
        MutableLiveData(List(LocalizationManager.roastListSize) {false})
    val roastBtnStateList: LiveData<List<Boolean>> = _roastRadioBtnState

    private val  _grindSizeRadioBtnState: MutableLiveData<List<Boolean>> =
        MutableLiveData(List(LocalizationManager.grindListSize) {false})
    val grindSizeBtnStateList: LiveData<List<Boolean>> = _grindSizeRadioBtnState

    private val _ratingRadioBtnState: MutableLiveData<List<Boolean>> = MutableLiveData(List(5) {false})
    val ratingRadioBtnState: LiveData<List<Boolean>> = _ratingRadioBtnState

    private val _sourRadioBtnState: MutableLiveData<List<Boolean>> = MutableLiveData(List(5) {false})
    val sourRadioBtnState: LiveData<List<Boolean>> = _sourRadioBtnState

    private val _bitterRadioBtnState: MutableLiveData<List<Boolean>> = MutableLiveData(List(5) {false})
    val bitterRadioBtnState: LiveData<List<Boolean>> = _bitterRadioBtnState

    private val _sweetRadioBtnState: MutableLiveData<List<Boolean>> = MutableLiveData(List(5) {false})
    val sweetRadioBtnState: LiveData<List<Boolean>> = _sweetRadioBtnState

    private val _flavorRadioBtnState: MutableLiveData<List<Boolean>> = MutableLiveData(List(5) {false})
    val flavorRadioBtnState: LiveData<List<Boolean>> = _flavorRadioBtnState

    private val _richRadioBtnState: MutableLiveData<List<Boolean>> = MutableLiveData(List(5) {false})
    val richRadioBtnState: LiveData<List<Boolean>> = _richRadioBtnState

    fun setRoastRadioBtnState(selectedIndex: Int) {
        _roastRadioBtnState.value = updateBtnStateList(selectedIndex, _roastRadioBtnState.value!!)
    }
    fun setGrindSizeRadioBtnState(selectedIndex: Int) {
        _grindSizeRadioBtnState.value = updateBtnStateList(selectedIndex, _grindSizeRadioBtnState.value!!)
    }
    fun setRatingRadioBtnState(selectedIndex: Int) {
        _ratingRadioBtnState.value = updateBtnStateList(selectedIndex, _ratingRadioBtnState.value!!)
    }
    fun setSourRadioBtnState(selectedIndex: Int) {
        _sourRadioBtnState.value = updateBtnStateList(selectedIndex, _sourRadioBtnState.value!!)
    }
    fun setBitterRadioBtnState(selectedIndex: Int) {
        _bitterRadioBtnState.value = updateBtnStateList(selectedIndex, _bitterRadioBtnState.value!!)
    }
    fun setSweetRadioBtnState(selectedIndex: Int) {
        _sweetRadioBtnState.value = updateBtnStateList(selectedIndex, _sweetRadioBtnState.value!!)
    }
    fun setFlavorRadioBtnState(selectedIndex: Int) {
        _flavorRadioBtnState.value = updateBtnStateList(selectedIndex, _flavorRadioBtnState.value!!)
    }
    fun setRichRadioBtnState(selectedIndex: Int) {
        _richRadioBtnState.value = updateBtnStateList(selectedIndex, _richRadioBtnState.value!!)
    }

    // 入力データの保持
    private val _countryValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val countryValues: LiveData<List<String>> = _countryValues

    private val _toolValues: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val toolValues: LiveData<List<String>> = _toolValues

    fun addCountryValue(country: String) {
        _countryValues.value = addList(country, _countryValues.value!!)
    }
    fun removeCountryValue(country: String) {
        _countryValues.value = removeList(country, _countryValues.value!!)
    }

    fun addToolValue(tool: String) {
        _toolValues.value = addList(tool, _toolValues.value!!)
    }
    fun removeToolValue(tool: String) {
        _toolValues.value = removeList(tool, _toolValues.value!!)
    }

    // 絞り込み要素表示テキスト
    val selectedRoastText: LiveData<String> = _roastRadioBtnState.map { list ->
        val roastList: List<String> =
            LocalizationManager.getRoastList()
        buildSelectedText(list) { index -> "${roastList[index]},  "}
    }
    val selectedGrindSizeText: LiveData<String> = _grindSizeRadioBtnState.map { list ->
        val grindSizeList: List<String> =
            LocalizationManager.getGrindSizeList()
        buildSelectedText(list) { index ->
            "${grindSizeList[index]},  "
        }
    }
    val inputCountriesText: LiveData<String> = _countryValues.map { list ->
        if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }
    val inputToolsText: LiveData<String> = _toolValues.map { list ->
        if (list.isEmpty())  return@map ""

        return@map list.joinToString(", ")
    }
    val selectedRatingText: LiveData<String> = _ratingRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatIndex(index) }
    }
    val selectedSourText: LiveData<String> = _sourRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatIndex(index) }
    }
    val selectedBitterText: LiveData<String> = _bitterRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatIndex(index) }
    }
    val selectedSweetText: LiveData<String> = _sweetRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatIndex(index) }
    }
    val selectedFlavorText: LiveData<String> = _flavorRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatIndex(index) }
    }
    val selectedRichText: LiveData<String> = _richRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatIndex(index) }
    }

    // viewModel 初期化
    fun initialize(useCase: GetFilterRecipeOutputDataUseCase) {
        val filterData = useCase.execute("filterRecipeInputData") ?: return

        _countryValues.value = filterData.countries
        _toolValues.value = filterData.tools
        _ratingRadioBtnState.value = filterData.rating
        _sourRadioBtnState.value = filterData.sour
        _bitterRadioBtnState.value = filterData.bitter
        _sweetRadioBtnState.value = filterData.sweet
        _flavorRadioBtnState.value = filterData.flavor
        _richRadioBtnState.value = filterData.rich
        _roastRadioBtnState.value = filterData.roasts
        _grindSizeRadioBtnState.value = filterData.grindSizes
    }
}