package com.example.coffeememos.viewModel

import androidx.lifecycle.ViewModel

class FilterViewModel : ViewModel() {
    // 隠れているテキスト入力タイプのコンテナの高さ
    var inputTextContainerHeight: Int = 0

    // 隠れているラジオボタンタイプのコンテナの高さ
    var radioGroupContainerHeight: Int = 0

}