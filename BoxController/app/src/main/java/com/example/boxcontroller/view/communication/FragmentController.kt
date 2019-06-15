package com.example.boxcontroller.view.communication

import androidx.annotation.StringRes
import com.example.boxcontroller.view.viewmodel.User

interface FragmentController {
    fun changeFragment(newFragmentType: String, addToBackStack: Boolean)
    fun setToolbarTitle(@StringRes title: Int)
    fun setUser(user: User)
}