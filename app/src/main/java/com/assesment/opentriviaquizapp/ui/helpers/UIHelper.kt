package com.assesment.opentriviaquizapp.ui.helpers

fun addLeadingZero(n: Int): String {
    return if (n < 10) "0$n" else n.toString() + ""
}

fun getTimeDurationString(duration: Long): String {
    val seconds = (duration.toInt() / 1000) % 60
    val minutes = (duration.toInt() / (1000 * 60) % 60)
    val hours = (duration.toInt() / (1000 * 60 * 60) % 24)
    var timeStr = ""
    timeStr += "${addLeadingZero(hours)}:"
    timeStr += "${addLeadingZero(minutes)}:"
    timeStr += "${addLeadingZero(seconds)}"
    return timeStr
}
