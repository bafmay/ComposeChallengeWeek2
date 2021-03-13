package com.bafmay.challengeweek2.ui.timer

import android.os.CountDownTimer
import com.bafmay.challengeweek2.utils.Constant
import com.bafmay.challengeweek2.utils.toTimeString

class CountDownTimer(
    duration: Long = Constant.DEFAULT_TIMER,
    interval: Long = Constant.DEFAULT_INTERVAL,
    private val onTickTimer: (timeLeftMillis: Long, timeLeftFormat: String) -> Unit,
    private val onTickerFinish: () -> Unit
) : CountDownTimer(duration, interval) {

    override fun onTick(millisUntilFinished: Long) {
        onTickTimer(millisUntilFinished, millisUntilFinished.toTimeString())
    }

    override fun onFinish() {
        onTickerFinish()
    }
}