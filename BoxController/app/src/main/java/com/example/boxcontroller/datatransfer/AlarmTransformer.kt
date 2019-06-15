package com.example.boxcontroller.datatransfer

import com.example.boxcontroller.domain.service.data.AlarmDataClass
import com.example.boxcontroller.view.viewmodel.Alarm

object AlarmTransformer {

    fun transform(alarmsDC: List<AlarmDataClass>) : List<Alarm> {
        val alarms = mutableListOf<Alarm>()

        alarmsDC.forEach {
            alarms.add(Alarm(it.boxName, it.alarmTime))
        }

        return alarms
    }
}