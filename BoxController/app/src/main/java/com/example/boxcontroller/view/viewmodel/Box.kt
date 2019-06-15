package com.example.boxcontroller.view.viewmodel

import androidx.databinding.*
import com.example.boxcontroller.BR
import com.example.boxcontroller.view.delegate.bindable
import java.net.InetAddress
import javax.inject.Inject

class Box @Inject constructor(
    name: String,
    ssidWifiConfigured: String,
    passwordWifiConfigured: String,
    ip: InetAddress,
    port: Int,
    boxSynchronized: Boolean
) : BaseObservable() {

    @get:Bindable
    var name by bindable(name, BR.name)

    @get:Bindable
    var ssidWifiConfigured by bindable(ssidWifiConfigured, BR.ssidWifiConfigured)

    @get:Bindable
    var passwordWifiConfigured by bindable(passwordWifiConfigured, BR.passwordWifiConfigured)

    @get:Bindable
    var ip by bindable(ip, BR.ip)

    @get:Bindable
    var port by bindable(port, BR.port)

    @get:Bindable
    var boxSynchronized by bindable(boxSynchronized, BR.boxSynchronized)
}