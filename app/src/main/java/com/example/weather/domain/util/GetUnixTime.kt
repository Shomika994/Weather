package com.example.weather.domain.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
fun getUnixTime(time: Long): String{
    val date = Date(time * 1000L)
    val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()
    return simpleDateFormat.format(date)
}