package com.withapp.coffeememo.home.bean.presentation.mapper

import com.withapp.coffeememo.home.bean.domain.model.HomeBeanModel
import com.withapp.coffeememo.home.bean.presentation.model.HomeBeanCardData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeBeanCardModelMapperImpl @Inject constructor()
    : HomeBeanCardModelMapper {
    private val pattern: String = "yyyy/MM/dd HH:mm"

    override fun execute(homeBeanModel: HomeBeanModel): HomeBeanCardData {
        // createdAt 変換
        val createdAtStr =
            convertDate(homeBeanModel.createdAt) ?: ""

        return HomeBeanCardData(
            homeBeanModel.id,
            homeBeanModel.country,
            homeBeanModel.farm,
            homeBeanModel.district,
            createdAtStr,
            homeBeanModel.rating.toString(),
            homeBeanModel.isFavorite
        )
    }

    private fun convertDate(localDateTime: LocalDateTime?): String? {
        if (localDateTime == null) return localDateTime

        val formatter = DateTimeFormatter.ofPattern(pattern)
        return localDateTime.format(formatter)
    }
}