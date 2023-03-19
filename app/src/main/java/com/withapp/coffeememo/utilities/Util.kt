package com.withapp.coffeememo.utilities

import android.util.Log
import com.withapp.coffeememo.Constants
import kotlinx.coroutines.delay

class Util {
    companion object {
        // 空文字列を0に変換
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