package com.example.boxcontroller.domain.controller.wificontroller

import android.content.ContentValues.TAG
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log

@Deprecated("Aós Android 5, essa função não é mais possivel acessar hotspot")
class WifiController(
    private val wifiManager: WifiManager
) {

    @Deprecated("Aós Android 5, essa função não é mais possivel acessar hotspot")
    fun startWifiHotspot(ssid: String, password: String) : Boolean {
        wifiManager.isWifiEnabled = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.w(TAG, "Version not supported")
            return false
        }

        val wifiConfiguration = getWifiConfiguration(ssid, password)
        startAp(wifiConfiguration)

        return true
    }

    @Deprecated("Aós Android 5, essa função não é mais possivel acessar hotspot")
    fun stopWifiHotspot(ssid: String, password: String) {
        wifiManager.isWifiEnabled = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.w(TAG, "Version not supported")
            return
        }

        val wifiConfiguration = getWifiConfiguration(ssid, password)
        stopAp(wifiConfiguration)
    }

    private fun getWifiConfiguration(ssid: String, password: String) : WifiConfiguration {
        val netConfig = WifiConfiguration()

        netConfig.SSID = ssid
        netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
        netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN)
        netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
        netConfig.preSharedKey = password

        return netConfig
    }

    private fun startAp(netConfig: WifiConfiguration) = changeApStatus(netConfig, true)

    private fun stopAp(netConfig: WifiConfiguration) = changeApStatus(netConfig, false)

    private fun changeApStatus(netConfig: WifiConfiguration, status: Boolean) {
        val setWifiApMethod = wifiManager.javaClass
            .getMethod("setWifiApEnabled", WifiConfiguration::class.java, Boolean::class.javaPrimitiveType)

        setWifiApMethod.invoke(wifiManager, netConfig, status) as Boolean


        Log.e(TAG, "\nSSID: ${netConfig.SSID} \nPassword: ${netConfig.preSharedKey} \nSTATUS: $status")
    }
}