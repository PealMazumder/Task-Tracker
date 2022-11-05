package com.example.tasktracker.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasktracker.network.TaskTrackerBaseResponse
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {
    protected val _showLoader = MutableLiveData(false)
    val showLoader: LiveData<Boolean> = _showLoader

    protected val _showMessage = MutableLiveData<String>()

    suspend fun <T> callService(
        showSuccessMessage: Boolean = false,
        api: suspend () -> Response<T>
    ): T? {
        try {
            val response = api.invoke()
            if (response.code() == 401) {
                _showMessage.value = "Authentication Failed!"
                return null
            }
            if (response.isSuccessful && response.body() is TaskTrackerBaseResponse<*>) {
                val baseResponse = response.body() as TaskTrackerBaseResponse<*>
                if (showSuccessMessage) {
                    baseResponse.message?.let {
                        if (it.isNotEmpty())
                            _showMessage.value = it
                    }
                }
            }

            return response.body()
        } catch (e: Exception) {
            _showMessage.value = "Something went wrong! Please try again later"
            e.printStackTrace()
        }
        return null
    }
}