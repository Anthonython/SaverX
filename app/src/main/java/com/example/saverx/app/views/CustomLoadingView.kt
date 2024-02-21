package com.example.saverx.app.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import com.example.saverx.R
import kotlin.math.min

class CustomLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val circlePaint1: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var circleRadius = 0f
    private var centerX = 0f
    private var centerY = 0f
    private var currentAngle = 0f
    private var currentAngle1 = 0f
    private val animator: ValueAnimator = ValueAnimator.ofFloat(0f, 360f)

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLoadingView)
        val customColor = typedArray.getColor(
            R.styleable.CustomLoadingView_customColor,
            ContextCompat.getColor(context, R.color.purple_500)
        )
        typedArray.recycle()


        circlePaint.apply {
            circlePaint.color = customColor
            style = Paint.Style.STROKE
            strokeWidth = 15f
        }

        circlePaint1.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }

        animator.duration = 2000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener { animation ->
            currentAngle =  (animation.animatedValue as Float)
            currentAngle1 = -(animation.animatedValue as Float)
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)

        centerX = measuredWidth / 2f
        centerY = measuredHeight / 2f
        circleRadius = size / 3f - circlePaint.strokeWidth / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val startAngle = currentAngle + 90
        val startAngle1 = currentAngle1 + 90
        val sweepAngle = 45f

        canvas.drawArc(
            centerX - circleRadius,
            centerY - circleRadius,
            centerX + circleRadius,
            centerY + circleRadius,
            startAngle,
            sweepAngle,
            false,
            circlePaint
        )

        canvas.drawArc(
            centerX+25f - circleRadius,
            centerY+25f - circleRadius,
            centerX-25f + circleRadius,
            centerY-25f + circleRadius,
            startAngle1 + 45,
            sweepAngle,
            false,
            circlePaint1
        )


        canvas.drawArc(
            centerX - circleRadius,
            centerY - circleRadius,
            centerX + circleRadius,
            centerY + circleRadius,
            startAngle + 90,
            sweepAngle,
            false,
            circlePaint
        )

        canvas.drawArc(
            centerX+25f - circleRadius,
            centerY+25f - circleRadius,
            centerX-25f + circleRadius,
            centerY-25f + circleRadius,
            startAngle1 + 135,
            sweepAngle,
            false,
            circlePaint1
        )



        canvas.drawArc(
            centerX - circleRadius,
            centerY - circleRadius,
            centerX + circleRadius,
            centerY + circleRadius,
            startAngle + 180,
            sweepAngle,
            false,
            circlePaint
        )

        canvas.drawArc(
            centerX+25f - circleRadius,
            centerY+25f - circleRadius,
            centerX-25f + circleRadius,
            centerY-25f + circleRadius,
            startAngle1 + 225,
            sweepAngle,
            false,
            circlePaint1
        )



        canvas.drawArc(
            centerX - circleRadius,
            centerY - circleRadius,
            centerX + circleRadius,
            centerY + circleRadius,
            startAngle + 270,
            sweepAngle,
            false,
            circlePaint
        )

        canvas.drawArc(
            centerX+25f - circleRadius,
            centerY+25f - circleRadius,
            centerX-25f + circleRadius,
            centerY-25f + circleRadius,
            startAngle1 + 315,
            sweepAngle,
            false,
            circlePaint1
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.cancel()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    fun startAnim(){
       animator.start()
    }

    fun stopAnim(){
        animator.cancel()
    }

    fun isAnimating() : Boolean {
        return animator.isRunning
    }

    fun setLoadingColor(color: Int){
        circlePaint.color = color
    //    invalidate()
    }
}
