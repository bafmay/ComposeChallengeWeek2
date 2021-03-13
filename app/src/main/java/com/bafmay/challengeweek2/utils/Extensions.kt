package com.bafmay.challengeweek2.utils

fun Int.toMillis() = this * 60 * 1000.toLong()

fun Long.toTimeString() : String{
    val seconds = (this / 1000) % 60
    val minutes = (this / 1000 / 60) % 60

    val minutesString = "${if (minutes < 10) "0$minutes" else minutes}"
    val secondsString = "${if (seconds < 10) "0$seconds" else seconds}"

    return "$minutesString:$secondsString"
}