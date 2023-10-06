package com.hamza.di;

import com.hamza.data.ApiCalls
import com.hamza.utils.Const
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun getApiCalls(): ApiCalls {
        val client = OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(150, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .callTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalRequest = chain.request()
                    val originalUrl = originalRequest.url
                    val url = originalUrl.newBuilder().build()
                    val requestBuilder = originalRequest.newBuilder().url(url)
/*
                         .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Key=${MySharedPreferences.getUserToken()}"
 */
                    val request = requestBuilder.build()
                    val response = chain.proceed(request)
                    response.code//status code
                    return response
                }
            })
            .build()




        return Retrofit.Builder().baseUrl(Const.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(ApiCalls::class.java)
    }
}