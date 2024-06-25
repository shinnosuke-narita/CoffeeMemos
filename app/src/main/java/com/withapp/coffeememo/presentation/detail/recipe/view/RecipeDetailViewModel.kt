package com.withapp.coffeememo.presentation.detail.recipe.view

import androidx.lifecycle.*
import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.core.data.entity.Taste
import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.domain.repository.TasteRepository
import com.withapp.coffeememo.entity.Rating
import com.withapp.coffeememo.entity.Rating.Star
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val beanDao: BeanRepository,
    private val recipeDao: RecipeRepository,
    private val tasteDao: TasteRepository
) : ViewModel() {
    private lateinit var _recipeRatingManager: Rating
    private lateinit var _beanRatingManager: Rating

    private var _selectedRecipe: MutableLiveData<Recipe> = MutableLiveData()
    val selectedRecipe: LiveData<Recipe> = _selectedRecipe

    private var _selectedBean: MutableLiveData<Bean> = MutableLiveData()
    val selectedBean: LiveData<Bean> = _selectedBean

    private var _selectedTaste: MutableLiveData<Taste> = MutableLiveData()
    val selectedTaste: LiveData<Taste> = _selectedTaste

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

    fun initialize(
        recipeId: Long,
        beanId: Long,
        recipeRatingManager:
        Rating,
        beanRatingManager: Rating
    ) {
        // RatingManagerを先に初期化する！（アプリ落ちる）
        _recipeRatingManager  = recipeRatingManager
        _beanRatingManager    = beanRatingManager

        viewModelScope.launch {
            _selectedRecipe.postValue(recipeDao.getRecipeById(recipeId))
            _selectedBean.postValue(beanDao.getBeanById(beanId))
            _selectedTaste.postValue(tasteDao.getTasteByRecipeId(recipeId))
        }

    }

    fun updateTaste(sour: Int, bitter: Int, sweet: Int, flavor: Int, rich: Int) {
        val newTaste = Taste(
            _selectedTaste.value!!.id,
            _selectedRecipe.value!!.id,
            sour,
            bitter,
            sweet,
            flavor,
            rich
        )

        viewModelScope.launch {
            tasteDao.updateTaste(newTaste)
            _selectedTaste.postValue(tasteDao.getTasteById(selectedTaste.value!!.id))
        }
    }

    fun updateBean(id: Long) {
        viewModelScope.launch {
            _selectedBean.postValue(beanDao.getBeanById(id))
        }
    }

    fun updateRecipe(id: Long) {
        viewModelScope.launch {
            _selectedRecipe.postValue(recipeDao.getRecipeById(id))
        }
    }

    fun deleteRecipe() {
        viewModelScope.launch {
            recipeDao.deleteById(_selectedRecipe.value!!.id)
        }
    }
}