package com.example.aston_intensiv_2.data

import android.graphics.Color
import android.graphics.Paint
import com.example.aston_intensiv_2.presentation.custom_views.spinning_wheel.PieData

object PieDataRepo {
    fun getPieData(): PieData {
        val pieData = PieData()
        getColorList().forEach {
            pieData.add(it.first, it.second)
        }
        return pieData
    }

    private fun getColorList() = listOf(
        Pair("RED", getPaint("#FF0000")),
        Pair("ORANGE", getPaint("#FFA500")),
        Pair("YELLOW", getPaint("#FFFF00")),
        Pair("GREEN", getPaint("#008000")),
        Pair("CYAN", getPaint("#00FFFF")),
        Pair("BLUE", getPaint("#0000FF")),
        Pair("MAGENTA", getPaint("#FF00FF"))
    )

    private fun getPaint(color: String): Paint {
        val newPaint = Paint()
        newPaint.color = Color.parseColor(color)
        newPaint.isAntiAlias = true
        newPaint.style = Paint.Style.FILL
        return newPaint
    }
}