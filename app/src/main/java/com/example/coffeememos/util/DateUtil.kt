package com.example.coffeememos.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateUtil {
    companion object {
        const val pattern: String = "yyyy/MM/dd HH:mm"
        const val simplePattern: String = "yyyy/MM/dd"

        val today: String = formatEpochTimeMills(System.currentTimeMillis(), simplePattern)


        fun formatEpochTimeMills(epochTimeMills: Long, pattern: String): String {
            val localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(epochTimeMills),
                ZoneId.systemDefault()
            )
            val format = DateTimeFormatter.ofPattern(pattern)
            return localDateTime.format(format)
        }
    }
}