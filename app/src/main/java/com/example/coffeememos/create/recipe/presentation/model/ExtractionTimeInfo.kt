package com.example.coffeememos.create.recipe.presentation.model

import com.example.coffeememos.state.InputType

data class ExtractionTimeInfo(
    val inputType: InputType,
    val minutes: Int,  // 手動入力
    val seconds: Int,  // 手動入力
    val autoTime: Long // 自動入力
)
