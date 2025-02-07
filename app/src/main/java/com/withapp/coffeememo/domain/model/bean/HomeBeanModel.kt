package com.withapp.coffeememo.domain.model.bean

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
