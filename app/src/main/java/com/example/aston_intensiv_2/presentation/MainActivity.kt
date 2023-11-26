package com.example.aston_intensiv_2.presentation

import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.request.CachePolicy
import com.example.aston_intensiv_2.R
import com.example.aston_intensiv_2.databinding.ActivityMainBinding
import com.example.aston_intensiv_2.presentation.custom_views.CustomTextView
import com.example.aston_intensiv_2.presentation.custom_views.spinning_wheel.PieData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var currentPieData: PieData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        initViewModel()
        initButton()
        initSeekbar()
    }

    private fun initViewModel() {
        viewModel.state.observe(this) { state ->
            currentPieData = state.pieData
            initPieData(state.pieData)
            binding.spinningWheel.setScale(state.wheelScale / 100f)
        }
        viewModel.getState()
    }

    private fun initPieData(pieData: PieData) {
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

    private fun initButton() {
        binding.btnReset.setOnClickListener {
            binding.bottomContainer.removeAllViews()
        }
    }

    private fun initSeekbar() {
        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onScaling(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
//          nothing
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
//          nothing
            }
        })
    }
}