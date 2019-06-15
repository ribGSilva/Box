package com.example.boxcontroller.datatransfer

import com.example.boxcontroller.domain.service.data.MyDeviceDetailDataClass
import com.example.boxcontroller.view.viewmodel.Alarm
import com.example.boxcontroller.view.viewmodel.AlarmsDetail
import com.example.boxcontroller.view.viewmodel.MyDeviceDetail

object MyDevicesDetailTransformer {

    fun transform(myDeviceDetailDC: MyDeviceDetailDataClass, myDevice: MyDeviceDetail) {

        val alarms = mutableSetOf<AlarmsDetail>()

        if (!myDeviceDetailDC.alarms.isNullOrEmpty()) {
            myDeviceDetailDC.alarms.forEach { alarm ->
                alarms.add(AlarmsDetail(alarm.id, alarm.time))
            }
        }

        myDevice.myDeviceDetailBoxId = myDeviceDetailDC.id
        myDevice.boxNameDetail = myDeviceDetailDC.boxName
        myDevice.pillsRemainingDetail =  myDeviceDetailDC.pills
        myDevice.nextPillDetail = myDeviceDetailDC.nextPill
        myDevice.lastPillDetail = myDeviceDetailDC.lastPill
        myDevice.buyPillsOnDetail = myDeviceDetailDC.buyPils
        myDevice.alarms = alarms
    }
}