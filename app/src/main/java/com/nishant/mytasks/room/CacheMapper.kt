package com.nishant.mytasks.room

import com.nishant.mytasks.model.Task
import com.nishant.mytasks.util.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject
constructor() : EntityMapper<TaskCacheEntity, Task> {
    override fun mapFromEntity(entity: TaskCacheEntity): Task {
        return Task(
            userId = entity.userId,
            day = entity.day,
            category = entity.category,
            title = entity.title,
            task = entity.task,
            isCompleted = entity.isCompleted,
            isArchived = entity.isArchived,
            isPinned = entity.isPinned,
            time = entity.time
        )
    }

    override fun mapToEntity(domainModel: Task): TaskCacheEntity {
        return TaskCacheEntity(
            userId = domainModel.userId,
            day = domainModel.day,
            category = domainModel.category,
            title = domainModel.title,
            isCompleted = domainModel.isCompleted,
            task = domainModel.task,
            isArchived = domainModel.isArchived,
            isPinned = domainModel.isPinned,
            time = domainModel.time
        )
    }

    fun mapFromEntityList(entities: List<TaskCacheEntity>): List<Task> {
        return entities.map { mapFromEntity(it) }
    }
}