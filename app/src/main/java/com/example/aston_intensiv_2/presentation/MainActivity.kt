package com.example.aston_intensiv_2.presentation

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.request.CachePolicy
import com.example.aston_intensiv_2.R
import com.example.aston_intensiv_2.custom_views.CustomTextView
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
        initButton()
    }

    private fun initButton() {
        binding.btnReset.setOnClickListener {
            binding.bottomContainer.removeAllViews()
        }
    }

    private fun initPieData() {
        pieData.reset()
        getColorList().forEach {
            pieData.add(it.first, it.second)
        }
        binding.spinningWheel.apply {
            setData(pieData)
            setDoOnAnimationEnd { winner ->
                when (winner) {
                    "RED", "YELLOW", "CYAN", "MAGENTA" -> loadText()
                    "ORANGE", "GREEN", "BLUE" -> loadImage()
                }
            }
        }
    }

    private fun loadText() {
        binding.bottomContainer.addView(CustomTextView(this).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            setText("Some text", R.color.black, 80f)
        })
    }

    private fun loadImage() {
        binding.bottomContainer.addView(ImageView(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            load("https://loremflickr.com/320/240/cat") {
                crossfade(true)
                diskCachePolicy(CachePolicy.DISABLED)
                memoryCachePolicy(CachePolicy.DISABLED)
            }
        })
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