package dev.rezastallone.cartrackchallange.data.source.remote

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dev.rezastallone.cartrackchallange.BuildConfig
import dev.rezastallone.cartrackchallange.constant.BASE_URL
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RemoteService {
    fun getRemoteService(gson: Gson): RemoteClient {

        var connectionTimeOut: Long = 30
        var writeTimeOut: Long = 30
        var readTimeOut: Long = 30

        if (BuildConfig.DEBUG) {
            connectionTimeOut = 5
            writeTimeOut = 10
            readTimeOut = 10
        }

        val maxRequestPerHost = 500

        val endpoint = BASE_URL
        val dispatcher = Dispatcher()
        dispatcher.maxRequestsPerHost = maxRequestPerHost

        // http client
        val client = OkHttpClient.Builder()
            .connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
            .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .retryOnConnectionFailure(true)
            .build()

        // retrofit client
        val retrofit = Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()

        return retrofit.create(RemoteClient::class.java)
    }
}