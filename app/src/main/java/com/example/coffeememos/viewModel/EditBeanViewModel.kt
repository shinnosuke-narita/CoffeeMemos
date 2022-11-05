package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.manager.RatingManager
import kotlinx.coroutines.launch

class EditBeanViewModel(private val beanDao: BeanDao) : ViewModel() {
    private val _selectedBean: MutableLiveData<Bean> = MutableLiveData()
    val selectedBean: LiveData<Bean> = _selectedBean

    // Rating 関連
    private lateinit var _ratingManager: RatingManager

    private var _beanCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val beanCurrentRating: LiveData<Int> = _beanCurrentRating

    private val _beanStarList: MutableLiveData<List<RatingManager.Star>> = MutableLiveData(listOf())
    val beanStarList: LiveData<List<RatingManager.Star>> = _beanStarList

    private val _currentFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val currentFavorite: LiveData<Boolean> = _currentFavorite

    fun setFavorite(isFavorite: Boolean) {
        _currentFavorite.value = isFavorite
    }

    // 更新されたデータを保持
    private var _process      : Int = 0
    private var _elevationFrom: Int = 0
    private var _elevationTo  : Int = 0
    private var _country      : String = ""
    private var _farm         : String = ""
    private var _district     : String = ""
    private var _species      : String = ""
    private var _store        : String = ""
    private var _comment      : String = ""

    fun setProcess(process: Int)                { _process = process}
    fun setElevationFrom(elevationFrom: Int)    { _elevationFrom = elevationFrom }
    fun setElevationTo(elevationTo: Int)        { _elevationTo = elevationTo }
    fun setCountry(country: String)             { _country = country }
    fun setFarm(farm: String)                   { _farm = farm }
    fun setDistrict(district: String)           { _district = district }
    fun setSpecies(species: String)             { _species = species }
    fun setStore(store: String)                 { _store = store }
    fun setComment(comment: String)             { _comment = comment }

    fun initialize(id: Long, ratingManager: RatingManager) {
        // RatingManagerを先に初期化する！（アプリ落ちる）
        _ratingManager = ratingManager

        viewModelScope.launch {
            val selectedBean = beanDao.getBeanById(id)

            _selectedBean.value = selectedBean

            updateRatingState(selectedBean.rating)

            _currentFavorite.value = selectedBean.isFavorite
        }
    }

    fun updateRatingState(selectedRate: Int) {
        _ratingManager.changeRatingState(selectedRate)

        _beanCurrentRating.value = _ratingManager.currentRating
        _beanStarList.value      = _ratingManager.starList
    }

    fun updateBean() {
        // TODO ImageId処理
        viewModelScope.launch {
            val id: Long            = _selectedBean.value!!.id
            val process: Int        = if (_process == 0) _selectedBean.value!!.process else _process
            val elevationFrom: Int  = _elevationFrom
            val elevationTo: Int    = _elevationTo
            val country: String     = _country
            val farm: String        = _farm
            val district: String    = _district
            val species: String     = _species
            val store: String       = _store
            val comment: String     = _comment
            val rating: Int         = _ratingManager.currentRating
            val image: Int          = 0
            val isFavorite: Boolean = _currentFavorite.value!!
            val createdAt: Long     = _selectedBean.value!!.createdAt

            beanDao.update(
                Bean(
                    id,
                    country,
                    farm,
                    district,
                    species,
                    elevationFrom,
                    elevationTo,
                    process,
                    store,
                    comment,
                    rating,
                    image,
                    isFavorite,
                    createdAt
                )
            )
        }
    }
}

class EditBeanViewModelFactory(
    private val beanDao  : BeanDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditBeanViewModel::class.java)) {
            return EditBeanViewModel(beanDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
    }
}