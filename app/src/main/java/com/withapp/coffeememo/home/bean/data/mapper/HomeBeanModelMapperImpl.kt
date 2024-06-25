package com.withapp.coffeememo.home.bean.data.mapper

import com.withapp.coffeememo.home.bean.data.model.HomeBeanData
import com.withapp.coffeememo.domain.model.bean.HomeBeanModel
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class HomeBeanModelMapperImpl @Inject constructor()
    : HomeBeanModelMapper {
    override fun execute(bean: HomeBeanData): HomeBeanModel {
        // interactorにnull処理は委譲
        val createdAt: LocalDateTime? =
            convertLocalDate(bean.createdAt)
        return HomeBeanModel(
            bean.id,
            bean.country,
            bean.farm,
            bean.district,
            bean.rating,
            createdAt,
            bean.isFavorite
        )
    }

    // LocalDateTime型に変換
    private fun convertLocalDate(createdAt: Long): LocalDateTime? {
        val createdDateTime: LocalDateTime?
        try {
            createdDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(createdAt),
                ZoneId.systemDefault()
            )
        } catch (e: DateTimeException) {
            e.printStackTrace()
            return null
        }

        return createdDateTime
    }
}