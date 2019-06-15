package com.example.boxcontroller.view.fragment

import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import com.example.boxcontroller.view.communication.FragmentController
import com.example.boxcontroller.view.delegate.bindFragmentController

abstract class BasicFragment : Fragment() {

    protected val fragmentController by bindFragmentController<FragmentController>()
    protected lateinit var snackbar : Snackbar

}