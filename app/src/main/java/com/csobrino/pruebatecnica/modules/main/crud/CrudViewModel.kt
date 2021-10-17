package com.csobrino.pruebatecnica.modules.main.crud

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csobrino.pruebatecnica.api.RetrofitClient
import com.csobrino.pruebatecnica.data.Product
import com.csobrino.pruebatecnica.helpers.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class CrudViewModel : ViewModel() {
    var planetList = MutableLiveData<ArrayList<Product>>()

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
                            Constants.SERVER_SUCCESS_CODE -> {
                                planetList.value = response.body() as ArrayList<Product>
                            }
                            Constants.SERVER_BADREQUEST_CODE,
                            Constants.SERVER_UNAUTHORIZED_CODE,
                            Constants.SERVER_FORBIDDEN_CODE -> {
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