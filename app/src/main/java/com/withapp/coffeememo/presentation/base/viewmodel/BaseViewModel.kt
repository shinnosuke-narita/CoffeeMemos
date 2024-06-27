package com.withapp.coffeememo.presentation.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.withapp.coffeememo.presentation.validation.RecipeValidationLogic
import com.withapp.coffeememo.presentation.validation.ValidationInfo
import com.withapp.coffeememo.presentation.validation.ValidationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    // validation ロジック
    protected fun setValidationInfoAndResetAfterDelay(validationInfo: MutableLiveData<ValidationInfo>, message: String) {
        setValidationMessage(validationInfo, message)
        resetValidationState(validationInfo)
    }
    private fun setValidationMessage(validationInfo: MutableLiveData<ValidationInfo>, message: String) {
        validationInfo.value = ValidationInfo(ValidationState.ERROR, message)
    }
    private fun resetValidationState(validationInfo: MutableLiveData<ValidationInfo>) {
        viewModelScope.launch(Dispatchers.Default) {
            delay(RecipeValidationLogic.VALIDATION_MESSAGE_DISPLAY_TIME)
            validationInfo.postValue(ValidationInfo(ValidationState.NORMAL, ""))
        }
    }
}