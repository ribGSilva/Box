package com.example.boxcontroller.view.delegate

import androidx.fragment.app.Fragment
import com.example.boxcontroller.view.communication.FragmentController
import kotlin.reflect.KProperty

fun <T: FragmentController> bindFragmentController(): BindFragmentController<T> {
    return BindFragmentController()
}

@Suppress("UNCHECKED_CAST")
class BindFragmentController<out T: FragmentController> {

    private var value: T? = null

    operator fun getValue(thisRef: Any, property: KProperty<*>) : T {

        value = (thisRef as Fragment).activity as T

        return value!!
    }
}