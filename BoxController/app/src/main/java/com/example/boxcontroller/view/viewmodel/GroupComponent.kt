package com.example.boxcontroller.view.viewmodel

import androidx.annotation.DrawableRes

data class GroupComponent(
    val name: String,
    val ra: String,
    val email: String,
    val contact: String,
    @DrawableRes val image: Int
)