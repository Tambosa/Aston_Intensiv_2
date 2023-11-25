package com.example.aston_intensiv_2.custom_views

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import kotlin.random.Random

class SpinningWheel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var pieData: PieData? = null
    private val oval = RectF()
    private val spinningAnimator = ValueAnimator.ofFloat()
    private val animateSpinning = AnimatorSet()
    private var spinningWheelState = SpinningWheelState.IDLE
    private var rotationAngle = 0f
    private var animationResultAngle = 0f

    init {
        setupAnimations()
    }

    fun setData(pieData: PieData) {
        this.pieData = pieData
        setPieSliceDimensions()
        invalidate()
    }

    private fun setPieSliceDimensions() {
        var lastAngle = 0f
        pieData?.pieSlices?.let { data ->
            data.forEach {
                it.value.startAngle = lastAngle
                it.value.sweepAngle = pieData!!.getSweepAngle()
                lastAngle += it.value.sweepAngle
            }
        }
    }

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
        canvas.rotate(rotationAngle, width / 2f, height / 2f)
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) return true
        if (event?.action == MotionEvent.ACTION_UP) {
            when (spinningWheelState) {
                SpinningWheelState.IDLE -> animateSpinning()
                SpinningWheelState.ACTIVE -> {
                    //nothing
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun setupAnimations() {
        spinningAnimator.duration = ANIMATION_DURATION
        spinningAnimator.interpolator = AccelerateDecelerateInterpolator()
        spinningAnimator.addUpdateListener {
            rotationAngle = (it.animatedValue as Float)
            requestLayout()
            invalidate()
        }
        spinningAnimator.doOnStart {
            spinningWheelState = SpinningWheelState.ACTIVE
        }
        spinningAnimator.doOnEnd {
            spinningWheelState = SpinningWheelState.IDLE
            updatePieData()
            rotationAngle = 0f
            invalidate()
            calculateWinner()
        }
        animateSpinning.play(spinningAnimator)
    }

    private fun calculateWinner() {
        pieData?.pieSlices?.forEach {
            if (it.value.startAngle <= 270 && it.value.startAngle + it.value.sweepAngle > 270) {
                Log.d("@@@", it.value.name)
            }
        }
    }

    private fun updatePieData() {
        pieData?.pieSlices?.let { data ->
            data.forEach {
                it.value.startAngle = (it.value.startAngle + animationResultAngle) % 360
            }
        }
    }

    private fun animateSpinning() {
        animationResultAngle = getRandomSpinningResult()
        spinningAnimator.setFloatValues(0f, animationResultAngle)
        animateSpinning.start()
    }

    private fun getRandomSpinningResult() = Random.nextFloat() * 3600f


    enum class SpinningWheelState {
        IDLE,
        ACTIVE
    }

    companion object {
        private const val ANIMATION_DURATION = 5_000L
    }
}