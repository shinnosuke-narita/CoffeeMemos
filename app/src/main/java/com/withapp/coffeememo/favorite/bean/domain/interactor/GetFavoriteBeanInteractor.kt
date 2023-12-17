package com.withapp.coffeememo.favorite.bean.domain.interactor

import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.favorite.bean.domain.repository.StorageRepository
import com.withapp.coffeememo.favorite.bean.domain.use_case.GetFavoriteBeanUseCase
import javax.inject.Inject

class GetFavoriteBeanInteractor @Inject constructor()
    : GetFavoriteBeanUseCase {
    @Inject
    lateinit var repository: StorageRepository

    override suspend fun handle(): List<Bean> {
        return repository.getFavoriteBeans()
    }
}