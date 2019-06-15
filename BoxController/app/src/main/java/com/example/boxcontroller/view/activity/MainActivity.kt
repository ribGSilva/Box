package com.example.boxcontroller.view.activity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import com.example.boxcontroller.R
import com.example.boxcontroller.databinding.MainLayoutBinding
import com.example.boxcontroller.injector.ApplicationDataCenter
import com.example.boxcontroller.injector.DaggerComponentInjector
import com.example.boxcontroller.injector.DataBaseRepository
import com.example.boxcontroller.injector.RetrofitFactory
import com.example.boxcontroller.view.communication.FragmentController
import com.example.boxcontroller.view.communication.FragmentControllerManager
import com.example.boxcontroller.view.delegate.contentView
import com.example.boxcontroller.view.fragment.*
import com.example.boxcontroller.view.viewmodel.LayoutData
import com.example.boxcontroller.view.viewmodel.User
import dagger.Module
import kotlinx.android.synthetic.main.main_layout.*
import javax.inject.Inject

@Module
class MainActivity : FragmentActivity(), FragmentController {

    @Inject lateinit var layoutData: LayoutData
    private val screenData by contentView<MainLayoutBinding>(R.layout.main_layout)
    private val fragmentControllerManager by lazy {
        FragmentControllerManager.newInstance(
            this,
            R.id.container,
            getFragmentsMap(),
            FragmentTypes.ALARMS_FRAGMENT.name,
            supportFragmentManager
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerComponentInjector.builder()
            .applicationDataCenter(ApplicationDataCenter)
            .dataBaseRepository(DataBaseRepository(application))
            .retrofitFactory(RetrofitFactory)
            .build().inject(this)

        setupLayout()

        setupToolbar()

        setupNavigationDrawer()

        setupNavigationView()

        fragmentControllerManager
    }

    private fun setupNavigationView() {
        nav_view.setCheckedItem(layoutData.drawerItemSelected)

        nav_view.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.home -> {
                    changeFragment(FragmentTypes.ALARMS_FRAGMENT.name, true)
                    layoutData.drawerItemSelected = R.id.home
                }
                R.id.myBoxes -> {
                    changeFragment(FragmentTypes.MY_DEVICES_FRAGMENT.name, true)
                    layoutData.drawerItemSelected = R.id.myBoxes
                }
                R.id.config -> {
                    changeFragment(FragmentTypes.CONFIGURATION_HELP_FRAGMENT.name, true)
                    layoutData.drawerItemSelected = R.id.config
                }
                R.id.info -> {
                    changeFragment(FragmentTypes.INFO_FRAGMENT.name, true)
                    layoutData.drawerItemSelected = R.id.info
                }
            }

            drawer_layout.closeDrawer(GravityCompat.START)

            true
        }
    }

    private fun setupNavigationDrawer() {
        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(p0: Int) {}

            override fun onDrawerSlide(p0: View, p1: Float) {}

            override fun onDrawerClosed(p0: View) {
                layoutData.toolbarIcon = R.drawable.ic_menu
                Log.i(TAG, "Navigation Drawer closed")
            }

            override fun onDrawerOpened(p0: View) {
                layoutData.toolbarIcon = R.drawable.ic_left_arrow
                
                Log.i(TAG, "Navigation Drawer opened")
            }
        })
    }

    private fun setupToolbar() {
        my_custom_toolbar.setNavigationOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START, true)
                Log.i(TAG, "Navigation Drawer closed by toolbar")
            } else {
                drawer_layout.openDrawer(GravityCompat.START, true)
                Log.i(TAG, "Navigation Drawer opened by toolbar")
            }
        }
    }

    private fun setupLayout() {
        screenData.layoutData = layoutData
    }

    private fun getFragmentsMap() = mapOf<String, Class<*>>(
        FragmentTypes.ALARMS_FRAGMENT.name to AlarmsFragment::class.java,
        FragmentTypes.CONFIGURATION_HELP_FRAGMENT.name to ConfigurationHelpFragment::class.java,
        FragmentTypes.SYNC_BOX_FRAGMENT.name to SyncBoxFragment::class.java,
        FragmentTypes.MY_DEVICES_FRAGMENT.name to MyDevicesFragment::class.java,
        FragmentTypes.MY_DEVICE_DETAIL_FRAGMENT.name to MyDeviceDetailFragment::class.java,
        FragmentTypes.INFO_FRAGMENT.name to InfoFragment::class.java
    )


    override fun changeFragment(newFragmentType: String, addToBackStack: Boolean) {
        fragmentControllerManager.changeFragment(newFragmentType, addToBackStack)
    }

    override fun setToolbarTitle(title: Int) {
        screenData.layoutData?.toolbarTitle = title
    }

    override fun setUser(user: User) {
        screenData.user = user
    }

    override fun onBackPressed() {
        super.onBackPressed()
        fragmentControllerManager.updateManagerOnBackStackChange()
    }

}
