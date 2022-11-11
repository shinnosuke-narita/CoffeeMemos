package com.example.coffeememos.viewModel

import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Time

class TimerViewModel : ViewModel() {
    // 現在の経過時間
    private var _currentTime: MutableLiveData<Long> = MutableLiveData(0)
    val currentTime: LiveData<Long> = _currentTime

    private var _timerState: TimerState = TimerState.STOP


    fun start() {
       viewModelScope.launch {
           val startTime: Long = System.currentTimeMillis()
           val currentTime: Long = _currentTime.value!!
           _timerState = TimerState.RUN

           while(_timerState == TimerState.RUN) {
               val now = System.currentTimeMillis()
               var elapsedTime = (now - startTime) / 1000
               elapsedTime += currentTime
               _currentTime.postValue(elapsedTime)

               delay(100L)
           }
       }
    }

    fun stop() {
        _timerState = TimerState.STOP
    }

    fun reset() {
        _currentTime.value = 0L
    }

    enum class TimerState {
        RUN, STOP
    }
}