package com.example.boxcontroller.view.application

import android.app.Application
import com.example.boxcontroller.injector.ApplicationDataCenter
import com.example.boxcontroller.injector.DaggerComponentInjector
import com.example.boxcontroller.injector.DataBaseRepository
import com.example.boxcontroller.injector.RetrofitFactory
import com.example.boxcontroller.view.viewmodel.User
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class BoxApplication : Application() {

    @Provides
    @Singleton
    fun provideApplication() : BoxApplication = this

}