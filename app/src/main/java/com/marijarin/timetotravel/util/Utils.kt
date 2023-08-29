package com.marijarin.timetotravel.util

import android.icu.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun toDate(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return formatter.format(parser.parse(date) ?: "")
    }
}