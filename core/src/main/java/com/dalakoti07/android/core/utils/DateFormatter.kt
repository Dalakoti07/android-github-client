package com.dalakoti07.android.core.utils

class DateFormatter {

    private fun getMonth(month: Int): String{
        val months: List<String> = listOf(
            "",
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sept",
            "Oct",
            "Nov",
            "Dec",
        )
        return months[month]
    }

    private fun getDate(date: String): String{
        val parts = date.split('-')
        // date is in format
        // yyyy-mm-dd
        return "${parts[2]} ${getMonth(parts[1].toInt())} ${parts[0]}"
    }

    fun formatDate(dateTime: String): String {
        val parts = dateTime.split("T")
        val date = parts[0]
        val time = parts[1].dropLast(1)
        return "${getDate(date)}, $time"
    }

}