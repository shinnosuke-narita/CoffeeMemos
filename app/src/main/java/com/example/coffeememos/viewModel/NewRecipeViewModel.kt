package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.TasteKind
import com.example.coffeememos.util.Util
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.dao.TasteDao
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste
import com.example.coffeememos.state.NewRecipeMenuState
import kotlinx.coroutines.launch

class NewRecipeViewModel(
    private val recipeDao: RecipeDao,
    private val beanDao: BeanDao,
    private val tasteDao: TasteDao
) : ViewModel() {
    // お気に入り
    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    /**
     * taste
     */
    private var _sour: MutableLiveData<Int> = MutableLiveData(3)
    val sour: LiveData<Int> = _sour

    private var _bitter: MutableLiveData<Int> = MutableLiveData(3)
    val bitter: LiveData<Int> = _bitter

    private var _sweet: MutableLiveData<Int> = MutableLiveData(3)
    val sweet: LiveData<Int> = _sweet

    private var _rich: MutableLiveData<Int> = MutableLiveData(3)
    val rich: LiveData<Int> = _rich

    private var _flavor: MutableLiveData<Int> = MutableLiveData(3)
    val flavor: LiveData<Int> = _flavor

    // 現在のrate(初期値3)
    private var currentRating: Int = 3

    /**
     * rateの状態管理フラグ
     */
    private var _starFirst: MutableLiveData<Boolean> = MutableLiveData(true)
    val starFirst: LiveData<Boolean> = _starFirst

    private var _starSecond: MutableLiveData<Boolean> = MutableLiveData(false)
    val starSecond: LiveData<Boolean> = _starSecond

    private var _starThird: MutableLiveData<Boolean> = MutableLiveData(false)
    val starThird: LiveData<Boolean> = _starThird

    private var _starFourth: MutableLiveData<Boolean> = MutableLiveData(false)
    val starFourth: LiveData<Boolean> = _starFourth

    private var _starFifth: MutableLiveData<Boolean> = MutableLiveData(false)
    val starFifth: LiveData<Boolean> = _starFifth

    private val starList = listOf(_starFirst, _starSecond, _starThird, _starFourth, _starFifth)


    /**
     * menuの状態管理フラグ
     */
    private var _isMenuOpened: MutableLiveData<NewRecipeMenuState> = MutableLiveData(NewRecipeMenuState.MENU_CLOSED)
    val isMenuOpened: LiveData<NewRecipeMenuState> = _isMenuOpened

    /**
     * view リセットフラグ
     */
    private var _shouldResetView: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldResetView: LiveData<Boolean> = _shouldResetView

    /**
     * spinner 現在のインデックス
     */
    private var _roastIndex: Int = 0

    private var _grindSizeIndex: Int = 0

    /**
     *  選択されたコーヒー豆
     */
    private var _selectedBeanId: Long? = null


    fun setFavoriteFlag(flag: Boolean) {
        _isFavorite.value = flag
    }

    fun changeTasteValue(tasteKind: TasteKind, progress: Int) {
        // 1 ~ 5 の範囲にするため
        val currentValue = progress + 1

        when(tasteKind) {
            TasteKind.SOUR   -> _sour.value   = currentValue
            TasteKind.BITTER -> _bitter.value = currentValue
            TasteKind.SWEET  -> _sweet.value  = currentValue
            TasteKind.RICH   -> _rich.value   = currentValue
            TasteKind.FLAVOR -> _flavor.value = currentValue
        }
    }

    // rateのstate変更メソッド
    fun changeRatingState(selectedRate: Int) {
        currentRating = selectedRate
        for ((index, star) in starList.withIndex()) {
            star.value = index < selectedRate
        }
    }

    fun createNewRecipeAndTaste(
        tool: String,
        extractionTime: String,
        preInfusionTime: String,
        temperature: String,
        amountOfBeans: String,
        amountExtraction: String,
        comment: String
    ) {
        viewModelScope.launch {
            var newestRecipe: Recipe? = null

            // 最新のレシピを取得
            val job = launch {
                newestRecipe = recipeDao.getNewestRecipe()
            }

            // 現在時刻（エポックタイム）
            val createdAt = System.currentTimeMillis()

            // 型変換
            val iPreInfusionTime  = Util.convertStringIntoIntIfPossible(preInfusionTime)
            val iTemperature      = Util.convertStringIntoIntIfPossible(temperature)
            val iAmountOfBeans    = Util.convertStringIntoIntIfPossible(amountOfBeans)
            val iAmountExtraction = Util.convertStringIntoIntIfPossible(amountExtraction)
            val iExtractionTime   = Util.convertStringIntoIntIfPossible(extractionTime)

            recipeDao.insert(
                Recipe(
                    id                      = 0,
                    beanId                  = _selectedBeanId ?: 1,
                    tool                    = tool,
                    roast                   = _roastIndex,
                    extractionTimeMinutes   = iExtractionTime,
                    extractionTimeSeconds   = iExtractionTime,
                    preInfusionTime         = iPreInfusionTime,
                    amountExtraction        = iAmountExtraction,
                    temperature             = iTemperature,
                    grindSize               = _grindSizeIndex,
                    amountOfBeans           = iAmountOfBeans,
                    comment                 = comment,
                    isFavorite              = _isFavorite.value ?: false,
                    rating                  = currentRating,
                    createdAt               = createdAt
                )
            )

            // 最新レシピの取得を待つ
            job.join()

            // レシピIDを紐づける
            val tasteRecipeId: Long = if (newestRecipe == null) 1 else newestRecipe!!.id + 1

            tasteDao.insert(
                Taste(
                    id       = 0,
                    recipeId = tasteRecipeId,
                    sour     = sour.value    ?: 3,
                    bitter   = bitter.value  ?: 3,
                    sweet    = sweet.value   ?: 3,
                    rich     = rich.value    ?: 3,
                    flavor   = flavor.value  ?: 3
                )
            )
        }
    }



    /**
     * セッター
     */
    fun setMenuOpenedFlag(isMenuOpened: NewRecipeMenuState) {
        _isMenuOpened.value = isMenuOpened
    }

    fun setRoastIndex(index: Int) {
        _roastIndex = index
    }

    fun setGrindSizeIndex(index: Int) {
        _grindSizeIndex = index
    }

    fun setResetFlag(flag: Boolean) {
        _shouldResetView.value = flag
    }

    fun setSourValue(value: Int) {
        _sour.value = value
    }

    fun setBitterValue(value: Int) {
        _bitter.value = value
    }

    fun setSweetValue(value: Int) {
        _sweet.value = value
    }

    fun setRichValue(value: Int) {
        _rich.value = value
    }

    fun setFlavorValue(value: Int) {
        _flavor.value = value
    }

    fun setBeanId(id: Long) {
        _selectedBeanId = id
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

