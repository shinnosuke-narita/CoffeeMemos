package com.withapp.coffeememo.domain.model.bean

import androidx.room.ColumnInfo

data class SearchBeanModel (
    @ColumnInfo(name = "bean_id")
    val id: Long,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "farm")
    val farm: String,
    @ColumnInfo(name = "district")
    val district: String,
    @ColumnInfo(name = "store")
    val store: String,
    @ColumnInfo(name = "process")
    val process: Int,
    @ColumnInfo(name = "species")
    val species: String,
    @ColumnInfo(name = "rating")
    val rating: Int,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean,
    @ColumnInfo(name = "createdAt")
    val createdAt: Long)
