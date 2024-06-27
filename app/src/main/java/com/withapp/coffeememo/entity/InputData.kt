package com.withapp.coffeememo.entity

data class InputData (
    val beanId: Long,
    val country: String,
    val tool: String,
    val roast: Int,
    val extractionTime: Long,
    val preInfusionTime: Long,
    val amountExtraction: Int,
    val temperature: Int,
    val grindSize: Int,
    val amountOfBean: Int,
    val comment: String,
    val isFavorite: Boolean,
    val rating: Int,
    val createdAt: Long,
    val sour: Int,
    val bitter: Int,
    val sweet: Int,
    val flavor: Int,
    val rich: Int
)