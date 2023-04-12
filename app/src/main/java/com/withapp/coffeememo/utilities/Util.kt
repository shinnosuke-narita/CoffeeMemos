package com.withapp.coffeememo.utilities


class Util {
    companion object {
        // 空文字列を0に変換
        fun convertStringIntoIntIfPossible(value: String): Int =
            if (value.isEmpty()) 0 else value.toInt()

    }
}