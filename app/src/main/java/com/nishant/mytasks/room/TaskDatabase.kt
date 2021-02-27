package com.nishant.mytasks.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskCacheEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_NAME: String = "task_db"
    }
}