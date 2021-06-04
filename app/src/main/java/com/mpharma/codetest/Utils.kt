package com.mpharma.codetest

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Date.format(): String {
    val dateFormat = SimpleDateFormat("EEE, d MMM yyyy")
    return dateFormat.format(this)
}