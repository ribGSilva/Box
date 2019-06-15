package com.example.boxcontroller.domain.valueobject

import java.net.InetAddress

class NetworkBoxDataVO (
    val senderIp: InetAddress,
    val senderPort: Int,
    val boxData: BoxVO
)
