package com.example.coffeememos.fixture

import com.withapp.coffeememo.infra.data.entity.Recipe
import com.withapp.coffeememo.infra.data.entity.Taste
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

val recipeFixture =
    Recipe(
        id = 0L,
        beanId = 0L,
        country = "ブラジル",
        tool = "ハリオ",
        roast = 1,
        extractionTime = 3.minutes.toLong(DurationUnit.MILLISECONDS),
        preInfusionTime = 30.seconds.toLong(DurationUnit.MILLISECONDS),
        amountExtraction = 500,
        temperature = 90,
        grindSize = 3,
        amountOfBeans = 25,
        comment = "",
        isFavorite = true,
        rating = 5,
        createdAt = System.currentTimeMillis()
    )

val tasteFixture =
    Taste(
        id = 0L,
        recipeId = 0L,
        sour = 3,
        bitter = 3,
        sweet = 3,
        rich = 3,
        flavor = 3
    )