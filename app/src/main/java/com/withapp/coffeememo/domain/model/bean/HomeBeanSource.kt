package com.withapp.coffeememo.domain.model.bean

data class HomeBeanSource(
    val newBeans: List<HomeBeanModel>,
    val favoriteBeans: List<HomeBeanModel>,
    val highRatingBeans: List<HomeBeanModel>,
    val totalCount: Int,
    val todayCount: Int,
)