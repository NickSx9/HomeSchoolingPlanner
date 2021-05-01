package com.sid1722289.schoolhomeorganiser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LessonData::class], version = 1, exportSchema = false)
abstract class LessonDatabase : RoomDatabase() {

    abstract val lessonDatabaseDao: LessonDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: LessonDatabase? = null

        fun getInstance(context: Context): LessonDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null)
                {
                    instance = Room.databaseBuilder(
                                    context.applicationContext,
                                    LessonDatabase::class.java,
                                    "lesson_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
@Database(entities = [ScheduleData::class], version = 2, exportSchema = false)
abstract class ScheduleDatabase : RoomDatabase() {

    abstract val scheduleDatabaseDao: ScheduleDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: ScheduleDatabase? = null

        fun getInstance(context: Context): ScheduleDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null)
                {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            ScheduleDatabase::class.java,
                            "lesson_history_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
@Database(entities = [DayData::class], version = 3, exportSchema = false)
abstract class DayDatabase : RoomDatabase() {

    abstract val dayDatabaseDao: DayDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: DayDatabase? = null

        fun getInstance(context: Context): DayDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null)
                {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            DayDatabase::class.java,
                            "lesson_history_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
@Database(entities = [GPSLocation::class], version = 4, exportSchema = false)
abstract  class LocationDatabase: RoomDatabase() {
    abstract val locationDatabaseDao: LocationDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: LocationDatabase? = null

        fun getInstance(context: Context): LocationDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null)
                {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            LocationDatabase::class.java,
                            "gps_data_table"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}