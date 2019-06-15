package com.example.boxcontroller.domain.controller.networkcontroller

import android.content.ContentValues.TAG
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.boxcontroller.messages.MessageType
import com.example.boxcontroller.domain.controller.networkcontroller.broascast.BroadcastClientReceiver
import com.example.boxcontroller.domain.controller.networkcontroller.socketsender.SocketMessageSender
import com.example.boxcontroller.domain.controller.networkcontroller.vo.BroadcastDataPacketVO
import com.example.boxcontroller.domain.protocol.SynchronizationDataTransformer
import com.example.boxcontroller.domain.valueobject.NetworkBoxDataVO

class NetworkController (mHandler : Handler){

    private var broadcastClientReceiver: BroadcastClientReceiver? = null
    private val superHandler = mHandler
    private var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            if (msg != null) handleBroadcastMessage(msg)
        }
    }

    fun startServerSocket() {
        broadcastClientReceiver =
            BroadcastClientReceiver(handler)
        broadcastClientReceiver?.start()
        Log.i(TAG, "NetworkController started a server socket")
    }

    fun stopServerSocket() {
        broadcastClientReceiver?.stopServer()
        Log.i(TAG, "NetworkController stopped a server socket")
    }

    fun sendMessage(obj: NetworkBoxDataVO) {
        val data = SynchronizationDataTransformer.transformDataFromObjectToString(obj.boxData)
        val dataByteArray = data.toByteArray(Charsets.UTF_8)
        val sender = SocketMessageSender(dataByteArray, obj.senderIp, obj.senderPort)

        Thread(sender).start()
    }

    private fun handleBroadcastMessage(msg: Message): Boolean {
        if (MessageType.SYNCHRONIZATION_MESSAGE == msg.what) {
            if (msg.obj is BroadcastDataPacketVO) {
                handleDataPacket(msg.obj as BroadcastDataPacketVO)
                stopServerSocket()
            }
            return true
        }

        return false
    }

    private fun handleDataPacket(dataPacket: BroadcastDataPacketVO) {
        val boxVO = SynchronizationDataTransformer.transformDataFromStringToObj(dataPacket.dataString) ?: return

        val msg = Message()
        msg.what = MessageType.BOX_SYNCHRONIZATION_MESSAGE
        msg.obj = NetworkBoxDataVO(dataPacket.senderIp, dataPacket.senderPort, boxVO)

        superHandler.sendMessage(msg)

        Log.i(TAG, "NetworkController got a packet from device: ${dataPacket.senderIp.hostAddress}")
    }
}