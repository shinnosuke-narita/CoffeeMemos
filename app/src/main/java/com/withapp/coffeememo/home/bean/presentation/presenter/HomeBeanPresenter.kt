package com.withapp.coffeememo.home.bean.presentation.presenter

import com.withapp.coffeememo.home.bean.domain.model.HomeBeanSource
import com.withapp.coffeememo.home.bean.presentation.model.HomeBeanOutput

interface HomeBeanPresenter {
    fun presentHomeBeanData(homeBeanSource: HomeBeanSource): HomeBeanOutput
}