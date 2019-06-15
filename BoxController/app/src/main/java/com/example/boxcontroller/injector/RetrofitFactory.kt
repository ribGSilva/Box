package com.example.boxcontroller.injector

import android.util.Base64
import com.example.boxcontroller.domain.service.BoxWebService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object RetrofitFactory {

    private const val AUTHORIZATION = "Authorization"
    private const val AUTHORIZATION_GRANTS = "user:userPassword"

    @Singleton
    @Provides
    fun boxService() = Retrofit.Builder()
        .baseUrl("http://")
        .client(createClient().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(BoxWebService::class.java)

    private fun createClient(): OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader(AUTHORIZATION, "Basic ${Base64.encodeToString(AUTHORIZATION_GRANTS.toByteArray(),Base64.NO_WRAP)}")
            .build()

        chain.proceed(request)
    }
}