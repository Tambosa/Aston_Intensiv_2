package com.example.aston_intensiv_2.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class SpinningWheel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var pieData: PieData? = null
    private val oval = RectF()

    fun setData(pieData: PieData) {
        this.pieData = pieData
        setPieSliceDimensions()
        invalidate()
    }

    private fun setPieSliceDimensions() {
        var lastAngle = getRandomStartAngle()
        pieData?.pieSlices?.let { data ->
            data.forEach {
                it.value.startAngle = lastAngle
                it.value.sweepAngle = pieData!!.getSweepAngle()
                lastAngle += it.value.sweepAngle
            }
        }
    }

    private fun getRandomStartAngle() = Random.nextFloat() * 360

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setCircleBounds()
    }

    private fun setCircleBounds(
        top: Float = 0f,
        bottom: Float = layoutParams.height.toFloat(),
        left: Float = (width / 2) - (layoutParams.height / 2).toFloat(),
        right: Float = (width / 2) + (layoutParams.height / 2).toFloat()
    ) {
        oval.top = top
        oval.bottom = bottom
        oval.left = left
        oval.right = right
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pieData?.pieSlices?.let { slices ->
            slices.forEach {
                canvas.drawArc(
                    oval,
                    it.value.startAngle,
                    it.value.sweepAngle,
                    true,
                    it.value.paint
                )
            }
        }
    }
}