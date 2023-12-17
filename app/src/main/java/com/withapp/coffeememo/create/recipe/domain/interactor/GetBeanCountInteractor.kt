package com.withapp.coffeememo.create.recipe.domain.interactor

import com.withapp.coffeememo.create.recipe.domain.repository.StorageRepository
import com.withapp.coffeememo.create.recipe.domain.use_case.GetBeanCountUseCase
import javax.inject.Inject

class GetBeanCountInteractor @Inject constructor()
    : GetBeanCountUseCase {
    @Inject
    lateinit var repository: StorageRepository

    override suspend fun handle(): Int {
        return repository.getBeanCount()
    }
}