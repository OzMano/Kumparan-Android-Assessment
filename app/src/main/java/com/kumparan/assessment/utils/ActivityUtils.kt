package com.kumparan.assessment.utils

import android.app.Activity
import android.text.format.DateUtils
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

/**
 * menampilkan [Toast] di semua [Activity]
 */
fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, message, duration).show()
}

fun Activity.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(applicationContext, id)

/**
 * untuk melakukan formating number agar terlihat lebih ringkas
 */
fun prettyCount(number: Int): String? {
    val suffix = charArrayOf(' ', 'K', 'M', 'B', 'T', 'P', 'E')
    val numValue = number.toLong()
    val value = floor(log10(numValue.toDouble())).toInt()
    val base = value / 3
    return if (value >= 3 && base < suffix.size) {
        DecimalFormat("#0.0").format(
            numValue / 10.0.pow((base * 3).toDouble())
        ) + suffix[base]
    } else {
        DecimalFormat("#,##0").format(numValue)
    }
}

fun timeAgo(timestamp: String): String? {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    sdf.timeZone = TimeZone.getTimeZone("GMT")

    try {
        val time: Long = sdf.parse(timestamp).time
        val now = System.currentTimeMillis()
        val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)

        return "Updated $ago"
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return null
}