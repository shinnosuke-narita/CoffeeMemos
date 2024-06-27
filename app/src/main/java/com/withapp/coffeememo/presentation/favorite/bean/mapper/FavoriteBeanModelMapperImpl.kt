package com.withapp.coffeememo.presentation.favorite.bean.mapper

import com.withapp.coffeememo.infra.ad_mob.locale.LocalizationManager
import com.withapp.coffeememo.infra.data.entity.Bean
import com.withapp.coffeememo.domain.model.bean.FavoriteBeanModel
import javax.inject.Inject

class FavoriteBeanModelMapperImpl @Inject constructor()
    : FavoriteBeanModelMapper {
    override fun execute(bean: Bean): FavoriteBeanModel {
        // process 変換
        val processList: List<String> =
            LocalizationManager.getProcessList()
        val process: String =
            processList[bean.process]

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
            createdAt = bean.createdAt
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