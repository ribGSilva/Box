package com.example.boxcontroller.view.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.boxcontroller.BR
import com.example.boxcontroller.view.delegate.bindable

class AlarmsDetail (
    idAlarm: Long,
    alarmTimeInt: Int
) : BaseObservable() {

    @get:Bindable
    var idAlarm by bindable(idAlarm, BR.idAlarm)

    @get:Bindable
    var alarmTimeInt by bindable(alarmTimeInt, BR.alarmTimeInt)

    @get:Bindable
    var formatTime by bindable(String.format("%d:%02d", alarmTimeInt/100, alarmTimeInt%100), BR.formatTime)

}