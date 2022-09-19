package com.example.coffeememos

import android.text.TextUtils.isEmpty
import android.util.Log
import kotlinx.coroutines.delay

class Util {
    companion object {
        fun convertStringIntoIntIfPossible(value: String): Int =
            if (value.isEmpty()) 0 else value.toInt()


        // 動作確認用
        suspend fun printHello(num: Int) {
            delay(1000L)
            Log.d("sample", "hello$num")
        }
    }
}