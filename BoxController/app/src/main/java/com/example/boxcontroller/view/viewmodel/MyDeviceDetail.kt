package com.example.boxcontroller.view.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.boxcontroller.BR
import com.example.boxcontroller.view.delegate.bindable
import com.example.boxcontroller.view.delegate.listOfField
import java.util.stream.Collectors
import javax.inject.Inject

class MyDeviceDetail @Inject constructor(
    myDeviceDetailBoxId: Int,
    boxNameDetail: String,
    pillsRemainingDetail: Int,
    nextPillDetail: String,
    lastPillDetail: String,
    buyPillsOnDetail: String,
    alarms: MutableSet<AlarmsDetail>
) : BaseObservable() {

    @get:Bindable
    var myDeviceDetailBoxId by bindable(myDeviceDetailBoxId, BR.myDeviceDetailBoxId)

    @get:Bindable
    var boxNameDetail by bindable(boxNameDetail, BR.boxNameDetail)

    @get:Bindable
    var pillsRemainingDetail by bindable(pillsRemainingDetail, BR.pillsRemainingDetail)

    @get:Bindable
    var nextPillDetail by bindable(nextPillDetail, BR.nextPillDetail)

    @get:Bindable
    var lastPillDetail by bindable(lastPillDetail, BR.lastPillDetail)

    @get:Bindable
    var buyPillsOnDetail by bindable(buyPillsOnDetail, BR.buyPillsOnDetail)

    @get:Bindable
    var alarms by bindable(alarms, BR.alarms)

    fun alarmTimeList() = alarms.listOfField(AlarmsDetail::formatTime)

}