package com.nishant.mytasks.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TaskTable")
data class TaskCacheEntity(

    @ColumnInfo(name = "userId")
    var userId: String,

    @ColumnInfo(name = "day")
    var day: String,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "task")
    var task: String,

    @ColumnInfo(name = "isArchived")
    var isArchived: Boolean,

    @ColumnInfo(name = "inPinned")
    var isPinned: Boolean,

    @ColumnInfo(name = "taskStatus")
    var taskStatus: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "time")
    var time: String
)