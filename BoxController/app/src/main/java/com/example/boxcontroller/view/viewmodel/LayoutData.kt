package com.example.boxcontroller.view.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.annotation.IdRes
import com.example.boxcontroller.BR
import com.example.boxcontroller.view.delegate.bindable
import javax.inject.Inject

class LayoutData @Inject constructor(
    toolbarTitle: Int,
    toolbarIcon: Int,
    @IdRes drawerItemSelected: Int
) : BaseObservable() {

    @get:Bindable
    var toolbarTitle by bindable(toolbarTitle, BR.toolbarTitle)

    @get:Bindable
    var toolbarIcon by bindable(toolbarIcon, BR.toolbarIcon)

    @get:Bindable
    var drawerItemSelected by bindable(drawerItemSelected, BR.drawerItemSelected)
}
