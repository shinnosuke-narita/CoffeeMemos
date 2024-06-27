package com.withapp.coffeememo.infra.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "bean")
data class Bean(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bean_id") val id: Long,
    val country: String,
    val farm: String,
    val district: String,
    val species: String,
    val elevationFrom: Int,
    val elevationTo: Int,
    val process: Int,
    val store: String,
    val comment: String,
    val rating: Int,
    val isFavorite: Boolean,
    val createdAt: Long
) : Serializable
