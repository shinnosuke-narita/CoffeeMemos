package com.withapp.coffeememo.data.taste

import com.withapp.coffeememo.core.data.dao.TasteDao
import com.withapp.coffeememo.core.data.entity.Taste
import com.withapp.coffeememo.domain.repository.TasteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasteRepositoryImpl @Inject constructor(
    private val tasteDao: TasteDao
) : TasteRepository {
    override suspend fun insert(taste: Taste) {
        tasteDao.insert(taste)
    }

    override suspend fun updateTaste(taste: Taste) {
        tasteDao.updateTaste(taste)
    }

    override fun getAll(): Flow<List<Taste>> {
        return tasteDao.getAll()
    }

    override suspend fun getTasteByRecipeId(recipeId: Long): Taste {
        return tasteDao.getTasteByRecipeId(recipeId)
    }

    override suspend fun getTasteIdByRecipeId(recipeId: Long): Long {
        return tasteDao.getTasteIdByRecipeId(recipeId)
    }

    override suspend fun getTasteById(id: Long): Taste {
        return tasteDao.getTasteById(id)
    }
}