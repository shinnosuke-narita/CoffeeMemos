package com.withapp.coffeememo.domain.interactor.bean

import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.home.bean.data.mapper.HomeBeanModelMapper
import com.withapp.coffeememo.domain.model.bean.HomeBeanModel
import com.withapp.coffeememo.domain.model.bean.HomeBeanSource
import com.withapp.coffeememo.domain.usecase.bean.GetHomeBeanDataUseCase
import java.time.LocalDate
import javax.inject.Inject

class GetHomeBeanDataUseCaseInteractor @Inject constructor(
    private val beanRepo: BeanRepository,
    private val mapper: HomeBeanModelMapper
) : GetHomeBeanDataUseCase {
    private val maxDisplayNum = 15

    override suspend fun handle(): HomeBeanSource {
        val beans = beanRepo.getHomeBeanData().map { mapper.execute(it) }
        val sortedBeans = beans.sortedByDescending { bean -> bean.id }

        // 新しい順コーヒー豆
        val newBeans = sortedBeans.take(maxDisplayNum)
        // お気に入りコーヒー豆
        val favoriteBeans = getFavoriteBeans(sortedBeans)
        // 高評価コーヒー豆
        val highRatingBeans = getHighRatingBeans(sortedBeans)
        // 総コーヒー豆数
        val totalCount = beans.size
        // 今日のコーヒー豆数
        val todayBeansCount = getTodayBeanCount(beans)

        return HomeBeanSource(
            newBeans,
            favoriteBeans,
            highRatingBeans,
            totalCount,
            todayBeansCount)
    }

    private fun getTodayBeanCount(
        beans: List<HomeBeanModel>
    ): Int {
        if (beans.isEmpty()) return 0

        var result = 0
        val today: LocalDate = LocalDate.now()
        for (bean in beans) {
            if (bean.createdAt == null) continue

            val year = bean.createdAt.year
            val month = bean.createdAt.month
            val day = bean.createdAt.dayOfMonth
            val createdAt = LocalDate.of(year, month, day)

            if (createdAt == today) {
                result++
            }
        }

        return result
    }

    private fun getFavoriteBeans(beans: List<HomeBeanModel>)
        : List<HomeBeanModel> {
        if (beans.isEmpty()) return listOf()

        return beans
            .filter { bean -> bean.isFavorite }
            .take(maxDisplayNum)
    }

    private fun getHighRatingBeans(beans: List<HomeBeanModel>)
        : List<HomeBeanModel> {
        if (beans.isEmpty()) return listOf()

        return beans
            .sortedByDescending { bean -> bean.rating }
            .take(maxDisplayNum)
    }
}