package com.example.coffeememos

import android.text.TextUtils.isEmpty
import android.util.Log
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Util {
    companion object {
        fun convertStringIntoIntIfPossible(value: String): Int =
            if (value.isEmpty()) 0 else value.toInt()


        // 動作確認用
        suspend fun printHello(num: Int) {
            delay(1000L)
            Log.d("sample", "hello$num")
        }

        fun formatEpochTimeMills(epochTimeMills: Long): String {
            val localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(epochTimeMills),
                ZoneId.systemDefault()
            )
            val format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
            return localDateTime.format(format)
        }


    }
}