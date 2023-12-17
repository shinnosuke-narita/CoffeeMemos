package com.withapp.coffeememo.search.common.presentation.view_model

import android.view.View
import androidx.lifecycle.ViewModel
import com.withapp.coffeememo.utilities.StringUtil

open class BaseFilterViewModel : ViewModel() {
    // 現在開いているメニューのタグを保持する
    protected var currentOpenViewTag: String? = null

    protected fun isSameAsCurrentOpenedView(clickedView: View): Boolean {
        return clickedView.tag.toString() == currentOpenViewTag
    }

    protected fun notExistCurrentOpenedView(): Boolean {
        return currentOpenViewTag == null
    }

    protected fun updateBtnStateList(selectedIndex: Int, currentList: List<Boolean>): MutableList<Boolean> {
        val updatedList = MutableList(currentList.size) { i -> currentList[i]}

        for ((i, state) in currentList.withIndex()) {
            if (i != selectedIndex) continue

            updatedList[i] = !state
        }
        return updatedList
    }

    protected fun buildSelectedText(list: List<Boolean>, processData: (Int) -> String): String {
        var resultText = ""
        for ((i, isSelected) in list.withIndex()) {
            if (isSelected) {
                resultText += processData(i)
            }
        }
        // 未選択の場合、空文字列を返す
        if (resultText.isEmpty()) return resultText

        return StringUtil.subStringLastSeparator(resultText, ",")
    }

    // 入力されたデータを更新する共通処理
    protected fun addList(value: String, currentValues: List<String>): List<String> {
        val result = mutableListOf<String>()
        result.addAll(currentValues)
        result.add(value)
        return result
    }
    protected fun removeList(value: String, currentValue: List<String>): List<String> {
        val result = mutableListOf<String>()
        result.addAll(currentValue)
        result.remove(value)
        return result
    }

    // indexをフォーマットする。
    protected fun formatIndex(index: Int): String = "${index + 1}.0,  "
}