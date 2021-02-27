package com.nishant.mytasks.model

data class Task(
    var userId: String,
    var day: String,
    var category: String,
    var title: String,
    var task: String,
    var isArchived: Boolean,
    var isPinned: Boolean,
    var time: String
)