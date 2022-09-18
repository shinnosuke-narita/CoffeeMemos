package com.example.coffeememos.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "bean")
data class Bean(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bean_id") val id: Long,
    val country: String,
    val farm: String,
    val district: String,
    val species: String,
    val elevationFrom: String,
    val elevationTo: String,
    val process: String,
    val store: String,
    val comment: String,
    val review: Int,
    val image: Int,
    val createdAt: String
)
