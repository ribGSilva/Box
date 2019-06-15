package com.example.boxcontroller.view.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.boxcontroller.BR
import com.example.boxcontroller.view.delegate.bindable
import javax.inject.Inject

class MyDevice @Inject constructor(
    myDeviceBoxId: Int,
    boxName: String,
    pillsRemaining: Int,
    nextPill: String,
    lastPill: String,
    buyPillsOn: String
) : BaseObservable() {

    @get:Bindable
    var myDeviceBoxId by bindable(myDeviceBoxId, BR.myDeviceBoxId)

    @get:Bindable
    var boxName by bindable(boxName, BR.boxName)

    @get:Bindable
    var pillsRemaining by bindable(pillsRemaining, BR.pillsRemaining)

    @get:Bindable
    var nextPill by bindable(nextPill, BR.nextPill)

    @get:Bindable
    var lastPill by bindable(lastPill, BR.lastPill)

    @get:Bindable
    var buyPillsOn by bindable(buyPillsOn, BR.buyPillsOn)
}