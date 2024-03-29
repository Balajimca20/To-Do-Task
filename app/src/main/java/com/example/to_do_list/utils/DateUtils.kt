package com.example.to_do_list.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun getUtcTime(date: String?): String {
    try {
        date?.let {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val utcDate = dateFormat.parse(date)
            utcDate?.let {
                dateFormat.timeZone = TimeZone.getDefault()
                return dateFormat.format(utcDate)
            }
        }
    } catch (exp: java.lang.Exception) {
        Log.e("TAG", "getLocalTime: ", exp)
    }
    return ""
}

fun getDateWithMonth(dateInput: String?): String {
    try {
        if (dateInput?.isBlank() == true)
            return ""
        val date: Date? =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateInput?:"")
        date?.let {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            return SimpleDateFormat(
                "dd MMM yyyy",
                Locale.getDefault()
            ).format(calendar.time)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}
