package com.example.aston_intensiv_2.custom_views

import android.graphics.Paint

data class PieSlice(
    val name: String,
    var startAngle: Float,
    var sweepAngle: Float,
    val paint: Paint
)