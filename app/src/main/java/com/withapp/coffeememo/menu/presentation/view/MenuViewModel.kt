package com.withapp.coffeememo.menu.presentation.view

import androidx.lifecycle.ViewModel
import com.withapp.coffeememo.menu.presentation.model.MenuItem

class MenuViewModel : ViewModel() {
    // リストデータ
    private val _menuItemList: MutableList<MenuItem> = mutableListOf()
    val menuListItem: List<MenuItem> = _menuItemList

    fun initialize(
        titleList: Array<out String>,
        descList: Array<out String>,
        versionName: String) {
        // リセット
        _menuItemList.clear()

        for ((index, title) in titleList.withIndex()) {

            if (index == titleList.size - 1) {
                _menuItemList.add(
                    MenuItem(
                        title,
                        versionName
                    )
                )
                return
            }

            _menuItemList.add(
                MenuItem(
                    title,
                    descList[index]
                )
            )
        }
    }
}