package com.nishant.mytasks.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nishant.mytasks.model.TaskCount

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskEntity: TaskCacheEntity): Long

    @Query("select COUNT(category) AS count, category from TaskTable group by category")
    suspend fun getAllCategoriesWithCount(): List<TaskCount>
}