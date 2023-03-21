package com.withapp.coffeememo.menu.presentation.view

import androidx.lifecycle.ViewModel
import com.withapp.coffeememo.menu.presentation.model.MenuItem

class MenuViewModel : ViewModel() {
    val titleList: List<String> =
        listOf(
            "ライセンス",
            "著作権",
            "プライバシーポリシー",
            "バージョン"
        )

    // リストデータ
    private val _menuItemList: MutableList<MenuItem> = mutableListOf()
    val menuListItem: List<MenuItem> = _menuItemList


    fun initialize(descList: Array<out String>, versionName: String) {
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