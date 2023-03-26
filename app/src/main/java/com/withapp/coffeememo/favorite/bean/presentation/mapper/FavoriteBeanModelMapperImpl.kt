package com.withapp.coffeememo.favorite.bean.presentation.mapper

import com.withapp.coffeememo.Constants
import com.withapp.coffeememo.entity.Bean
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.utilities.DateUtil
import javax.inject.Inject

class FavoriteBeanModelMapperImpl @Inject constructor()
    : FavoriteBeanModelMapper{
    override fun execute(bean: Bean): FavoriteBeanModel {
        // createdAt 変換
        val createdAt: String =
            DateUtil.formatEpochTimeMills(
                bean.createdAt,
                DateUtil.pattern
            )

        // process 変換
        val process: String =
            Constants.processList[bean.process]

        // elevation 変換
        val elevationStr: String =
            convertElevation(
                bean.elevationFrom,
                bean.elevationTo
            )

        return FavoriteBeanModel(
            id = bean.id,
            country = bean.country,
            farm = bean.farm,
            district = bean.district,
            species = bean.species,
            elevation = elevationStr,
            process = process,
            store = bean.store,
            comment = bean.comment,
            rating = bean.rating,
            isFavorite = bean.isFavorite,
            createdAt = createdAt
        )
    }

    private fun convertElevation(
        elevationFrom: Int,
        elevationTo: Int
    ): String {
        if (elevationFrom == 0 && elevationTo == 0) {
            return ""
        }

        return String.format(
            "%dm ~ %dm",
            elevationFrom,
            elevationTo
        )
    }
}