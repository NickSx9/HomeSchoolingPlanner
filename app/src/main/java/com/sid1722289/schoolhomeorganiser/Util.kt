package com.sid1722289.schoolhomeorganiser

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.core.text.HtmlCompat
import com.sid1722289.schoolhomeorganiser.database.DayData
import com.sid1722289.schoolhomeorganiser.database.ScheduleData


fun formatSchedule(data: List<ScheduleData>, resources: Resources): String {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        data.forEach {
            append(resources.getString(R.string.start_time))
            append("\t${it.LessonStartTime}<br>")
            append(resources.getString(R.string.end_time))
            append("\t${it.LessonFinishTime}<br>")
            append(resources.getString(R.string.lesson))
            append("\t${it.lessonName}<br>")
            append("======================== <br>")
        }
    }
    Log.d("MADE IT HERE", data.size.toString())
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }
}
