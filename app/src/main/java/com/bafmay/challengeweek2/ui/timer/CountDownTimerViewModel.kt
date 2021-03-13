package com.bafmay.challengeweek2.ui.timer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bafmay.challengeweek2.models.Timer
import com.bafmay.challengeweek2.utils.toTimeString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountDownTimerViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData<Timer>()
    val state: LiveData<Timer> get() = _state

    private var countdownTimer: CountDownTimer? = null

    fun init(time: Long){
        _state.value = Timer(time,time.toTimeString())
    }

    private fun getTimerModel() = _state.value

    fun startTimer(time: Long) {
        countdownTimer?.cancel()
        countdownTimer = CountDownTimer(
            duration = time,
            onTickTimer = { timeLeftTime, timeLeftFormat ->
                _state.value = getTimerModel()?.copy(
                    timeInMillis = timeLeftTime,
                    timeFormatted = timeLeftFormat,
                    state = TimerState.RUNNING
                )
            },
            onTickerFinish = {
                _state.value = getTimerModel()?.copy(
                    timeInMillis = 0,
                    timeFormatted = "00:00",
                    state = TimerState.FINISHED
                )
            }
        )
        countdownTimer?.start()
    }

    fun stopTimer() {
        countdownTimer?.cancel()

        _state.value = getTimerModel()?.copy(state = TimerState.STOPPED)
    }

    fun resetTimer(time: Long) {
        countdownTimer?.cancel()
        _state.value = Timer(timeInMillis = time,timeFormatted = time.toTimeString())
        countdownTimer?.start()
    }

}