package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.utilities.Util
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.validate.ValidationInfo
import kotlinx.coroutines.launch

class NewBeanViewModel(val beanDao: BeanDao) : ViewModel() {
    // Favorite
    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun setFavoriteFlag(flag: Boolean) {
        _isFavorite.value = flag
    }


    // 精製法
    private val _process: MutableLiveData<Int> = MutableLiveData(0)
    val process: LiveData<Int> = _process

    fun setProcess(process: Int) {
        _process.value = process
    }


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

    // バリデーション
    private val _countryValidation: MutableLiveData<ValidationInfo> = MutableLiveData()
    val countryValidation: LiveData<ValidationInfo> = _countryValidation

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
    fun initialize(ratingManager: RatingManager) {
        if (_ratingManager != null) return

        _ratingManager           = ratingManager
        _beanCurrentRating.value = _ratingManager?.currentRating
        _beanStarList.value      = _ratingManager?.starList
    }

    fun createNewBean() {
        viewModelScope.launch {
            beanDao.insert(
                Bean(
                    id            = 0,
                    country       = _country,
                    farm          = _farm,
                    district      = _district,
                    species       = _species,
                    elevationFrom = _elevationFrom,
                    elevationTo   = _elevationTo,
                    process       = _process.value!!,
                    store         = _store,
                    comment       = _comment,
                    rating        = _beanCurrentRating.value!!,
                    isFavorite    = _isFavorite.value!!,
                    createdAt     = System.currentTimeMillis()
                )
            )
        }

    }
}


class NewBeanViewModelFactory(private val beanDao: BeanDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewBeanViewModel::class.java)) {
            return NewBeanViewModel(beanDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_NEWBEANVIEWMODEL")
    }
}

