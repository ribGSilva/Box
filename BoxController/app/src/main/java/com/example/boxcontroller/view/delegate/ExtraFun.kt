package com.example.boxcontroller.view.delegate

import kotlin.reflect.KProperty1

inline fun <reified T, Y> Collection<T>.listOfField(property: KProperty1<T, Y?>):MutableList<Y> {
    val yy = ArrayList<Y>()
    this.forEach { t: T ->
        yy.add(property.get(t)!!)
    }
    return yy
}