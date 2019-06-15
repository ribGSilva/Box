package com.example.boxcontroller.view.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.boxcontroller.BR
import com.example.boxcontroller.view.delegate.bindable
import javax.inject.Inject

class User @Inject constructor(
    userId: Int,
    userName: String,
    userEmail: String
) : BaseObservable() {

    @get:Bindable
    var userId by bindable(userId, BR.userId)

    @get:Bindable
    var userName by bindable(userName, BR.userName)

    @get:Bindable
    var userEmail by bindable(userEmail, BR.userEmail)

}