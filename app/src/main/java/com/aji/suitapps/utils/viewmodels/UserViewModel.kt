package com.aji.suitapps.utils.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aji.suitapps.utils.repositories.Response
import com.aji.suitapps.utils.repositories.UserRepository
import com.aji.suitapps.utils.responses.DataItem
import com.aji.suitapps.utils.responses.UserResponse
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private var page = 1
    private var userResponse: UserResponse? = null
    private var _response = MutableLiveData<Response<UserResponse?>>()
    var response: LiveData<Response<UserResponse?>> = _response

    fun list(perPage: Int) {
        viewModelScope.launch {
            _response.postValue(Response.Loading)
            val response = userRepository.getList(page, perPage)
            _response.postValue(handle(response))
        }
    }

    private fun handle(response: retrofit2.Response<UserResponse?>): Response<UserResponse?> {
        val data: MutableList<DataItem>?
        if (response.isSuccessful) {
            response.body()?.let {
                page++
                if (userResponse == null) userResponse = it else {
                    data = userResponse?.data
                    val newData = it.data
                    if (newData != null) data?.addAll(newData)
                }
            }
            return Response.Success(userResponse ?: response.body())
            data?.clear()
        }
        else {
            return Response.Error(try {
                response.errorBody()?.string().let {
                    JSONObject(it).get("message")
                }
            } catch (e: JSONException) {
                e.localizedMessage
            } as String )
        }
    }
}