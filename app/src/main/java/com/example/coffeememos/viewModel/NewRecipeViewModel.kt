package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.dao.TasteDao
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.state.InputType
import com.example.coffeememos.state.MenuState
import com.example.coffeememos.state.ProcessState
import com.example.coffeememos.utilities.DateUtil
import com.example.coffeememos.utilities.StringUtil
import com.example.coffeememos.utilities.Util
import com.example.coffeememos.validate.TasteValidation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewRecipeViewModel(
    private val recipeDao: RecipeDao,
    private val beanDao: BeanDao,
    private val tasteDao: TasteDao
) : ViewModel() {
    private val VALIDATION_MESSAGE_DISPLAY_TIME: Long = 1500L

    // お気に入り
    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun setFavoriteFlag(flag: Boolean) {
        _isFavorite.value = flag
    }


    // Rating 関連
    private var _ratingManager: RatingManager? = null
    val ratingManager: RatingManager
        get() = _ratingManager!!

    private var _recipeCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val recipeCurrentRating: LiveData<Int> = _recipeCurrentRating

    private val _recipeStarList: MutableLiveData<List<RatingManager.Star>> = MutableLiveData(listOf())
    val recipeStarList: LiveData<List<RatingManager.Star>> = _recipeStarList

    fun updateRatingState(selectedRate: Int) {
        ratingManager.changeRatingState(selectedRate)

        _recipeCurrentRating.value = ratingManager.currentRating
        _recipeStarList.value      = ratingManager.starList
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
        if (sour.isEmpty()) return

        _sour = StringUtil.deleteBlank(sour).toInt()
    }
    fun setBitter(bitter: String) {
        if (bitter.isEmpty()) return

        _bitter = StringUtil.deleteBlank(bitter).toInt()
    }
    fun setSweet(sweet: String) {
        if (sweet.isEmpty()) return

        _sweet = StringUtil.deleteBlank(sweet).toInt()
    }
    fun setFlavor(flavor: String) {
        if (flavor.isEmpty()) return

        _flavor = StringUtil.deleteBlank(flavor).toInt()
    }
    fun setRich(rich: String) {
        if (rich.isEmpty()) return

        _rich = StringUtil.deleteBlank(rich).toInt()
    }
    fun setAmountBeans(amountBeans: String) { _amountBeans = Util.convertStringIntoIntIfPossible(amountBeans) }
    fun setTemperature(temperature: String) { _temperature = Util.convertStringIntoIntIfPossible(temperature) }
    fun setPreInfusionTime(preInfusionTime: String) { _preInfusionTime = Util.convertStringIntoIntIfPossible(preInfusionTime) }
    fun setExtractionTimeMinutes(extractionTimeMinutes: String) {_extractionTimeMinutes = Util.convertStringIntoIntIfPossible(extractionTimeMinutes) }
    fun setExtractionTimeSeconds(extractionTimeSeconds: String) {_extractionTimeSeconds = Util.convertStringIntoIntIfPossible(extractionTimeSeconds) }
    fun setAmountExtraction(amountExtraction: String) {_amountExtraction = Util.convertStringIntoIntIfPossible(amountExtraction) }
    fun setTool(tool: String) { _tool = tool }
    fun setComment(comment: String) { _comment = comment }

    // tasteバリデーション
    private val _tasteValidation: MutableLiveData<TasteValidation> = MutableLiveData()
    val tasteValidation: LiveData<TasteValidation> = _tasteValidation

    fun setTasteValidation(validation: TasteValidation) {
        _tasteValidation.value = validation
    }

    // menuの状態管理
    private var _isMenuOpened: MutableLiveData<MenuState> = MutableLiveData(null)
    val isMenuOpened: LiveData<MenuState> = _isMenuOpened

    fun setMenuOpenedFlag(menuState: MenuState) {
        _isMenuOpened.value = menuState
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
    fun initialize(ratingManager: RatingManager, preInfusionInputType: InputType, extractionInputType: InputType) {
        if (_ratingManager != null) return
        _ratingManager = ratingManager

        setPreInfusionTimeInputType(preInfusionInputType)
        setExtractionTimeInputType(extractionInputType)
    }


    // 保存処理
    fun createNewRecipeAndTaste(beanId: Long, preInfusionTime: Long, extractionTime: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            // 保存処理開始
            _processState.postValue(ProcessState.PROCESSING)

            val createdAt = System.currentTimeMillis()

            // 蒸らし時間
            val resultPreInfusionTime: Long
            if (_preInfusionTimeInputType.value!! == InputType.AUTO )
                resultPreInfusionTime = preInfusionTime
            else
                resultPreInfusionTime =  (_preInfusionTime * 1000).toLong()

            // 抽出時間
            val resultExtractionTime : Long
            if (_extractionTimeInputType.value!! == InputType.AUTO)
                resultExtractionTime = extractionTime
            else
                resultExtractionTime = DateUtil.convertSecondsIntoMills(_extractionTimeMinutes, _extractionTimeSeconds)

            // レシピ保存
            recipeDao.insert(
                Recipe(
                    id                    = 0,
                    beanId                = beanId,
                    tool                  = _tool,
                    roast                 = _currentRoast.value ?: 2,
                    extractionTime        = resultExtractionTime,
                    preInfusionTime       = resultPreInfusionTime,
                    amountExtraction      = _amountExtraction,
                    temperature           = _temperature,
                    grindSize             = _currentGrind.value ?: 3,
                    amountOfBeans         = _amountBeans,
                    comment               = _comment,
                    isFavorite            = _isFavorite.value ?: false,
                    rating                = _recipeCurrentRating.value!!,
                    createdAt             = createdAt
                )
            )

            // 上で保存したレシピのIDを取得
            val newestRecipeId = recipeDao.getNewestRecipeId()
            tasteDao.insert(
                Taste(
                    id       = 0,
                    recipeId = newestRecipeId,
                    sour     = _sour,
                    bitter   = _bitter,
                    sweet    = _sweet,
                    rich     = _rich ,
                    flavor   = _flavor
                )
            )

            // 保存処理完了
            _processState.postValue(ProcessState.FINISH_PROCESSING)
        }
    }

    override fun onCleared() {
        super.onCleared()

    }
}

class NewRecipeViewModelFactory(
    private val recipeDao:RecipeDao,
    private val beanDao: BeanDao,
    private val tasteDao: TasteDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewRecipeViewModel::class.java)) {
                return NewRecipeViewModel(recipeDao, beanDao, tasteDao) as T
            }
            throw IllegalArgumentException("CANNOT_GET_NEWRECIPEVIEWMODEL")
        }
    }

