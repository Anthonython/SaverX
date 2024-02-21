package com.example.saverx.app.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class CustomProgressLoadView : View{
    private var progress = 0
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        progressPaint.color = Color.CYAN
        progressPaint.style = Paint.Style.FILL

        backgroundPaint.color = Color.TRANSPARENT
        backgroundPaint.style = Paint.Style.FILL
    }

    fun setProgress(value: Int) {
        val animator = ValueAnimator.ofInt(progress, value)
        animator.duration = 300 // Adjust the duration of the animation as needed
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            progress = animation.animatedValue as Int
            invalidate()
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        val progressAngle = (progress / 100f) * 360f

        // Draw background
        canvas.drawCircle(width / 2f, height / 2f, width / 2f, backgroundPaint)

        // Draw progress
        canvas.drawArc(0f, 0f, width, height, -90f, progressAngle, true, progressPaint)
    }
}