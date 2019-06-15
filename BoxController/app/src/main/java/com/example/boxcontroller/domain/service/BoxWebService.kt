package com.example.boxcontroller.domain.service

import com.example.boxcontroller.domain.service.data.AlarmDataClass
import com.example.boxcontroller.domain.service.data.ConfigurationBoxDefaultDataClass
import com.example.boxcontroller.domain.service.data.MyDeviceDataClass
import com.example.boxcontroller.domain.service.data.MyDeviceDetailDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BoxWebService {

    @GET("/client/{userId}/mydevices")
    fun getMyDevices(@Path("userId") userId: Int): Call<List<MyDeviceDataClass>>

    @GET("/client/{userId}/mydevice/{deviceId}")
    fun getDeviceDetail(@Path("userId") userId: Int, @Path("deviceId") deviceId: Int): Call<MyDeviceDetailDataClass>

    @GET("/client/defaultWifiConfiguration")
    fun getDefaultWifiConfig(): Call<ConfigurationBoxDefaultDataClass>

    @GET("/client/{userId}/nextalarms")
    fun getAlarms(@Path("userId") userId: Int): Call<List<AlarmDataClass>>

    @GET("/client/updateToken/{userId}/{token}")
    fun updateToken(@Path("userId") userId: Int, @Path("token") token: String) : Call<Unit>

}