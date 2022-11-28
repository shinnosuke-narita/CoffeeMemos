package com.example.coffeememos.viewModel

import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coffeememos.search.FilterMenuInfo
import com.example.coffeememos.state.MenuState

class FilterViewModel : ViewModel() {
    // 隠れているコンテナの高さ(EditText)
    var inputTextContainerHeight: Float = 0f

    // 隠れているコンテナの高さ(RadioGroup)
    var radioGroupContainerHeight: Float = 0f


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

    private lateinit var menuInfoList:MutableList<FilterMenuInfo>

    fun makeMenuInfoList(wrapperList: List<ViewGroup>) {
        val result: MutableList<FilterMenuInfo> = mutableListOf()

        for (wrapper in wrapperList) {
           result.add(FilterMenuInfo(wrapper.id, MenuState.CLOSE, wrapper.tag.toString()))
        }

        menuInfoList = result
    }

    fun updateMenuInfoList(id: Int, state: MenuState) {
        for (menuInfo in menuInfoList) {
            if (menuInfo.viewId == id) {
                menuInfo.state = state
                break
            }
        }
    }

    fun calculateOpenContainerHeight(clickedIndex: Int): Float  {
        var totalOpenContainerHeight = 0F
        var inputTextContainerCount = 0
        var radioGroupContainerCount = 0

        for ((index, menuInfo) in menuInfoList.withIndex()) {
            if (clickedIndex <= index) break

            if (menuInfo.state == MenuState.OPEN) {
                if (menuInfo.tag == "editText") {
                    inputTextContainerCount++
                }
                if (menuInfo.tag == "radioGroup") {
                    radioGroupContainerCount++
                }
            }
        }

        totalOpenContainerHeight = (inputTextContainerHeight * inputTextContainerCount) + (radioGroupContainerHeight * radioGroupContainerCount)
        return totalOpenContainerHeight
    }
}