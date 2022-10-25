package com.example.coffeememos

import android.content.Context
import android.graphics.Color
import androidx.appcompat.content.res.AppCompatResources
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet

class ChartManager {

    companion object {
        fun createRadarChart(context: Context, radarChart: RadarChart) {
            radarChart.description.isEnabled = false
            radarChart.isRotationEnabled = false
            radarChart.setBackgroundColor(Color.WHITE)

            radarChart.webLineWidth = 1.5f
            radarChart.webColor = Color.LTGRAY
            radarChart.webLineWidthInner = 1f
            radarChart.webColorInner = Color.LTGRAY
            radarChart.webAlpha = 100
            val entries1: ArrayList<RadarEntry> = ArrayList()

            entries1.add(RadarEntry(3F))
            entries1.add(RadarEntry(3F))
            entries1.add(RadarEntry(3F))
            entries1.add(RadarEntry(5F))
            entries1.add(RadarEntry(3F))

            val set1 = RadarDataSet(entries1, "Taste")
            set1.color = context.getColor(R.color.beige_light)
            set1.fillColor = context.getColor(R.color.beige_light)
            set1.setDrawFilled(true)
            set1.fillAlpha = 180
            set1.lineWidth = 2f
            set1.isDrawHighlightCircleEnabled = true
            set1.setDrawHighlightIndicators(false)


            val sets: ArrayList<IRadarDataSet> = ArrayList()

            sets.add(set1)

            val data = RadarData(sets)
            data.setValueTextSize(12f)
            data.setDrawValues(true)
            data.setValueTextColor(Color.rgb(193,171,151))
            data.setValueFormatter(object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toString()
                }
            })

            radarChart.data = data
            // 中身グラフのアニメーション
            radarChart.animateXY(1400, 1400, Easing.EaseInOutQuad)

            val xAxis = radarChart.xAxis
            xAxis.textSize = 13F
            xAxis.textColor = Color.BLACK
            xAxis.valueFormatter = object : ValueFormatter() {
                private val labels = arrayOf("Sour", "Bitter", "Sweet", "Rich", "Flavor")

                override fun getFormattedValue(value: Float): String {
                    return labels[value.toInt()]
                }
            }

            val yAxis = radarChart.yAxis
            yAxis.axisMaximum = 5f
            yAxis.axisMinimum = 1f
            yAxis.setLabelCount(5, true)
            yAxis.setDrawLabels(false)
            yAxis.textColor = Color.BLACK

            // 凡例の表示無効
            radarChart.legend.isEnabled = false

            radarChart.invalidate()
        }
    }


}