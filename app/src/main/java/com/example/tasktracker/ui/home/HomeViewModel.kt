package com.example.tasktracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tasktracker.base.BaseViewModel
import com.example.tasktracker.network.TaskTrackerBaseResponse
import com.example.tasktracker.network.getTaskModel.Data
import com.example.tasktracker.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel() {
    private val _apiResponse = MutableLiveData<TaskTrackerBaseResponse<List<Data>>>()
    val apiResponse: LiveData<TaskTrackerBaseResponse<List<Data>>> = _apiResponse

    fun getTaskFromServer() {
        _showLoader.value = true
        viewModelScope.launch {
            val response = callService { repository.requestToGetTask() }
            _showLoader.value = false
            response?.let {
                _apiResponse.value = it
            }
        }
    }
}