package com.withapp.coffeememo.home.bean.presentation.model

data class HomeBeanCardData(
    val id: Long,
    val country: String,
    val farm: String,
    val district: String,
    val createdAt: String,
    val rating: String,
    var isFavorite: Boolean
)