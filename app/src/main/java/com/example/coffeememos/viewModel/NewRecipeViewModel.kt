package com.example.coffeememos.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.example.coffeememos.TasteKind
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.dao.TasteDao
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste

class NewRecipeViewModel(
    val recipeDao: RecipeDao,
    val beanDao: BeanDao,
    val tasteDao: TasteDao
) : ViewModel() {

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



    fun changeTasteValue(tasteKind: TasteKind, progress: Int) {
        // 1 ~ 5 の範囲にするため
        val currentValue = progress + 1

        when(tasteKind) {
            TasteKind.SOUR   -> _sour.value = currentValue
            TasteKind.BITTER -> _bitter.value = currentValue
            TasteKind.SWEET  -> _sweet.value = currentValue
            TasteKind.RICH   -> _rich.value = currentValue
            TasteKind.FLAVOR -> _flavor.value = currentValue
        }
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

