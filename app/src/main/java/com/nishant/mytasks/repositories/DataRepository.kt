package com.nishant.mytasks.repositories

import com.nishant.mytasks.model.Task
import com.nishant.mytasks.room.CacheMapper
import com.nishant.mytasks.room.TaskDao


class DataRepository
constructor(
    private val taskDao: TaskDao,
    private val cacheMapper: CacheMapper
) {

    suspend fun insertTask(
        task: Task,
        success: (Long) -> Unit,
        failure: (Long) -> Unit
    ) {
        val result: Long = taskDao.insert(cacheMapper.mapToEntity(task))
        if (result >= 0) {
            success(result)
        } else {
            failure(-1)
        }
    }
}