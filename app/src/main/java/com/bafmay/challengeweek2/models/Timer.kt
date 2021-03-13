package com.bafmay.challengeweek2.models

import com.bafmay.challengeweek2.ui.timer.TimerState
import com.bafmay.challengeweek2.utils.Constant

data class Timer(
    val timeInMillis :Long = Constant.DEFAULT_TIMER,
    val timeFormatted : String = Constant.DEFAULT_TIMER_STRING,
    val state : TimerState = TimerState.STOPPED
)