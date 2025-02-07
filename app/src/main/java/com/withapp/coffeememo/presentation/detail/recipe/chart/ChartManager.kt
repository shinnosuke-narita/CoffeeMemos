package com.withapp.coffeememo.presentation.detail.recipe.chart

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.withapp.coffeememo.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet

class ChartManager {
    fun createRadarChart(context: Context, radarChart: RadarChart) {
        // チャートのタイトルの非表示
        radarChart.description.isEnabled = false

        // チャート回転の無効化
        radarChart.isRotationEnabled = false

        // チャート領域のバックグラウンドカラー
        radarChart.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.radar_chart_background
            )
        )

        //チャートの線
        radarChart.webLineWidth = 1.5f
        radarChart.webColor = Color.LTGRAY
        radarChart.webLineWidthInner = 1f
        radarChart.webColorInner = Color.LTGRAY
        radarChart.webAlpha = 100

        // 中身グラフのアニメーション
        radarChart.animateXY(1400, 1400, Easing.EaseInOutQuad)

        // x軸の設定
        val xAxis = radarChart.xAxis
        xAxis.textSize = 13F
        xAxis.textColor = ContextCompat.getColor(
            context, R.color.normal_text_color
        )

        // x軸（チャート外）のラベルの設定
        xAxis.valueFormatter = object : ValueFormatter() {
            private val labels =
                context.resources
                .getStringArray(R.array.taste_chart_labels)

            override fun getFormattedValue(value: Float): String {
                return labels[value.toInt()]
            }
        }

        // y軸（チャート内）の設定
        val yAxis = radarChart.yAxis
        yAxis.axisMaximum = 5f
        yAxis.axisMinimum = 0F
        yAxis.setLabelCount(6, true)
        yAxis.setDrawLabels(true)
        yAxis.textColor = Color.GRAY

        // 凡例の表示無効
        radarChart.legend.isEnabled = false
    }


    fun setData(context   : Context,
                radarChart: RadarChart,
                sour      : Float,
                bitter    : Float,
                sweet     : Float,
                flavor    : Float,
                rich      : Float
    ) {
        // データの生成
        val tasteData: ArrayList<RadarEntry> = ArrayList()

        tasteData.add(RadarEntry(sour))
        tasteData.add(RadarEntry(bitter))
        tasteData.add(RadarEntry(sweet))
        tasteData.add(RadarEntry(flavor))
        tasteData.add(RadarEntry(rich))

        val set1 = RadarDataSet(tasteData, "")
        set1.color = context.getColor(R.color.beige_dark)
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
        // value を描画したい場合、true
        data.setDrawValues(false)
        data.setValueTextColor(Color.rgb(193,171,151))
        data.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toString()
            }
        })

        // データセット
        radarChart.data = data

        // チャートの描画更新処理
        radarChart.invalidate()
    }
}