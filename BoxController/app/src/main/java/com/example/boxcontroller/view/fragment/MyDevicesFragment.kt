package com.example.boxcontroller.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boxcontroller.R
import com.example.boxcontroller.datatransfer.MyDevicesTransformer
import com.example.boxcontroller.domain.service.BoxWebService
import com.example.boxcontroller.domain.service.data.MyDeviceDataClass
import com.example.boxcontroller.injector.ApplicationDataCenter
import com.example.boxcontroller.injector.DaggerComponentInjector
import com.example.boxcontroller.injector.DataBaseRepository
import com.example.boxcontroller.injector.RetrofitFactory
import com.example.boxcontroller.view.adapters.MyDeviceAdapter
import com.example.boxcontroller.view.viewmodel.MyDevice
import com.example.boxcontroller.view.viewmodel.MyDeviceDetail
import com.example.boxcontroller.view.viewmodel.User
import kotlinx.android.synthetic.main.fragment_my_devices.*
import kotlinx.android.synthetic.main.main_layout.*
import org.jetbrains.anko.design.longSnackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MyDevicesFragment : BasicFragment() {

    @Inject lateinit var boxService: BoxWebService
    @Inject lateinit var user: User
    @Inject lateinit var myDevices: Set<MyDevice>
    @Inject lateinit var myDeviceDetail: MyDeviceDetail
    private val myDevicesAdapter by lazy {
        MyDeviceAdapter(context!!) {
            (it.tag as MyDevice).let { device ->
                myDeviceDetail.myDeviceDetailBoxId = device.myDeviceBoxId
                myDeviceDetail.boxNameDetail = device.boxName
                myDeviceDetail.buyPillsOnDetail = device.buyPillsOn
                myDeviceDetail.lastPillDetail = device.lastPill
                myDeviceDetail.nextPillDetail = device.nextPill
                myDeviceDetail.pillsRemainingDetail = device.pillsRemaining

                fragmentController.changeFragment(FragmentTypes.MY_DEVICE_DETAIL_FRAGMENT.name, true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DaggerComponentInjector.builder()
            .applicationDataCenter(ApplicationDataCenter)
            .dataBaseRepository(DataBaseRepository(activity!!.application))
            .retrofitFactory(RetrofitFactory)
            .build().inject(this)

        return inflater.inflate(R.layout.fragment_my_devices, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        my_devices_swipe_container.setOnRefreshListener { updateMyDevices() }

        my_devices_swipe_container.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        snackbar = activity!!.coordinator_layout!!.longSnackbar(R.string.pull_down_to_update)

        my_devices_recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MyDevicesFragment.context)
            adapter = myDevicesAdapter
        }

        myDevicesAdapter.addAll(myDevices.toList())

        updateMyDevices()

    }

    private fun updateMyDevices() {

        val callDevices = boxService.getMyDevices(user.userId)
        callDevices.enqueue(object : Callback<List<MyDeviceDataClass>> {
            override fun onFailure(call: Call<List<MyDeviceDataClass>>, t: Throwable) {

                myDevicesAdapter.clear()

                myDevices = setOf()

                snackbar = activity!!.coordinator_layout!!.longSnackbar(R.string.fail_to_update_devices)

                my_devices_swipe_container.isRefreshing = false
            }

            override fun onResponse(call: Call<List<MyDeviceDataClass>>, response: Response<List<MyDeviceDataClass>>?) {
                response?.body()?.let {
                    val list: List<MyDevice> = MyDevicesTransformer.transform(it)

                    myDevicesAdapter.clear()

                    myDevices = list.toSet()

                    myDevicesAdapter.addAll(list)
                }

                my_devices_swipe_container.isRefreshing = false
            }

        })
    }

    override fun onResume() {
        super.onResume()

        fragmentController.setToolbarTitle(R.string.my_boxes)
    }

}
