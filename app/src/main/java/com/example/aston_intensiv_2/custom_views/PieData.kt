package com.example.aston_intensiv_2.custom_views

import android.graphics.Paint

class PieData {
    val pieSlices = HashMap<String, PieSlice>()
    private val totalValue = 360f
    private var numberOfSlices = 0

    fun add(name: String, color: Paint) {
        pieSlices[name] = PieSlice(name, 0f, 0f, color)
        numberOfSlices++
    }

    fun getSweepAngle() = totalValue / numberOfSlices

    fun reset() {
        pieSlices.clear()
        numberOfSlices = 0
    }
}