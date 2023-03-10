package com.example.coffeememos.home.bean.presentation.presenter

import com.example.coffeememos.home.bean.domain.model.HomeBeanSource
import com.example.coffeememos.home.bean.presentation.mapper.HomeBeanCardModelMapper
import com.example.coffeememos.home.bean.presentation.model.HomeBeanOutput
import javax.inject.Inject

class HomeBeanPresenterImpl @Inject constructor()
    : HomeBeanPresenter {
    @Inject
    lateinit var mapper: HomeBeanCardModelMapper

    override fun presentHomeBeanData(
        homeBeanSource: HomeBeanSource
    ): HomeBeanOutput {
        val newBeans =
            homeBeanSource.newBeans.map { bean ->
                mapper.execute(bean)
            }
        val highRatingBeans =
            homeBeanSource.highRatingBeans.map { bean ->
                mapper.execute(bean)
            }
        val favoriteBeans =
            homeBeanSource.favoriteBeans.map { bean ->
                mapper.execute(bean)
            }

        return HomeBeanOutput(
            newBeans,
            favoriteBeans,
            highRatingBeans,
            homeBeanSource.totalCount.toString(),
            homeBeanSource.todayCount.toString(),
            beanExists(homeBeanSource.totalCount)
        )
    }

    private fun beanExists(totalCount: Int): Boolean {
        if (totalCount == 0) return false
        return true
    }
}