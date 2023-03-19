package com.withapp.coffeememo

import androidx.room.ColumnInfo

data class SimpleBeanInfo(
    @ColumnInfo(name = "bean_id")   val id         : Long,
    @ColumnInfo(name = "country")   val country    : String,
    @ColumnInfo(name = "farm")      val farm       : String,
    @ColumnInfo(name = "district")  val district   : String,
    @ColumnInfo(name = "rating")    val rating     : String,
    @ColumnInfo(name = "isFavorite")val isFavorite : Boolean,
    @ColumnInfo(name = "createdAt") val createdAt  : String)