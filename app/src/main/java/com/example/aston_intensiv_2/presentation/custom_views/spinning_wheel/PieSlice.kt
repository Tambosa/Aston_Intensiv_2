package com.example.aston_intensiv_2.presentation.custom_views.spinning_wheel

import android.graphics.Paint

data class PieSlice(
    val name: String,
    var startAngle: Float,
    var sweepAngle: Float,
    val paint: Paint
)