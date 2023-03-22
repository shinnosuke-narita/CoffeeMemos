package com.withapp.coffeememo.utilities

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

        fun formatExtractionTime(extractionTime: Long): String {
            val minutes = getMinutes(extractionTime)
            val seconds = getSeconds(extractionTime)

            return String.format("%d分%d秒", minutes, seconds)
        }

        fun formatPreInfusionTime(preInfusionTime: Long): String {
            val seconds = convertSeconds(preInfusionTime)

            return String.format("%d秒", seconds)
        }

        fun getMinutes(millsTime: Long): Long = millsTime / 1000 / 60
        fun getSeconds(millsTime: Long): Long = millsTime / 1000 % 60
        fun convertSeconds(millsTime: Long): Long  {
            val minutes = getMinutes(millsTime)
            val seconds = getSeconds(millsTime)
            return (minutes * 60) + seconds
        }

        fun convertSecondsIntoMills(seconds: Int): Long = (seconds * 1000).toLong()

        fun convertSecondsIntoMills(minutes: Int, seconds: Int): Long {
            val totalMills = (minutes * 60 + seconds) * 1000

            return totalMills.toLong()
        }
    }
}