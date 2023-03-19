package com.withapp.coffeememo.home.bean.presentation.model

data class HomeBeanOutput(
    val newBeans: List<HomeBeanCardData>,
    val favoriteBeans: List<HomeBeanCardData>,
    val highRatingBeans: List<HomeBeanCardData>,
    val totalCount: String,
    val todayCount: String,
    val beanExists: Boolean
)