package com.example.boxcontroller.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.boxcontroller.R
import com.example.boxcontroller.databinding.FragmentConfigurationHelpBinding
import com.example.boxcontroller.injector.ApplicationDataCenter
import com.example.boxcontroller.injector.DaggerComponentInjector
import com.example.boxcontroller.injector.DataBaseRepository
import com.example.boxcontroller.injector.RetrofitFactory
import com.example.boxcontroller.view.delegate.contentView
import com.example.boxcontroller.view.viewmodel.ConfigurationBoxDefault
import kotlinx.android.synthetic.main.fragment_configuration_help.*
import javax.inject.Inject

class ConfigurationHelpFragment : BasicFragment() {

    @Inject lateinit var configuration: ConfigurationBoxDefault
    private val screenData by contentView<FragmentConfigurationHelpBinding>(R.layout.fragment_configuration_help)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerComponentInjector.builder()
            .applicationDataCenter(ApplicationDataCenter)
            .dataBaseRepository(DataBaseRepository(activity!!.application))
            .retrofitFactory(RetrofitFactory)
            .build().inject(this)

        screenData.config = configuration

        return screenData.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnConfigureBox.setOnClickListener {
            fragmentController.changeFragment(FragmentTypes.SYNC_BOX_FRAGMENT.name, true)
        }
    }

    override fun onResume() {
        super.onResume()

        fragmentController.setToolbarTitle(R.string.box_configuration)
    }
}
