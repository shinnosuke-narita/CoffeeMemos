package com.example.coffeememos.home.bean.presentation.presenter

import com.example.coffeememos.home.bean.domain.model.HomeBeanSource
import com.example.coffeememos.home.bean.presentation.model.HomeBeanOutput

interface HomeBeanPresenter {
    fun presentHomeBeanData(homeBeanSource: HomeBeanSource): HomeBeanOutput
}