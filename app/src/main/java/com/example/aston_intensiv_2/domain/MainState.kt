package com.example.aston_intensiv_2.domain

import com.example.aston_intensiv_2.presentation.custom_views.spinning_wheel.PieData

data class MainState(
    val pieData: PieData,
    val wheelScale: Int,
)
