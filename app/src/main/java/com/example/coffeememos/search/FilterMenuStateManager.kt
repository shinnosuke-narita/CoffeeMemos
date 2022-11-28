package com.example.coffeememos.search

import android.view.View
import android.view.ViewGroup
import com.example.coffeememos.utilities.AnimUtil

class FilterMenuStateManager {
    // アニメーションで動かす距離
    var inputTextHeight: Float = 0F
    var radioGroupHeight: Float = 0F

    val viewList: List<ViewGroup> = listOf()

    val openViewList: MutableList<View> = mutableListOf()


    fun openMenu(clickedIndex: Int) {
        for ((index, targetView) in viewList.withIndex()) {
            if (index <= clickedIndex) continue

            AnimUtil.translateYAnimation(targetView, 500L, 0f, inputTextHeight)
        }
    }

    fun closeMenu(clickedIndex: Int) {
        for ((index, targetView) in viewList.withIndex()) {
            if (index <= clickedIndex) continue

            AnimUtil.translateYAnimation(targetView, 500L,  inputTextHeight, 0f)
        }
    }

    fun changeSate(clickedView: View) {
        // タッチされたviewを渡す

        // openViewListが空または、タッチされたviewが含まれていなかったら、open
        // タッチされたviewが含まれていたら、close リストから削除





    }
}