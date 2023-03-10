package com.example.coffeememos.search.bean.domain.iterator



import com.example.coffeememos.search.bean.domain.repository.SearchBeanDiskRepository
import com.example.coffeememos.search.bean.domain.use_case.UpdateFavoriteBeanUseCase
import javax.inject.Inject

class UpdateFavoriteBeanInterator @Inject constructor()
    : UpdateFavoriteBeanUseCase {
    @Inject
    lateinit var repository: SearchBeanDiskRepository

    override suspend fun handle(beanId: Long, isFavorite: Boolean) {
        repository.updateFavorite(beanId, isFavorite)
    }
}