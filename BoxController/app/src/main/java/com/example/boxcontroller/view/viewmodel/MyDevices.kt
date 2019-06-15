package com.example.boxcontroller.view.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.boxcontroller.BR
import com.example.boxcontroller.view.delegate.bindable
import javax.inject.Inject

class MyDevices @Inject constructor(
    deviceList: MutableList<MyDevices>
) : BaseObservable() {

    @get:Bindable
    var deviceList by bindable(deviceList, BR.deviceList)
}