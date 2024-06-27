package com.withapp.coffeememo.presentation.base.custom_view

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.withapp.coffeememo.R


class CircleProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var _objectAnimator: ObjectAnimator? = null

    /**
     * ProgressBar's line thickness
     */
    private var _strokeWidth: Float = 4f
    private var progress   : Float = 30f
    private var min        : Int = 0
    private var max        : Int = 100

    /**
     * Start the progress at 12 o'clock
     */
    private val startAngle     : Float = -90F
    private var color          : Int = Color.GRAY
    private var rectF          : RectF? = null
    private var backgroundPaint: Paint? = null
    private var foregroundPaint: Paint? = null

    fun getStrokeWidth(): Float {
        return _strokeWidth
    }

    fun setStrokeWidth(strokeWidth: Float) {
        _strokeWidth = strokeWidth
        backgroundPaint?.strokeWidth = strokeWidth
        foregroundPaint?.strokeWidth = strokeWidth
        invalidate()
        requestLayout() //Because it should recalculate its bounds
    }

    fun getProgress(): Float {
        return progress
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    fun getMin(): Int {
        return min
    }

    fun setMin(min: Int) {
        this.min = min
        invalidate()
    }

    fun getMax(): Int {
        return max
    }

    fun setMax(max: Int) {
        this.max = max
        invalidate()
    }

    fun getColor(): Int {
        return color
    }

    fun setColor(color: Int) {
        this.color = color
        backgroundPaint!!.color = adjustAlpha(color, 0.3f)
        foregroundPaint!!.color = color
        invalidate()
        requestLayout()
    }


    init {
        rectF = RectF()
        val typedArray: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleProgressBar,
            0, 0
        )
        //Reading values from the XML layout
        try {
            _strokeWidth = typedArray.getDimension(R.styleable.CircleProgressBar_progressBarThickness, _strokeWidth)
            progress    = typedArray.getFloat(R.styleable.CircleProgressBar_progress, progress)
            color       = typedArray.getInt(R.styleable.CircleProgressBar_progressbarColor, color)
            min         = typedArray.getInt(R.styleable.CircleProgressBar_min, min)
            max         = typedArray.getInt(R.styleable.CircleProgressBar_max, max)
        } finally {
            typedArray.recycle()
        }

        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = adjustAlpha(color, 0.1f)
            style = Paint.Style.STROKE
            strokeWidth = _strokeWidth
        }
        foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = resources.getColor(R.color.orange, null)
            style = Paint.Style.STROKE
            strokeWidth = _strokeWidth + 0.5F
            maskFilter = BlurMaskFilter(8F, BlurMaskFilter.Blur.NORMAL)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawOval(rectF!!, backgroundPaint!!)
        val angle = 360 * progress / max
        canvas.drawArc(rectF!!, startAngle, angle, false, foregroundPaint!!)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = Math.min(width, height)
        setMeasuredDimension(min, min)
        rectF!!.set(
            0 + _strokeWidth / 2,
            0 + _strokeWidth / 2,
            min - _strokeWidth / 2,
            min - _strokeWidth / 2
        )
    }

    /**
     * Lighten the given color by the factor
     *
     * @param color  The color to lighten
     * @param factor 0 to 4
     * @return A brighter color
     */
    fun lightenColor(color: Int, factor: Float): Int {
        val r: Float = Color.red(color) * factor
        val g: Float = Color.green(color) * factor
        val b: Float = Color.blue(color) * factor
        val ir = Math.min(255, r.toInt())
        val ig = Math.min(255, g.toInt())
        val ib = Math.min(255, b.toInt())
        val ia: Int = Color.alpha(color)
        return Color.argb(ia, ir, ig, ib)
    }

    /**
     * Transparent the given color by the factor
     * The more the factor closer to zero the more the color gets transparent
     *
     * @param color  The color to transparent
     * @param factor 1.0f to 0.0f
     * @return int - A transplanted color
     */
    fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor).toInt()
        val red: Int = Color.red(color)
        val green: Int = Color.green(color)
        val blue: Int = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    /**
     * Set the progress with an animation.
     * Note that the [android.animation.ObjectAnimator] Class automatically set the progress
     * so don't call the [CircleProgressBar.setProgress] directly within this method.
     *
     * @param progress The progress it should animate to it.
     */
    fun setProgressWithAnimation(progress: Float) {
        if (_objectAnimator == null) {
            _objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress).apply {
                duration = 2000
                repeatCount = -1
                interpolator = DecelerateInterpolator()
            }
        }

        _objectAnimator!!.start()
    }

    fun stopAnimation() {
        if (_objectAnimator == null) return

        _objectAnimator!!.cancel()
        progress = 0F
        invalidate()
    }
}