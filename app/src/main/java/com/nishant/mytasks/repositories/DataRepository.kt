package com.nishant.mytasks.repositories

import android.util.Log
import com.nishant.mytasks.model.Task
import com.nishant.mytasks.room.CacheMapper
import com.nishant.mytasks.room.TaskDao
import javax.inject.Inject


class DataRepository
@Inject constructor(
    private val taskDao: TaskDao,
    private val cacheMapper: CacheMapper
) {

    suspend fun insertTask(
        task: Task,
        success: (Long) -> Unit,
        failure: (Long) -> Unit
    ) {
        val result: Long = taskDao.insert(cacheMapper.mapToEntity(task))
        Log.d("Here row", result.toString())
        if (result >= 0) {
            success(result)
        } else {
            failure(-1)
        }
    }

    fun getAllCategoriesWithCount() = taskDao.getAllCategoriesWithCount()

    fun getTodayTasks() = taskDao.getAllTodayTask()

    fun getArchieveTasks() = taskDao.getAllArchieveTasks()
}