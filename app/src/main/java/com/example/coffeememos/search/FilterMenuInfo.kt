package com.example.coffeememos.search

import android.view.View
import com.example.coffeememos.state.MenuState

data class FilterMenuInfo(
    val viewId: Int,
    var state: MenuState,
    val tag: String
)