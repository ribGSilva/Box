package com.example.boxcontroller.domain.service.data


data class MyDeviceDetailDataClass (
    val id: Int,
    val boxName: String,
    val pills: Int,
    val nextPill: String,
    val lastPill: String,
    val buyPils: String,
    val alarms: MutableSet<AlarmsDetailDataClass>
)