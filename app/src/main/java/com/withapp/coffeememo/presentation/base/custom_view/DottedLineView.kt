package com.withapp.coffeememo.presentation.base.custom_view;

import android.content.Context;
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet;
import android.view.View
import com.withapp.coffeememo.R

class DottedLineView(context: Context, attrs:AttributeSet) : View(context, attrs) {
    private val p = Paint().apply {
        color = context.getColor(R.color.brown_dark)
        strokeWidth = 7f
        style=Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val w = width

        var xPoint = 0f
        val yPoint = 0f
        val pointList: MutableList<Float> = mutableListOf()
        while (xPoint < w) {
            pointList.add(xPoint)
            pointList.add(yPoint)
            xPoint += 15f
        }
        canvas?.drawPoints( pointList.toFloatArray(), p)

    }

}
