package com.example.boxcontroller.view.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.boxcontroller.BR
import com.example.boxcontroller.view.delegate.bindable
import javax.inject.Inject

class ConfigurationBoxDefault @Inject constructor(
    ssid: String,
    password: String
) : BaseObservable() {

    @get:Bindable
    var ssid by bindable(ssid, BR.ssid)

    @get:Bindable
    var password by bindable(password, BR.password)

}