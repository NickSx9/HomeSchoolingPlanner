package com.sid1722289.schoolhomeorganiser.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL

@Entity(tableName = "lesson_data_table")
data class LessonData(
    @PrimaryKey(autoGenerate = true)
    var lessonId: Long = 0L,

    @ColumnInfo(name = "lesson_name")
    var lessonName: String = "",

    @ColumnInfo(name = "lesson_start")
    var LessonStartTime: String = "",

    @ColumnInfo(name = "lesson_end")
    var LessonFinishTime: String = "",

    @ColumnInfo(name = "lesson_notes")
    var LessonNotes: String = ""
)

@Entity(tableName = "schedule_data_table")
data class ScheduleData(
        @PrimaryKey(autoGenerate = true)
        var scheduleId: Long = 0L,

        @ColumnInfo(name = "lesson")
        var lessonName: String = "",

        @ColumnInfo(name = "day")
        var Day: String = "",

        @ColumnInfo(name = "lesson_start")
        var LessonStartTime: String = "",

        @ColumnInfo(name = "lesson_end")
        var LessonFinishTime: String = ""
)

@Entity(tableName = "day_data_table")
data class DayData(
        @PrimaryKey(autoGenerate = true)
        var dayId: Long = 0L,

        @ColumnInfo(name = "day")
        var Day: String = "",

        @ColumnInfo(name = "day_start")
        var DayStartTime: String = "",

        @ColumnInfo(name = "day_end")
        var DayFinishTime: String = ""
)
@Entity(tableName = "gps_data_table")
data class GPSLocation(
        @PrimaryKey(autoGenerate = true)
        var gpsId: Long = 0L,
        @ColumnInfo(name="longitude")
        var Longitude: String = "",
        @ColumnInfo(name="latitude")
        var Latitude: String = ""
)


