package com.example.boxcontroller.view.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.boxcontroller.BR
import com.example.boxcontroller.view.delegate.bindable
import javax.inject.Inject

class Alarm @Inject constructor(
    alarmBoxName: String,
    alarmTime: String
) : BaseObservable() {

    @get:Bindable
    var alarmBoxName by bindable(alarmBoxName, BR.alarmBoxName)

    @get:Bindable
    var alarmTime by bindable(alarmTime, BR.alarmTime)

}