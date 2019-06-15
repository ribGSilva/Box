package com.example.boxcontroller.injector

import com.example.boxcontroller.domain.pushnotification.MyFirebaseMessagingService
import com.example.boxcontroller.view.activity.MainActivity
import com.example.boxcontroller.view.application.BoxApplication
import com.example.boxcontroller.view.fragment.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationDataCenter::class,
                        RetrofitFactory::class,
                        DataBaseRepository::class,
                        BoxApplication::class])
interface ComponentInjector {
    fun inject(activity: MainActivity)
    fun inject(fragment: ConfigurationHelpFragment)
    fun inject(fragment: MyDeviceDetailFragment)
    fun inject(fragment: MyDevicesFragment)
    fun inject(fragment: SyncBoxFragment)
    fun inject(fragment: AlarmsFragment)
    fun inject(myFirebaseMessagingService: MyFirebaseMessagingService)
}