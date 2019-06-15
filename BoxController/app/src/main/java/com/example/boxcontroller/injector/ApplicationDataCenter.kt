package com.example.boxcontroller.injector

import com.example.boxcontroller.R
import com.example.boxcontroller.datatransfer.UserTransformer
import com.example.boxcontroller.domain.database.dataclass.UserDC
import com.example.boxcontroller.view.viewmodel.*
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import java.net.InetAddress
import javax.inject.Singleton

@Module
object ApplicationDataCenter {

    @Provides
    fun getUser(userDC: UserDC) = UserTransformer.transform(userDC = userDC)

    @Provides
    @Singleton
    fun appLayoutData() = LayoutData(R.string.app_name, R.drawable.ic_menu, R.id.home)

    @Provides
    fun appBox() = Box(
        "",
        "",
        "",
        InetAddress.getByName("127.0.0.1"),
        0,
        false
    )

    @Provides
    @Singleton
    fun appDefaultBoxConf() = ConfigurationBoxDefault("WIFI", "WIFI")

    @Provides
    @Singleton
    @ElementsIntoSet
    fun appMyDevices() = mutableSetOf<MyDevice>()

    @Provides
    @Singleton
    fun appMyDeviceDetail() = MyDeviceDetail(
        0,
        "",
        0,
        "",
        "",
        "",
        mutableSetOf()
    )

    @Provides
    @IntoSet
    fun appMyDevice() = MyDevice(
        0,
        "",
        0,
        "",
        "",
        ""
    )

    @Provides
    @Singleton
    @ElementsIntoSet
    fun appMyAlarms() = mutableSetOf<Alarm>()

    @Provides
    @IntoSet
    fun appAlarm() = Alarm(
        "",
        ""
    )

    @Provides
    @Singleton
    @ElementsIntoSet
    fun appAlarmsDetail() = mutableSetOf<AlarmsDetail>()

    @Provides
    @IntoSet
    fun appAlarmDetail() = AlarmsDetail(
        0,
        0
    )


}