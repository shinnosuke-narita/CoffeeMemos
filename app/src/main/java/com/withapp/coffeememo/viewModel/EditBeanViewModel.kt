package com.withapp.coffeememo.viewModel

import android.content.Context
import androidx.lifecycle.*
import com.withapp.coffeememo.dao.BeanDao
import com.withapp.coffeememo.dao.RecipeDao
import com.withapp.coffeememo.entity.Bean
import com.withapp.coffeememo.manager.RatingManager
import com.withapp.coffeememo.state.ProcessState
import com.withapp.coffeememo.utilities.Util
import com.withapp.coffeememo.validate.BeanValidationLogic
import com.withapp.coffeememo.validate.ValidationInfo
import kotlinx.coroutines.launch

class EditBeanViewModel(
    private val beanDao: BeanDao,
    private val recipeDao: RecipeDao
) : BaseViewModel() {
    // 選択された豆
    private val _selectedBean: MutableLiveData<Bean> = MutableLiveData()
    val selectedBean: LiveData<Bean> = _selectedBean


    // Rating 関連
    private var _ratingManager: RatingManager? = null
    private val ratingManager: RatingManager
        get() = _ratingManager!!

    private var _beanCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val beanCurrentRating: LiveData<Int> = _beanCurrentRating

    private val _beanStarList: MutableLiveData<List<RatingManager.Star>> = MutableLiveData(listOf())
    val beanStarList: LiveData<List<RatingManager.Star>> = _beanStarList

    fun updateRatingState(selectedRate: Int) {
        ratingManager.changeRatingState(selectedRate)

        _beanCurrentRating.value = ratingManager.currentRating
        _beanStarList.value      = ratingManager.starList
    }


    // Favorite
    private val _currentFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val currentFavorite: LiveData<Boolean> = _currentFavorite

    fun setFavorite(isFavorite: Boolean) {
        _currentFavorite.value = isFavorite
    }


    // 精製法
    private val _process: MutableLiveData<Int> = MutableLiveData(0)
    val process: LiveData<Int> = _process

    fun setProcess(process: Int) {
        _process.value = process
    }

    // バリデーション
    private val _countryValidation: MutableLiveData<ValidationInfo> = MutableLiveData()
    val countryValidation: LiveData<ValidationInfo> = _countryValidation

    fun validateBeanData(context: Context): Boolean  {
        var validationMessage = ""

        // country
        validationMessage = BeanValidationLogic.validateCountry(context, _country)
        if (validationMessage.isNotEmpty()) {
            setValidationInfoAndResetAfterDelay(_countryValidation, validationMessage)
            return true
        }

        return false
    }

    // 更新処理状態
    private val _processState: MutableLiveData<ProcessState> =
        MutableLiveData(ProcessState.BEFORE_PROCESSING)
    val processState: LiveData<ProcessState> = _processState

    // 更新されたデータを保持
    private var _elevationFrom: Int = 0
    private var _elevationTo  : Int = 0
    private var _country      : String = ""
    private var _farm         : String = ""
    private var _district     : String = ""
    private var _species      : String = ""
    private var _store        : String = ""
    private var _comment      : String = ""

    fun setElevationFrom(elevationFrom: String)    { _elevationFrom = Util.convertStringIntoIntIfPossible(elevationFrom) }
    fun setElevationTo(elevationTo: String)        { _elevationTo = Util.convertStringIntoIntIfPossible(elevationTo) }
    fun setCountry(country: String)                { _country = country }
    fun setFarm(farm: String)                      { _farm = farm }
    fun setDistrict(district: String)              { _district = district }
    fun setSpecies(species: String)                { _species = species }
    fun setStore(store: String)                    { _store = store }
    fun setComment(comment: String)                { _comment = comment }


    // 初期化処理
    fun initialize(id: Long, ratingManager: RatingManager) {
        if (_ratingManager != null) return

        // RatingManagerを先に初期化する！（アプリ落ちる）
        _ratingManager = ratingManager

        viewModelScope.launch {
            val selectedBean = beanDao.getBeanById(id)

            updateRatingState(selectedBean.rating)
            _selectedBean.value    = selectedBean
            _currentFavorite.value = selectedBean.isFavorite
            _process.value         = selectedBean.process
        }
    }


    // コーヒー豆更新処理
    fun updateBean() {
        // 更新処理開始
        _processState.value = ProcessState.PROCESSING
        viewModelScope.launch {
            val selectedBean = _selectedBean.value!!
            beanDao.update(
                Bean(
                    id            = selectedBean.id,
                    country       = _country,
                    farm          = _farm,
                    district      = _district,
                    species       = _species,
                    elevationFrom = _elevationFrom,
                    elevationTo   = _elevationTo,
                    process       = _process.value!!,
                    store         = _store,
                    comment       = _comment,
                    rating        = ratingManager.currentRating,
                    isFavorite    = _currentFavorite.value!!,
                    createdAt     = selectedBean.createdAt
                )
            )

            if (_country == _selectedBean.value!!.country)  {
                _processState.postValue(ProcessState.FINISH_PROCESSING)
                return@launch
            }

            // recipe テーブル更新処理
            val recipeIds: List<Long> =
                recipeDao.getRecipeIdsByBeanId(selectedBean.id)
            if (recipeIds.isEmpty()) {
                // 更新処理完了
                _processState.postValue(ProcessState.FINISH_PROCESSING)
                return@launch
            }

            recipeIds.forEach { id ->
                recipeDao.updateCountryById(id, _country)
            }

            // 更新処理完了
            _processState.postValue(ProcessState.FINISH_PROCESSING)
        }
    }
}

class EditBeanViewModelFactory(
    private val beanDao: BeanDao,
    private val recipeDao: RecipeDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditBeanViewModel::class.java)) {
            return EditBeanViewModel(beanDao, recipeDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
    }
}