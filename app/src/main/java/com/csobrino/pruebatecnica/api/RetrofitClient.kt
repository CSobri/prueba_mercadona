package com.csobrino.pruebatecnica.api

import android.util.Log
import com.csobrino.pruebatecnica.BuildConfig
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class RetrofitClient {

    enum class ErrorType {
        HTTP_EXCEPTION,  // HTTP
        NETWORK,        // IO
        TIMEOUT,        // Socket
        UNKNOWN         //Anything else
    }

    interface RemoteEmitter {
        fun onResponse(response: Response<Any>)
        fun onError(errorType: ErrorType, msg: String)
    }

    private val client by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient()
                    .newBuilder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
                    .protocols(listOf(Protocol.HTTP_1_1))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) // Gson
            .build().create(Api::class.java)
    }

    suspend inline fun <T> apiCall(
        crossinline responseFunction: suspend () -> T,
        emitter: RemoteEmitter
    ) {
        try {
            val response = withContext(Dispatchers.IO) { responseFunction.invoke() }
            withContext(Dispatchers.Main) {
                emitter.onResponse(response as Response<Any>)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.printStackTrace()
                Log.e("ApiCalls", "Call error: ${e.localizedMessage}", e.cause)
                when (e) {
                    is HttpException -> {
                        val body = e.response()?.errorBody().toString()
                        emitter.onError(ErrorType.HTTP_EXCEPTION, body)
                    }
                    is SocketTimeoutException -> emitter.onError(
                        ErrorType.TIMEOUT,
                        "Timeout Error"
                    )
                    is IOException -> emitter.onError(ErrorType.NETWORK, "Thread Error")
                    else -> emitter.onError(ErrorType.UNKNOWN, "Unknown Error")
                }
            }
        }
    }
}