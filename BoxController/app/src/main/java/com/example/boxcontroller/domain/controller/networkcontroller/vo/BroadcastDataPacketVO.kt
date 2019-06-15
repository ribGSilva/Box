package com.example.boxcontroller.domain.controller.networkcontroller.vo

import java.net.InetAddress

data class BroadcastDataPacketVO (
    val senderIp: InetAddress,
    val senderPort: Int,
    val dataString: String
)