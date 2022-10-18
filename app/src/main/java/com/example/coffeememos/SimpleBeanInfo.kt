package com.example.coffeememos

data class SimpleBeanInfo(
    val id         : Long,
    val country    : String,
    val farm       : String,
    val district   : String,
    val rating     : String,
    val isFavorite : Boolean,
    val createdAt  : String)
