package com.example.tasktracker.network

class TaskTrackerBaseResponse<T> {
    var message: String? = null
    var data: T? = null
}