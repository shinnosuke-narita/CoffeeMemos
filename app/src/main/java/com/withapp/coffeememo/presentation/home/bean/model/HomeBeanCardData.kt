package com.withapp.coffeememo.presentation.home.bean.model

import java.time.LocalDateTime

data class HomeBeanCardData(
    val id: Long,
    val country: String,
    val farm: String,
    val district: String,
    val createdAt: LocalDateTime,
    val rating: String,
    var isFavorite: Boolean
)