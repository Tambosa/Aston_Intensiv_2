package com.example.aston_intensiv_2.presentation

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.aston_intensiv_2.custom_views.PieData
import com.example.aston_intensiv_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pieData: PieData = PieData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPieData()
    }

    private fun initPieData() {
        pieData.reset()
        getColorList().forEach {
            pieData.add(it.first, it.second)
        }
        binding.spinningWheel.apply {
            setData(pieData)
            setDoOnAnimationEnd { winner ->
                Log.d("@@@", "initPieData: $winner")
            }
        }
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