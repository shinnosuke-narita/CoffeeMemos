package com.example.coffeememos.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.example.coffeememos.state.TimerButtonState
import com.example.coffeememos.state.TimerState
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Time

class TimerViewModel : ViewModel() {
    // タイマーの状態
    private var _timerState: MutableLiveData<TimerState> = MutableLiveData(TimerState.CLEAR)
    val timerState: LiveData<TimerState> = _timerState

    fun setTimerState(state: TimerState) {
        _timerState.value = state
    }


    // タイマーの開始時間
    private var _timerStartedAt: Long = 0L
    // タイマーを止めた時間
    private var _timerStoppedAt: Long = 0L
    // タイマーが止まっていた時間
    private var _timerStoppedPeriod: Long = 0L

    // 現在の経過時間（ミリ秒）
    val currentTime: LiveData<Long> =_timerState.switchMap { state ->
        liveData {
            when(state) {
                TimerState.CLEAR -> {
                    _timerStartedAt = 0L
                    _timerStoppedAt = 0L
                    _timerStoppedPeriod = 0L
                    emit(0L)
                }

                TimerState.RUN -> {
                    if (_timerStartedAt == 0L) {
                        _timerStartedAt = System.currentTimeMillis()
                    }
                    // タイマーの時間 = 現在の時間 - (タイマーの開始時間 + タイマーを止めていた時間)
                    _timerStartedAt += _timerStoppedPeriod

                    while (true) {
                        val now = System.currentTimeMillis()
                        emit(now - _timerStartedAt)

                        delay(10L)
                    }
                }

                TimerState.STOP -> {
                    _timerStoppedAt = System.currentTimeMillis()
                    while (_timerState.value!! == TimerState.STOP) {
                        val now = System.currentTimeMillis()
                        _timerStoppedPeriod = now - _timerStoppedAt
                        delay(10L)
                    }
                }
            }
        }
    }


    // タイマー用のフォーマットされた時間
    val mainTimerTime: LiveData<String> = currentTime.map { currentTime ->
        return@map String.format(
            "%02d:%02d",
            getMinutes(currentTime),
            getSeconds(currentTime)
        )
    }


    // メインボタンの状態
    val mainBtnState: LiveData<TimerButtonState> = _timerState.map { state ->
        if (state == TimerState.RUN) TimerButtonState.STOP
        else TimerButtonState.START
    }

    private fun getMinutes(timeMills: Long) = timeMills / 1000 / 60
    private fun getSeconds(timeMills: Long) = timeMills / 1000 % 60
}