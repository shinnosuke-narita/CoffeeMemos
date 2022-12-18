package com.example.coffeememos.viewModel

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.coffeememos.utilities.StringUtil

open class BaseFilterViewModel : ViewModel() {
    // 現在開いているメニューのタグを保持する
    protected var currentOpenViewTag: String? = null

    protected fun isSameAsCurrentOpenedView(clickedView: View): Boolean {
        return clickedView.tag.toString() == currentOpenViewTag
    }

    protected fun notExistCurrentOpenedView(): Boolean {
        return currentOpenViewTag == null
    }

    protected fun updateBtnStateList(selectedIndex: Int, currentList: MutableList<Boolean>): MutableList<Boolean> {
        val updatedList = MutableList(currentList.size) { i -> currentList[i]}

        for ((i, state) in currentList.withIndex()) {
            if (i != selectedIndex) continue

            updatedList[i] = !state
        }
        return updatedList
    }

    protected fun buildSelectedText(list: MutableList<Boolean>, processData: (Int) -> String): String {
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


}