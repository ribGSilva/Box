package com.example.boxcontroller.domain.controller.networkcontroller.socketsender

import android.content.ContentValues
import android.util.Log
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class SocketMessageSender(
    private val message : ByteArray,
    private val address: InetAddress,
    private val port: Int
) : Runnable {

    override fun run() {
        Log.i(ContentValues.TAG, "SocketMessageSender is sending a message")

        val socket = DatagramSocket()

        val packet = DatagramPacket(message, message.size, address, port)

        socket.send(packet)

        socket.close()

        Log.i(ContentValues.TAG, "SocketMessageSender sent a message")
    }
}