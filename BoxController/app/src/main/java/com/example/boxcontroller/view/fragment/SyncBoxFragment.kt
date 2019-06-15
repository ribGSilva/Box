package com.example.boxcontroller.view.fragment

import android.content.ContentValues
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.boxcontroller.R
import com.example.boxcontroller.databinding.FragmentSyncBoxBinding
import com.example.boxcontroller.domain.controller.networkcontroller.NetworkController
import com.example.boxcontroller.domain.valueobject.BoxVO
import com.example.boxcontroller.domain.valueobject.NetworkBoxDataVO
import com.example.boxcontroller.injector.ApplicationDataCenter
import com.example.boxcontroller.injector.DaggerComponentInjector
import com.example.boxcontroller.injector.DataBaseRepository
import com.example.boxcontroller.injector.RetrofitFactory
import com.example.boxcontroller.messages.MessageType
import com.example.boxcontroller.view.delegate.contentView
import com.example.boxcontroller.view.viewmodel.Box
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.fragment_sync_box.*
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.longToast
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncBoxFragment : BasicFragment() {

    private val screenData by contentView<FragmentSyncBoxBinding>(R.layout.fragment_sync_box)
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            if (msg != null) handleBoxMessage(msg)
        }
    }
    private val networkController = NetworkController(handler)
    @Inject lateinit var box : Box
    private lateinit var disposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        DaggerComponentInjector.builder()
            .applicationDataCenter(ApplicationDataCenter)
            .dataBaseRepository(DataBaseRepository(activity!!.application))
            .retrofitFactory(RetrofitFactory)
            .build().inject(this)

        screenData.box = box

        return screenData.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnSaveBoxConfiguration.setOnClickListener { sendNewConfiguration() }

        snackbar = activity!!.coordinator_layout!!.indefiniteSnackbar(R.string.waiting_for_box_connection)

        setupFieldValidation()
    }

    override fun onStart() {
        super.onStart()
        networkController.startServerSocket()
    }

    override fun onResume() {
        super.onResume()

        fragmentController.setToolbarTitle(R.string.box_configuration)
    }

    override fun onStop() {
        networkController.stopServerSocket()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    private fun sendNewConfiguration() {
        val data = NetworkBoxDataVO(
            box.ip,
            5000,
            BoxVO(
                box.name,
                box.ssidWifiConfigured,
                box.passwordWifiConfigured
            )
        )

        networkController.sendMessage(data)

        activity!!.longToast(R.string.box_configurated_test_it)

        box.boxSynchronized = false
    }

    private fun setupFieldValidation() {
        val boxNameObservable = RxTextView
            .textChanges(etNameDevice)
            .skipInitialValue()
            .debounce(800, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable(BackpressureStrategy.LATEST)

        val ssidWifiObservable = RxTextView
            .textChanges(etWifiSsid)
            .skipInitialValue()
            .debounce(800, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable(BackpressureStrategy.LATEST)

        disposable = Flowables.combineLatest(
            boxNameObservable,
            ssidWifiObservable
        ) { newBoxName: CharSequence,
            newSsid: CharSequence ->

            val newBoxNameValid = newBoxName.length > 4
            if (!newBoxNameValid) {
                etNameDevice.error = getString(R.string.invalid_box_name)
            }

            val newSsidValid = newSsid.isNotBlank()
            if (!newSsidValid) {
                etWifiSsid.error = getString(R.string.invalid_wifi_name)
            }

            newBoxNameValid && newSsidValid
        }.subscribe { formValid ->
            btnSaveBoxConfiguration.isEnabled = formValid
        }
    }

    private fun handleBoxMessage(message: Message): Boolean {
        if (MessageType.BOX_SYNCHRONIZATION_MESSAGE != message.what) return false

        Log.i(ContentValues.TAG, "SyncBoxFragment got a box message")

        transferMessageDataToView(message.obj as NetworkBoxDataVO)

        return true
    }

    private fun transferMessageDataToView(networkBoxDataVO: NetworkBoxDataVO) {
        box.ip = networkBoxDataVO.senderIp
        box.port = networkBoxDataVO.senderPort
        box.name = networkBoxDataVO.boxData.name
        box.ssidWifiConfigured = networkBoxDataVO.boxData.ssidWifiConfigured
        box.passwordWifiConfigured = networkBoxDataVO.boxData.passwordWifiConfigured
        box.boxSynchronized = true
        snackbar.dismiss()
    }
}