package com.example.coffeememos.fixture

import com.withapp.coffeememo.domain.model.bean.SearchBeanModel
import com.withapp.coffeememo.infra.data.entity.Bean

val beanFixture =
    Bean(
        id = 0,
        country = "",
        farm = "",
        district = "",
        species = "",
        elevationFrom = 1000,
        elevationTo = 2000,
        process = 3,
        store = "",
        comment = "",
        rating = 3,
        isFavorite = true,
        createdAt = System.currentTimeMillis(),
    )

val searchBeanModelFixture =
    SearchBeanModel(
        id = 0,
        country = "",
        farm = "",
        district = "",
        species = "",
        process = 3,
        store = "",
        rating = 3,
        isFavorite = true,
        createdAt = System.currentTimeMillis(),
    )
