package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.SimpleBeanInfo
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.utilities.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeBeanViewModel(private val beanDao: BeanDao) : ViewModel() {
    private val maxDisplayItemAmount = 10

    private var allBean: LiveData<List<Bean>> = beanDao.getAllByFlow().asLiveData()

    val simpleBeanInfoList: LiveData<MutableList<SimpleBeanInfo>> = Transformations.map(allBean) { allBean ->
        val result = mutableListOf<SimpleBeanInfo>()

        for (bean in allBean) {
            val simpleBeanInfo = SimpleBeanInfo(
                bean.id,
                bean.country,
                bean.farm,
                bean.district,
                bean.rating.toString(),
                bean.isFavorite,
                DateUtil.formatEpochTimeMills(bean.createdAt, DateUtil.pattern)
            )
            result.add(simpleBeanInfo)
        }

        // 新しい順にソート
        result.sortByDescending { bean: SimpleBeanInfo -> bean.id }

        return@map result
    }

    val todayBeanList = Transformations.map(simpleBeanInfoList) { simpleInfoList ->
        return@map simpleInfoList
            .filter { bean: SimpleBeanInfo -> bean.createdAt.contains(DateUtil.today) }
            .take(maxDisplayItemAmount)
    }

    val newBeanList: LiveData<List<SimpleBeanInfo>> = Transformations.map(simpleBeanInfoList) { simpleInfoList ->
        return@map simpleInfoList.take(maxDisplayItemAmount)
    }

    val favoriteBeanList: LiveData<List<SimpleBeanInfo>> = Transformations.map(simpleBeanInfoList) { simpleInfoList ->
        return@map simpleInfoList
            .filter { bean: SimpleBeanInfo -> bean.isFavorite }
            .take(maxDisplayItemAmount)
    }

    val highRatingBeanList: LiveData<List<SimpleBeanInfo>> = Transformations.map(simpleBeanInfoList) { simpleInfoList ->
        simpleInfoList.sortByDescending { bean: SimpleBeanInfo -> bean.rating }
        return@map simpleInfoList
    }

    val beanCountIsZero: LiveData<Boolean> = Transformations.map(simpleBeanInfoList) { simpleBeanInfoList ->
       if (simpleBeanInfoList.size == 0) return@map true
        return@map false
    }

    fun updateFavoriteIcon(beanInfo: SimpleBeanInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            if (beanInfo.isFavorite) {
                // isFavorite 更新
                beanDao.updateFavoriteByBeanId(beanInfo.id, false)
            } else {
                // isFavorite 更新
                beanDao.updateFavoriteByBeanId(beanInfo.id, true)
            }
        }
    }


    class HomeBeanViewModelFactory(
        private val beanDao: BeanDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeBeanViewModel::class.java)) {
                return HomeBeanViewModel(beanDao) as T
            }
            throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
        }
    }
}




