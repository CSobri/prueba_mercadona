package com.csobrino.pruebatecnica.modules.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csobrino.pruebatecnica.api.RetrofitClient
import com.csobrino.pruebatecnica.data.Planet
import com.csobrino.pruebatecnica.helpers.Constants
import com.csobrino.pruebatecnica.helpers.Constants.SERVER_BADREQUEST_CODE
import com.csobrino.pruebatecnica.helpers.Constants.SERVER_FORBIDDEN_CODE
import com.csobrino.pruebatecnica.helpers.Constants.SERVER_SUCCESS_CODE
import com.csobrino.pruebatecnica.helpers.Constants.SERVER_UNAUTHORIZED_CODE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {
    var planetList = MutableLiveData<ArrayList<Planet>>()

    init {

    }

    fun getPlanetList() {
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient().apply {
                this.apiCall({
                    this.getPlanets()
                }, object : RetrofitClient.RemoteEmitter {
                    override fun onResponse(response: Response<Any>) {
                        when (response.code()) {
                            SERVER_SUCCESS_CODE -> {
                                planetList.value = response.body() as ArrayList<Planet>
                            }
                            SERVER_BADREQUEST_CODE,
                            SERVER_UNAUTHORIZED_CODE,
                            SERVER_FORBIDDEN_CODE -> {
                                //TODO Handle common errors
                            }
                        }
                    }

                    override fun onError(errorType: RetrofitClient.ErrorType, msg: String) {
                        Log.e("onError call: getPlanets", msg)
                    }
                })
            }
        }
    }

}