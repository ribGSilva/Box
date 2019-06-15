package com.example.boxcontroller.domain.controller.networkcontroller.broascast

import android.content.ContentValues
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.boxcontroller.domain.controller.networkcontroller.vo.BroadcastDataPacketVO
import com.example.boxcontroller.messages.MessageType
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress


class BroadcastClientReceiver(
    mHandler: Handler
) : Thread() {
    private val socket: DatagramSocket = DatagramSocket(null)
    private var running: Boolean = false
    private val buf = ByteArray(256)
    private val handler = mHandler

    init {
        socket.reuseAddress = true
        socket.bind(InetSocketAddress(4445))
    }

    override fun run() {
        Log.i(ContentValues.TAG, "BroadcastClientReceiver started to scan")

        running = true

        var packet: DatagramPacket
        var address: InetAddress
        var port: Int
        var stringReceived: String
        while (running) {
            Log.i(ContentValues.TAG, "BroadcastClientReceiver waiting to some packet")

            packet = DatagramPacket(buf, buf.size)
            socket.receive(packet)

            address = packet.address
            port = packet.port
            packet = DatagramPacket(buf, buf.size, address, port)
            stringReceived = String(packet.data, 0, packet.length)
            sendMessage(stringReceived, address, port)
        }
        socket.close()
    }

    fun stopServer() {
        running = false

        Log.i(ContentValues.TAG, "BroadcastClientReceiver stopped to scan")
    }

    private fun sendMessage(message: String, address: InetAddress, port: Int) {

        val msg = Message().apply {
            what = MessageType.SYNCHRONIZATION_MESSAGE
            obj = BroadcastDataPacketVO(address, port, message)
        }

        handler.sendMessage(msg)

        Log.i(ContentValues.TAG, "BroadcastClientReceiver got a packet from device: ${address.hostAddress}")
    }
}