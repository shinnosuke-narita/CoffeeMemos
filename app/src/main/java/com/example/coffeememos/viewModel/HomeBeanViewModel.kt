package com.example.coffeememos.viewModel

import android.view.View
import androidx.lifecycle.*
import com.example.coffeememos.Constants
import com.example.coffeememos.SimpleBeanInfo
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.utilities.DateUtil
import com.example.coffeememos.utilities.ViewUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeBeanViewModel(private val beanDao: BeanDao) : ViewModel() {
    private val maxDisplayItemAmount = 10

    private var allBean: LiveData<List<Bean>> = beanDao.getAllBean().asLiveData()

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

    fun updateFavoriteIcon(clickedFavoriteIcon: View, beanId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (clickedFavoriteIcon.tag.equals(ViewUtil.IS_FAVORITE_TAG_NAME)) {
                // isFavorite 更新
                beanDao.updateFavoriteByBeanId(beanId, false)

                // リスト更新
               // allBean.postValue(beanDao.getAll())
            } else {
                // isFavorite 更新
                beanDao.updateFavoriteByBeanId(beanId, true)

                // リスト更新
                //allBean.postValue(beanDao.getAll())
            }
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
