package com.example.boxcontroller.domain.protocol

import com.example.boxcontroller.domain.valueobject.BoxVO

object SynchronizationDataTransformer {

    fun transformDataFromStringToObj(dataString: String) : BoxVO? {
        val listKeys = dataString.split('|')
        if (listKeys.size < 3)
            return null
        return BoxVO(listKeys[0], listKeys[1], listKeys[2])
    }

    fun transformDataFromObjectToString(dataObj: BoxVO) =
        "${dataObj.name}|${dataObj.ssidWifiConfigured}|${dataObj.passwordWifiConfigured}|"
}