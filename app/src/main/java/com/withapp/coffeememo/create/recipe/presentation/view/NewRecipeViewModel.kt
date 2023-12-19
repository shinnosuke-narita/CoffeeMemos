package com.withapp.coffeememo.create.recipe.presentation.view

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.withapp.coffeememo.create.recipe.presentation.controller.CreateRecipeController
import com.withapp.coffeememo.create.recipe.presentation.model.ExtractionTimeInfo
import com.withapp.coffeememo.create.recipe.presentation.model.PreInfusionTimeInfo
import com.withapp.coffeememo.entity.Rating
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.state.InputType
import com.withapp.coffeememo.state.MenuState
import com.withapp.coffeememo.state.ProcessState
import com.withapp.coffeememo.state.SelectBeanBtnAction
import com.withapp.coffeememo.utilities.Util
import com.withapp.coffeememo.validate.RecipeValidationLogic
import com.withapp.coffeememo.validate.ValidationInfo
import com.withapp.coffeememo.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewRecipeViewModel @Inject constructor()
    : BaseViewModel() {
    @Inject
    lateinit var controller: CreateRecipeController

    // お気に入り
    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun setFavoriteFlag(flag: Boolean) {
        _isFavorite.value = flag
    }

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

    // Roast
    private val _currentRoast: MutableLiveData<Int> = MutableLiveData(2)
    val currentRoast: LiveData<Int> = _currentRoast

    fun setRoast(roast: Int) {
        _currentRoast.value = roast
    }

    // Grind Size
    private val _currentGrind: MutableLiveData<Int> = MutableLiveData(3)
    val currentGrind: LiveData<Int> = _currentGrind

    fun setGrind(grind: Int) {
        _currentGrind.value = grind
    }

    // 更新データ保持
    private var _sour                 : Int = 1
    private var _bitter               : Int = 1
    private var _sweet                : Int = 1
    private var _rich                 : Int = 1
    private var _flavor               : Int = 1
    private var _amountBeans          : Int = 0
    private var _temperature          : Int = 0
    private var _preInfusionTime      : Int = 0
    private var _extractionTimeMinutes: Int = 0
    private var _extractionTimeSeconds: Int = 0
    private var _amountExtraction     : Int = 0
    private var _tool                 : String = ""
    private var _comment              : String = ""

    fun setSour(sour: String) {
        _sour = if (sour.isEmpty()) 1 else sour.toInt()
    }
    fun setBitter(bitter: String) {
        _bitter = if (bitter.isEmpty()) 1 else bitter.toInt()
    }
    fun setSweet(sweet: String) {
        _sweet = if (sweet.isEmpty()) 1 else sweet.toInt()
    }
    fun setFlavor(flavor: String) {
        _flavor = if (flavor.isEmpty()) 1 else flavor.toInt()
    }
    fun setRich(rich: String) {
        _rich = if (rich.isEmpty()) 1 else rich.toInt()
    }
    fun setAmountBeans(amountBeans: String) {
        _amountBeans = Util.convertStringIntoIntIfPossible(amountBeans)
    }
    fun setTemperature(temperature: String) {
        _temperature = Util.convertStringIntoIntIfPossible(temperature)
    }
    fun setPreInfusionTime(preInfusionTime: String) {
        _preInfusionTime = Util.convertStringIntoIntIfPossible(preInfusionTime)
    }
    fun setExtractionTimeMinutes(extractionTimeMinutes: String) {
        _extractionTimeMinutes = Util.convertStringIntoIntIfPossible(extractionTimeMinutes)
    }
    fun setExtractionTimeSeconds(extractionTimeSeconds: String) {
        _extractionTimeSeconds = Util.convertStringIntoIntIfPossible(extractionTimeSeconds)
    }
    fun setAmountExtraction(amountExtraction: String) {
        _amountExtraction = Util.convertStringIntoIntIfPossible(amountExtraction)
    }
    fun setTool(tool: String) { _tool = tool }
    fun setComment(comment: String) { _comment = comment }

    // バリデーション
    private val _beanValidation: MutableLiveData<ValidationInfo> = MutableLiveData()
    val beanValidation: LiveData<ValidationInfo> = _beanValidation

    private val _tasteValidation: MutableLiveData<ValidationInfo> = MutableLiveData()
    val tasteValidation: LiveData<ValidationInfo> = _tasteValidation

    private val _temperatureValidation: MutableLiveData<ValidationInfo> = MutableLiveData()
    val temperatureValidation: LiveData<ValidationInfo> = _temperatureValidation

    private val _extractionTimeValidation: MutableLiveData<ValidationInfo> = MutableLiveData()
    val extractionTimeValidation: LiveData<ValidationInfo> = _extractionTimeValidation

    private val _toolValidation: MutableLiveData<ValidationInfo> = MutableLiveData()
    val toolValidation: LiveData<ValidationInfo> = _toolValidation

    // menuの状態管理
    private var _isMenuOpened: MutableLiveData<MenuState?> = MutableLiveData(null)
    val isMenuOpened: LiveData<MenuState?> = _isMenuOpened

    fun setMenuOpenedFlag(menuState: MenuState) {
        _isMenuOpened.value = menuState
    }
    fun resetMenuState() {
        _isMenuOpened.value = null
    }


    // 蒸らし時間の入力タイプ
    private val _preInfusionTimeInputType: MutableLiveData<InputType> = MutableLiveData(InputType.MANUAL)
    val preInfusionTimeInputType: LiveData<InputType> = _preInfusionTimeInputType

    fun setPreInfusionTimeInputType(type: InputType) {
        _preInfusionTimeInputType.value = type
    }

    // 抽出時間の入力タイプ
    private val _extractionTimeInputType: MutableLiveData<InputType> = MutableLiveData(InputType.MANUAL)
    val extractionTimeInputType: LiveData<InputType> = _extractionTimeInputType

    fun setExtractionTimeInputType(type: InputType) {
        _extractionTimeInputType.value = type
    }


    // 保存処理状態
    private val _processState: MutableLiveData<ProcessState> = MutableLiveData(ProcessState.BEFORE_PROCESSING)
    val processState: LiveData<ProcessState> = _processState


    // viewModel 初期化処理
    fun initialize(ratingManager: Rating, preInfusionInputType: InputType, extractionInputType: InputType) {
        if (_ratingManager != null) return
        _ratingManager = ratingManager
        _recipeStarList.value = ratingManager.starList

        setPreInfusionTimeInputType(preInfusionInputType)
        setExtractionTimeInputType(extractionInputType)
    }

    // validationエラーの場合、true
    fun validateRecipeData(context: Context, selectedBean: SearchBeanModel?): Boolean {
        var validationMessage: String

        // taste
        validationMessage = RecipeValidationLogic.validateSelectedBean(context, selectedBean)
        if (validationMessage.isNotEmpty()) {
            setValidationInfoAndResetAfterDelay(_beanValidation, validationMessage)
            return true
        }
        // taste
        val tasteValues: List<Int> =
            listOf(_sour, _bitter, _sweet, _flavor, _rich)
        validationMessage = RecipeValidationLogic.validateTastes(context, tasteValues)
        if (validationMessage.isNotEmpty()) {
            setValidationInfoAndResetAfterDelay(_tasteValidation, validationMessage)
            return true
        }
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
        if (_extractionTimeInputType.value == InputType.MANUAL) {
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
        }

        return false
    }


    // 保存処理
    fun createNewRecipeAndTaste(
        bean: SearchBeanModel,
        preInfusionTime: Long,
        extractionTime: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            // 保存処理開始
            _processState.postValue(ProcessState.PROCESSING)

            // 蒸らし時間
            val preInfusionTimeInfo =
                PreInfusionTimeInfo(
                    inputType = _preInfusionTimeInputType.value!!,
                    manualTime = _preInfusionTime,
                    autoTime = preInfusionTime
                )

            // 抽出時間
            val extractionTimeInfo =
                ExtractionTimeInfo(
                    inputType = _extractionTimeInputType.value!!,
                    minutes = _extractionTimeMinutes,
                    seconds = _extractionTimeSeconds,
                    autoTime = extractionTime
                )

            // 保存処理
            controller.createRecipeAntTaste(
                beanId = bean.id,
                country = bean.country,
                tool = _tool,
                roast = _currentRoast.value!!,
                extractionTimeInfo = extractionTimeInfo,
                preInfusionTimeInfo = preInfusionTimeInfo,
                amountExtraction = _amountExtraction,
                temperature = _temperature,
                grindSize = _currentGrind.value!!,
                amountOfBean = _amountBeans,
                comment = _comment,
                isFavorite = isFavorite.value!!,
                rating = _recipeCurrentRating.value!!,
                sour = _sour,
                bitter = _bitter,
                sweet = _sweet,
                flavor = _flavor,
                rich = _rich
            )

            // 保存処理完了
            _processState.postValue(ProcessState.FINISH_PROCESSING)
        }
    }

    private val _selectBeanBtnAction: MutableLiveData<SelectBeanBtnAction> = MutableLiveData(SelectBeanBtnAction.NOTHING)
    val selectBeanBtnAction: LiveData<SelectBeanBtnAction> = _selectBeanBtnAction

    fun setSelectBeanBtnAction(action: SelectBeanBtnAction) {
        _selectBeanBtnAction.value = action
    }

    // beanの登録チェック
    fun decideSelectBeanBtnAction() {
        viewModelScope.launch(Dispatchers.IO) {
            val beanCount = controller.getBeanCount()

            // コーヒー豆未登録
            if (beanCount == 0) {
                _selectBeanBtnAction
                    .postValue(SelectBeanBtnAction.SHOW_DIALOG)
                return@launch
            }

            // コーヒー豆選択画面表示
            _selectBeanBtnAction
                .postValue(
                    SelectBeanBtnAction.SHOW_SELECT_BEAN_FRAGMENT)
        }
    }
}

