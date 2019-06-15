package com.example.boxcontroller.domain.pushnotification

import android.util.Log
import com.example.boxcontroller.domain.database.dao.UserDao
import com.example.boxcontroller.domain.service.BoxWebService
import com.example.boxcontroller.injector.ApplicationDataCenter
import com.example.boxcontroller.injector.DaggerComponentInjector
import com.example.boxcontroller.injector.DataBaseRepository
import com.example.boxcontroller.injector.RetrofitFactory
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FCM"

    @Inject lateinit var userDao: UserDao
    //@Inject lateinit var boxService: BoxWebService

    override fun onNewToken(token: String?) {
        Log.i("NEW_TOKEN", token)

        DaggerComponentInjector.builder()
            .applicationDataCenter(ApplicationDataCenter)
            .dataBaseRepository(DataBaseRepository(application))
            .retrofitFactory(RetrofitFactory)
            .build().inject(this)

        /*val call = boxService.updateToken(1, token ?: "")

        call.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.i("NEW_TOKEN", "Update falhou")
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.i("NEW_TOKEN", "Update feito")
            }

        })*/

        val user = userDao.getUser()
        user.token = token

        userDao.updateUser(user)

        FirebaseMessaging.getInstance().apply {
            subscribeToTopic("MAIN")
            isAutoInitEnabled = true
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val notification = remoteMessage.notification

        Log.i(TAG, "Message ID: ${remoteMessage.messageId}")
        Log.i(TAG, "Data message: ${remoteMessage.data}")
        Log.i(TAG, "Notification message: $notification")

        notification?.let {
            val title = it.title ?: ""
            val body = it.body ?: ""
            val data = remoteMessage.data

            Log.i(TAG, "Notification title: $title")
            Log.i(TAG, "Notification body: $body")
            Log.i(TAG, "Notification data: $data")

            if (data["notifyId"] == null)
                return

            if (data["action"] == "create")
                NotificationCreator.create(this, title, body, data["notifyId"]!!.toInt())

            if (data["action"] == "cancel")
                NotificationCreator.cancel(this, data["notifyId"]!!.toInt())
        }
    }
}
