package com.example.boxcontroller.view.delegate

import android.app.Activity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import kotlin.reflect.KProperty

fun <T : ViewDataBinding> contentView(@LayoutRes layoutRes: Int): SetContentView<T> {
    return SetContentView(layoutRes)
}

class SetContentView<out T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) {

    private var value: T? = null

    operator fun getValue(thisRef: Any, property: KProperty<*>): T {

        if (thisRef is Fragment) {
            initContentFromFragment(thisRef)
        }

        if (thisRef is Activity) {
            initContentFromActivity(thisRef)
        }

        return value!!
    }

    private fun initContentFromActivity(thisRef: Activity) {
        value = value ?: DataBindingUtil.setContentView(thisRef, layoutRes)
    }

    private fun initContentFromFragment(thisRef: Fragment) {
        value = value ?: DataBindingUtil.inflate(
            LayoutInflater.from(thisRef.context),
            layoutRes,
            thisRef.activity?.findViewById(android.R.id.content),
            false
        )
    }
}