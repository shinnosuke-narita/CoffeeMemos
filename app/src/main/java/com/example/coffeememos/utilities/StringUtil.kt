package com.example.coffeememos.utilities

class StringUtil {
    companion object {
        fun subStringLastSeparator(text: String, separator: String): String {
            val subStrLastIndex = text.lastIndexOf(separator)
            return text.substring(0, subStrLastIndex)
        }

        fun deleteBlank(value: String) = value.replace(" ", "")
    }
}