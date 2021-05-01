package com.sid1722289.schoolhomeorganiser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LessonDatabaseDao {

    @Insert
    suspend fun insert(lesson: LessonData)

    @Update
    suspend fun update(lesson: LessonData)

    @Query("INSERT INTO lesson_data_table(lesson_name, lesson_start, lesson_end, lesson_notes) VALUES(:lessonName, '0:00', '0:00', 'NONE') ")
    suspend fun insertInToDatabase(lessonName: String)

    @Query("UPDATE lesson_data_table SET lesson_notes = :note WHERE lesson_name = :lesson")
    suspend fun updateLessonNotes(lesson: String, note: String)

    @Query("DELETE FROM lesson_data_table")
    suspend fun clear()

    @Query("SELECT * from lesson_data_table WHERE lesson_name = :lesson")
    suspend fun get(lesson: String): LessonData?

    @Query("DELETE FROM lesson_data_table")
    suspend fun deleteLessonTableContent()

    @Query("SELECT lesson_notes from lesson_data_table WHERE lesson_name = :key")
    suspend fun getNotesForLesson(key: String): String

    @Query("SELECT * FROM lesson_data_table ORDER BY lessonId DESC LIMIT 1")
    suspend fun getLastLessonDetails() : LessonData

    @Query("SELECT * FROM lesson_data_table")
    suspend fun getAllLessonDetail() : List<LessonData>
}

@Dao
interface ScheduleDatabaseDao {

    @Insert
    suspend fun insert(schedule: ScheduleData)

    @Update
    suspend fun update(schedule: ScheduleData)

    @Query("DELETE FROM schedule_data_table")
    suspend fun clear()

    @Query("SELECT * FROM schedule_data_table WHERE day = :day")
    suspend fun getDataList(day: String) : List<ScheduleData>
}

@Dao
interface DayDatabaseDao {

    @Insert
    suspend fun insert(dayData: DayData)

    @Update
    suspend fun update(dayData: DayData)

    @Query("DELETE FROM day_data_table")
    suspend fun clear()

    @Query("UPDATE day_data_table SET day_start = :start, day_end = :end WHERE day = :day")
    suspend fun updateDay(day: String, start: String, end: String)

    @Query("SELECT * from day_data_table WHERE day = :day")
    suspend fun get(day: String): DayData?

    @Query("SELECT * FROM day_data_table ORDER BY dayId DESC LIMIT 1")
    suspend fun getLastDayDetails() : DayData

    @Query("SELECT * FROM day_data_table")
    suspend fun getAllRecords() : List<DayData>
}