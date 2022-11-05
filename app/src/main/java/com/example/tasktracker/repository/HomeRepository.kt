package com.example.tasktracker.repository

import com.example.tasktracker.network.ApiService
import com.example.tasktracker.network.TaskTrackerBaseResponse
import com.example.tasktracker.network.getTaskModel.Data
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun requestToGetTask(): Response<TaskTrackerBaseResponse<List<Data>>> {
        return apiService.getTask()
    }
}