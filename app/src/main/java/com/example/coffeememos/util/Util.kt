package com.example.coffeememos.util

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coffeememos.Constants
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Util {
    companion object {
        fun convertStringIntoIntIfPossible(value: String): Int =
            if (value.isEmpty()) 0 else value.toInt()

        fun isDefaultProcess(process: String): Boolean = Constants.processList.contains(process)

        // 動作確認用
        suspend fun printHello(num: Int) {
            delay(1000L)
            Log.d("sample", "hello$num")
        }
    }
}