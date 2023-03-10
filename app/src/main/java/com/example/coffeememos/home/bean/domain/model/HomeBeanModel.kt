package com.example.coffeememos.home.bean.domain.model

import java.time.LocalDateTime

data class HomeBeanModel(
    val id: Long,
    val country: String,
    val farm: String,
    val district: String,
    val rating: Int,
    val createdAt: LocalDateTime?,
    val isFavorite: Boolean
)
