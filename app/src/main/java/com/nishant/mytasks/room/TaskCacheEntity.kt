package com.nishant.mytasks.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TaskTable")
data class TaskCacheEntity(

    @PrimaryKey(autoGenerate = false)
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

    @ColumnInfo(name = "isCompleted")
    var isCompleted: Int,

    @ColumnInfo(name = "time")
    var time: String
)