package com.hamza.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hamza.data.ApiCalls
import com.hamza.model.Data
import com.hamza.model.NotificationModel
import com.hamza.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val api: ApiCalls) : ViewModel() {




    private val _response = MutableStateFlow<NetworkState?>(null)
    val response get() = _response

    fun sendNotification(model: NotificationModel) {

        viewModelScope.launch {
            _response.emit(NetworkState.LOADING)
            try {
                val result = api.sendNotification(model)
                if (result.isSuccessful)
                    _response.emit(NetworkState.getLoaded(result.body()))
                else {
                    _response.emit(NetworkState.getErrorMessage(result.errorBody().toString()))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _response.emit(NetworkState.Companion.getErrorMessage(e.message.toString()))
            }
        }
    }

}