package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.manager.RatingManager
import kotlinx.coroutines.launch

class EditRecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {
    // 選択されたレシピ
    private val _selectedRecipe: MutableLiveData<Recipe> = MutableLiveData()
    val selectedRecipe: LiveData<Recipe> = _selectedRecipe


    // Rating 関連
    private lateinit var _ratingManager: RatingManager

    private var _recipeCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val recipeCurrentRating: LiveData<Int> = _recipeCurrentRating

    private val _recipeStarList: MutableLiveData<List<RatingManager.Star>> = MutableLiveData(listOf())
    val recipeStarList: LiveData<List<RatingManager.Star>> = _recipeStarList

    fun updateRatingState(selectedRate: Int) {
        _ratingManager.changeRatingState(selectedRate)

        _recipeCurrentRating.value = _ratingManager.currentRating
        _recipeStarList.value      = _ratingManager.starList
    }

    // Favorite 関連
    private val _currentFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val currentFavorite: LiveData<Boolean> = _currentFavorite

    fun setFavorite(isFavorite: Boolean) {
        _currentFavorite.value = isFavorite
    }


    // 更新データの保持
    private var _tool: String = ""
    private var _amountBeans: Int = 0
    private var _temperature: Int = 0
    private var _preInfusionTime: Int = 0
    private var _extractionTimeMinutes: Int = 0
    private var _extractionTimeSeconds: Int = 0
    private var _amountExtraction: Int = 0
    private var _comment: String = ""

    fun setTool(tool: String)                                { _tool = tool }
    fun setAmountBeans(amountBeans: Int)                     { _amountBeans = amountBeans }
    fun setTemperature(temperature: Int)                     { _temperature = temperature}
    fun setPreInfusionTime(preInfusionTime: Int)             { _preInfusionTime = preInfusionTime }
    fun setExtractionTimeMinutes(extractionTimeMinutes: Int) {_extractionTimeMinutes = extractionTimeMinutes }
    fun setExtractionTimeSeconds(extractionTimeSeconds: Int) {_extractionTimeSeconds = extractionTimeSeconds }
    fun setAmountExtraction(amountExtraction: Int)           {_amountExtraction = amountExtraction }
    fun setComment(comment: String)                          { _comment = comment }


    fun initialize(id: Long, ratingManager: RatingManager) {
        // RatingManagerを先に初期化する！（アプリ落ちる）
        _ratingManager = ratingManager

        viewModelScope.launch {
            val selectedRecipe: Recipe = recipeDao.getRecipeById(id)
            _selectedRecipe.value = selectedRecipe

            updateRatingState(selectedRecipe.rating)

            _currentFavorite.value = selectedRecipe.isFavorite
        }
    }







}

class EditRecipeViewModelFactory(
    private val recipeDao  : RecipeDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditRecipeViewModel::class.java)) {
            return EditRecipeViewModel(recipeDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
    }
}