package com.withapp.coffeememo.favorite.bean.domain.model

data class FavoriteBeanModel(
    val id: Long,
    val country: String,
    val farm: String,
    val district: String,
    val species: String,
    val process: String,
    val store: String,
    val elevation: String,
    val comment: String,
    val rating: Int,
    val isFavorite: Boolean,
    val createdAt: String
)