package com.example.boxcontroller.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boxcontroller.R
import com.example.boxcontroller.datatransfer.AlarmTransformer
import com.example.boxcontroller.domain.service.BoxWebService
import com.example.boxcontroller.domain.service.data.AlarmDataClass
import com.example.boxcontroller.injector.ApplicationDataCenter
import com.example.boxcontroller.injector.DaggerComponentInjector
import com.example.boxcontroller.injector.DataBaseRepository
import com.example.boxcontroller.injector.RetrofitFactory
import com.example.boxcontroller.view.adapters.AlarmAdapter
import com.example.boxcontroller.view.viewmodel.Alarm
import com.example.boxcontroller.view.viewmodel.User
import kotlinx.android.synthetic.main.fragment_alarms.*
import kotlinx.android.synthetic.main.fragment_my_devices.*
import kotlinx.android.synthetic.main.main_layout.*
import org.jetbrains.anko.design.longSnackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

class AlarmsFragment : BasicFragment() {

    @Inject lateinit var boxService: BoxWebService
    @Inject lateinit var user: User
    @Inject lateinit var myAlarms: Set<Alarm>
    private val alarmsAdapter by lazy { AlarmAdapter(context!!) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DaggerComponentInjector.builder()
            .applicationDataCenter(ApplicationDataCenter)
            .dataBaseRepository(DataBaseRepository(activity!!.application))
            .retrofitFactory(RetrofitFactory)
            .build().inject(this)

        return inflater.inflate(R.layout.fragment_alarms, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        alarms_swipe_container.setOnRefreshListener { updateAlarms() }

        alarms_swipe_container.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        snackbar = activity!!.coordinator_layout!!.longSnackbar(R.string.pull_down_to_update)

        alarms_recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AlarmsFragment.context)
            adapter = alarmsAdapter
        }

        alarmsAdapter.addAll(myAlarms.toList())

        fragmentController.setUser(user)

        updateAlarms()

    }

    private fun updateAlarms() {

        val callAlarms = boxService.getAlarms(user.userId)
        callAlarms.enqueue(object : Callback<List<AlarmDataClass>> {
            override fun onFailure(call: Call<List<AlarmDataClass>>, t: Throwable) {

                alarmsAdapter.clear()

                myAlarms = setOf()

                alarms_swipe_container.isRefreshing = false

                snackbar = activity!!.coordinator_layout!!.longSnackbar(R.string.fail_to_update_devices)

            }

            override fun onResponse(call: Call<List<AlarmDataClass>>, response: Response<List<AlarmDataClass>>?) {
                response?.body()?.let {

                    val list: List<Alarm> = AlarmTransformer.transform(it)

                    alarmsAdapter.clear()

                    myAlarms = list.toSet()

                    alarmsAdapter.addAll(list)

                    alarms_swipe_container.isRefreshing = false

                }
            }

        })

    }

    override fun onResume() {
        super.onResume()

        fragmentController.setToolbarTitle(R.string.next_alarms)
    }

}
