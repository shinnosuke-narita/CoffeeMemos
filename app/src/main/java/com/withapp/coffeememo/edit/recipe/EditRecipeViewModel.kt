package com.withapp.coffeememo.edit.recipe

import android.content.Context
import androidx.lifecycle.*
import com.withapp.coffeememo.core.data.dao.RecipeDao
import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.entity.Rating
import com.withapp.coffeememo.utilities.DateUtil
import com.withapp.coffeememo.utilities.Util
import com.withapp.coffeememo.validate.RecipeValidationLogic
import com.withapp.coffeememo.validate.ValidationInfo
import com.withapp.coffeememo.base.viewmodel.BaseViewModel
import com.withapp.coffeememo.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRecipeViewModel @Inject constructor(
    private val recipeRepo: RecipeRepository
) : BaseViewModel() {
    // 選択されたレシピ
    private val _selectedRecipe: MutableLiveData<Recipe> = MutableLiveData()
    val selectedRecipe: LiveData<Recipe> = _selectedRecipe

    // Rating 関連
    private var _ratingManager: Rating? = null
    val ratingManager: Rating
        get() = _ratingManager!!

    private var _recipeCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val recipeCurrentRating: LiveData<Int> = _recipeCurrentRating

    private val _recipeStarList: MutableLiveData<List<Rating.Star>> = MutableLiveData(listOf())
    val recipeStarList: LiveData<List<Rating.Star>> = _recipeStarList

    fun updateRatingState(selectedRate: Int) {
        ratingManager.changeRatingState(selectedRate)

        _recipeCurrentRating.value = ratingManager.currentRating
        _recipeStarList.value      = ratingManager.starList
    }

    // Favorite 関連
    private val _currentFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val currentFavorite: LiveData<Boolean> = _currentFavorite

    fun setFavorite(isFavorite: Boolean) {
        _currentFavorite.value = isFavorite
    }

    // Roast
    private val _currentRoast: MutableLiveData<Int> = MutableLiveData(0)
    val currentRoast: LiveData<Int> = _currentRoast

    fun setRoast(roast: Int) {
        _currentRoast.value = roast
    }

    // Grind Size
    private val _currentGrind: MutableLiveData<Int> = MutableLiveData(0)
    val currentGrind: LiveData<Int> = _currentGrind

    fun setGrind(grind: Int) {
        _currentGrind.value = grind
    }

    // Validation
    private val _temperatureValidation: MutableLiveData<ValidationInfo> = MutableLiveData()
    val temperatureValidation: LiveData<ValidationInfo> = _temperatureValidation

    private val _extractionTimeValidation: MutableLiveData<ValidationInfo> = MutableLiveData()
    val extractionTimeValidation: LiveData<ValidationInfo> = _extractionTimeValidation

    private val _toolValidation: MutableLiveData<ValidationInfo> = MutableLiveData()
    val toolValidation: LiveData<ValidationInfo> = _toolValidation

    // validationエラーの場合、true
    fun validateRecipeData(context: Context): Boolean {
        var validationMessage = ""

        // tool
        validationMessage = RecipeValidationLogic.validateTool(context, _tool)
        if (validationMessage.isNotEmpty()) {
            setValidationInfoAndResetAfterDelay(_toolValidation, validationMessage)
            return true
        }
        // temperature
        validationMessage = RecipeValidationLogic.validateTemperature(context, _temperature)
        if (validationMessage.isNotEmpty()) {
            setValidationInfoAndResetAfterDelay(_temperatureValidation, validationMessage)
            return true
        }
        // extractionTime
        validationMessage = RecipeValidationLogic.validateExtractionTimeMinutes(context, _extractionTimeMinutes)
        if (validationMessage.isNotEmpty()) {
            setValidationInfoAndResetAfterDelay(_extractionTimeValidation, validationMessage)
            return true
        }
        validationMessage = RecipeValidationLogic.validateExtractionTimeSeconds(context, _extractionTimeSeconds)
        if (validationMessage.isNotEmpty()) {
            setValidationInfoAndResetAfterDelay(_extractionTimeValidation, validationMessage)
            return true
        }

        return false
    }

    // 更新データの保持
    private var _tool                 : String = ""
    private var _comment              : String = ""
    private var _amountBeans          : Int = 0
    private var _temperature          : Int = 0
    private var _preInfusionTime      : Int = 0
    private var _extractionTimeMinutes: Int = 0
    private var _extractionTimeSeconds: Int = 0
    private var _amountExtraction     : Int = 0

    fun setTool(tool: String)                                   { _tool = tool }
    fun setAmountBeans(amountBeans: String)                     { _amountBeans = Util.convertStringIntoIntIfPossible(amountBeans) }
    fun setTemperature(temperature: String)                     { _temperature = Util.convertStringIntoIntIfPossible(temperature) }
    fun setPreInfusionTime(preInfusionTime: String)             { _preInfusionTime = Util.convertStringIntoIntIfPossible(preInfusionTime) }
    fun setExtractionTimeMinutes(extractionTimeMinutes: String) { _extractionTimeMinutes = Util.convertStringIntoIntIfPossible(extractionTimeMinutes) }
    fun setExtractionTimeSeconds(extractionTimeSeconds: String) { _extractionTimeSeconds = Util.convertStringIntoIntIfPossible(extractionTimeSeconds) }
    fun setAmountExtraction(amountExtraction: String)           { _amountExtraction = Util.convertStringIntoIntIfPossible(amountExtraction) }
    fun setComment(comment: String)                             { _comment = comment }


    fun initialize(id: Long, ratingManager: Rating) {
        if (_ratingManager != null) return

        // RatingManagerを先に初期化する！（アプリ落ちる）
        _ratingManager = ratingManager

        viewModelScope.launch {
            val selectedRecipe: Recipe = recipeRepo.getRecipeById(id)

            updateRatingState(selectedRecipe.rating)
            _selectedRecipe.value  = selectedRecipe
            _currentFavorite.value = selectedRecipe.isFavorite
            _currentRoast.value    = selectedRecipe.roast
            _currentGrind.value    = selectedRecipe.grindSize
        }
    }

    fun updateRecipe() {
        viewModelScope.launch {
            val extractionTime = DateUtil.convertSecondsIntoMills(_extractionTimeMinutes, _extractionTimeSeconds)
            val preInfusionTime = DateUtil.convertSecondsIntoMills(_preInfusionTime)

            recipeRepo.update(
                Recipe(
                    id                    = _selectedRecipe.value!!.id,
                    beanId                = _selectedRecipe.value!!.beanId,
                    country               = _selectedRecipe.value!!.country,
                    tool                  = _tool,
                    roast                 = _currentRoast.value!!,
                    extractionTime        = extractionTime,
                    preInfusionTime       = preInfusionTime,
                    amountExtraction      = _amountExtraction,
                    temperature           = _temperature,
                    grindSize             = _currentGrind.value!!,
                    amountOfBeans         = _amountBeans,
                    comment               = _comment,
                    isFavorite            = _currentFavorite.value!!,
                    rating                = ratingManager.currentRating,
                    createdAt             = selectedRecipe.value!!.createdAt
                )
            )
        }
    }

    class EditRecipeViewModelFactory @Inject constructor(
        private val recipeRepo : RecipeRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditRecipeViewModel::class.java)) {
                return EditRecipeViewModel(recipeRepo) as T
            }
            throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
        }
    }
}
