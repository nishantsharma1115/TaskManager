package com.nishant.mytasks.model

import java.io.Serializable

data class Task(
    var userId: String,
    var day: String,
    var category: String,
    var title: String,
    var task: String,
    var isCompleted: Int,
    var isArchived: Boolean,
    var isPinned: Boolean,
    var time: String
) : Serializable