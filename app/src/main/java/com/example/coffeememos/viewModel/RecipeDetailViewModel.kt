package com.example.coffeememos.viewModel

import android.media.Rating
import androidx.lifecycle.*
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.dao.TasteDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.manager.RatingManager.*

class RecipeDetailViewModel(private val beanDao: BeanDao, private val recipeDao: RecipeDao, private val tasteDao: TasteDao) : ViewModel() {
    private lateinit var _recipeRatingManager: RatingManager
    private lateinit var _beanRatingManager: RatingManager

    private val _recipeId: MutableLiveData<Long> = MutableLiveData()

    private val _beanId: MutableLiveData<Long> = MutableLiveData(0)

    val selectedRecipe: LiveData<Recipe> = _recipeId.switchMap { recipeId ->
        liveData {
            emit(recipeDao.getRecipeByID(recipeId))
        }
    }

    val selectedTaste: LiveData<Taste> = _recipeId.switchMap { recipeId ->
        liveData {
            emit(tasteDao.getTasteByRecipeId(recipeId))
        }
    }

    val selectedBean: LiveData<Bean> = _beanId.switchMap { beanId ->
        liveData{
            emit(beanDao.getBeanById(beanId))
        }
    }

    val recipeStarList: LiveData<List<Star>> = selectedRecipe.switchMap { recipe ->
        val result = MutableLiveData<List<Star>>()
        _recipeRatingManager.changeRatingState(recipe.rating)
        result.value = _recipeRatingManager.starList
        _recipeCurrentRating.value = _recipeRatingManager.currentRating

        result
    }

    val beanStarList: LiveData<List<Star>> = selectedBean.switchMap { bean ->
        val result = MutableLiveData<List<Star>>()
        _beanRatingManager.changeRatingState(bean.rating)
        result.value = _beanRatingManager.starList
        _beanCurrentRating.value = _beanRatingManager.currentRating

        result
    }

    private var _recipeCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val recipeCurrentRating: LiveData<Int> = _recipeCurrentRating

    private var _beanCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val beanCurrentRating: LiveData<Int> = _beanCurrentRating

    fun initialize(recipeId: Long, beanId: Long, recipeRatingManager: RatingManager, beanRatingManager: RatingManager) {
        _recipeId.value       = recipeId
        _beanId.value         = beanId
        _recipeRatingManager  = recipeRatingManager
        _beanRatingManager    = beanRatingManager
    }
}



class RecipeDetailViewModelFactory(
    private val beanDao  : BeanDao,
    private val recipeDao: RecipeDao,
    private val tasteDao : TasteDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeDetailViewModel::class.java)) {
            return RecipeDetailViewModel(beanDao, recipeDao, tasteDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
    }
}