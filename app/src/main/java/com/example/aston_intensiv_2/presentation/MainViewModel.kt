package com.example.aston_intensiv_2.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aston_intensiv_2.data.PieDataRepo
import com.example.aston_intensiv_2.domain.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: PieDataRepo) : ViewModel() {
    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> = _state

    fun getState() {
        viewModelScope.launch {
            if (_state.value == null) {
                _state.value = MainState(
                    pieData = repo.getPieData(),
                    wheelScale = 50
                )
            }
        }
    }

    fun onScaling(newScale: Int) {
        viewModelScope.launch {
            _state.value?.let {
                _state.postValue(it.copy(wheelScale = newScale))
            }
        }
    }
}