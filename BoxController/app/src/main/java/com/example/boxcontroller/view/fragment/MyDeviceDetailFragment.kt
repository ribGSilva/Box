package com.example.boxcontroller.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.boxcontroller.R
import com.example.boxcontroller.databinding.FragmentMyDeviceDetailBinding
import com.example.boxcontroller.domain.service.BoxWebService
import com.example.boxcontroller.injector.ApplicationDataCenter
import com.example.boxcontroller.injector.DaggerComponentInjector
import com.example.boxcontroller.injector.DataBaseRepository
import com.example.boxcontroller.injector.RetrofitFactory
import com.example.boxcontroller.view.delegate.contentView
import com.example.boxcontroller.view.viewmodel.MyDeviceDetail
import com.example.boxcontroller.view.viewmodel.User
import kotlinx.android.synthetic.main.fragment_my_device_detail.*
import kotlinx.android.synthetic.main.main_layout.*
import org.jetbrains.anko.design.longSnackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import android.widget.ArrayAdapter
import com.example.boxcontroller.datatransfer.MyDevicesDetailTransformer
import com.example.boxcontroller.domain.service.data.MyDeviceDetailDataClass

class MyDeviceDetailFragment : BasicFragment() {

    @Inject lateinit var boxService: BoxWebService
    @Inject lateinit var myDeviceDetail: MyDeviceDetail
    @Inject lateinit var user: User
    private val layoutData by contentView<FragmentMyDeviceDetailBinding>(R.layout.fragment_my_device_detail)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DaggerComponentInjector.builder()
            .applicationDataCenter(ApplicationDataCenter)
            .dataBaseRepository(DataBaseRepository(activity!!.application))
            .retrofitFactory(RetrofitFactory)
            .build().inject(this)

        layoutData.boxDetail = myDeviceDetail

        return layoutData.root
    }

    override fun onResume() {
        super.onResume()

        fragmentController.setToolbarTitle(R.string.box_detail)

        myDeviceDetail.myDeviceDetailBoxId = 1

        val callDeviceDetail = boxService.getDeviceDetail(user.userId, myDeviceDetail.myDeviceDetailBoxId)
        callDeviceDetail.enqueue(object: Callback<MyDeviceDetailDataClass> {
            override fun onFailure(call: Call<MyDeviceDetailDataClass>, t: Throwable) {
                snackbar = activity!!.coordinator_layout!!.longSnackbar(R.string.fail_to_update_device_detail)
            }

            override fun onResponse(call: Call<MyDeviceDetailDataClass>, response: Response<MyDeviceDetailDataClass>?) {
                response?.body()?.let {
                    MyDevicesDetailTransformer.transform(it, myDeviceDetail)

                    alarms_detail_list.adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, myDeviceDetail.alarmTimeList())
                }
            }

        })
    }
}
