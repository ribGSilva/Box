package com.example.boxcontroller.datatransfer

import com.example.boxcontroller.domain.service.data.MyDeviceDataClass
import com.example.boxcontroller.view.viewmodel.MyDevice

object MyDevicesTransformer {

    fun transform(myDevicesDC: List<MyDeviceDataClass>) : List<MyDevice> {
        val myDevices = mutableListOf<MyDevice>()

        myDevicesDC.forEach {
            myDevices.add(MyDevice(it.id, it.boxName, it.pills, it.nextPill, it.lastPill, it.buyPils))
        }

        return myDevices
    }
}