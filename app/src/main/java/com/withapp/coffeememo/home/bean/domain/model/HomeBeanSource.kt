package com.withapp.coffeememo.home.bean.domain.model

data class HomeBeanSource(
    val newBeans: List<HomeBeanModel>,
    val favoriteBeans: List<HomeBeanModel>,
    val highRatingBeans: List<HomeBeanModel>,
    val totalCount: Int,
    val todayCount: Int,
)