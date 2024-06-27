package com.withapp.coffeememo.presentation.utilities

import android.content.Context
import com.withapp.coffeememo.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtil {
    fun formatEpochTimeMills(epochTimeMills: Long, pattern: String): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(epochTimeMills),
            ZoneId.systemDefault()
        )
        val format = DateTimeFormatter.ofPattern(pattern)
        return localDateTime.format(format)
    }

    fun formatExtractionTime(context: Context, extractionTime: Long): String {
        val minutes = getMinutes(extractionTime)
        val seconds = getSeconds(extractionTime)

        return context.getString(
           R.string.extractionTime_unit,
           minutes,
           seconds
           )
    }

    fun formatPreInfusionTime(context: Context, preInfusionTime: Long): String {
        val seconds = convertSeconds(preInfusionTime)

        return context.getString(
           R.string.preInfusionTime_seconds,
           seconds
           )
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