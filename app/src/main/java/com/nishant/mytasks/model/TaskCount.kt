package com.nishant.mytasks.model

import androidx.room.ColumnInfo

data class TaskCount(
    val count: Int,
    @ColumnInfo(name = "category") val category: String
)