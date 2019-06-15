package com.example.boxcontroller.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boxcontroller.R
import com.example.boxcontroller.view.adapters.GroupComponentAdapter
import com.example.boxcontroller.view.viewmodel.GroupComponent
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : BasicFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_component.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@InfoFragment.context)
            adapter = GroupComponentAdapter(groupElements(), this@InfoFragment.context!!)
        }
    }

    private fun groupElements() = listOf(
        GroupComponent("Gabriel Ribeiro Silva", "RA: 170004848", "gabriel.silva3409@gmail.com", "(19)995-647-233", R.drawable.grib_img),
        GroupComponent("Victor Silva Azevedo", "RA: 170002079", "victor.sil.azvedo@gmail.com", "(19)983-583-349", R.drawable.victor_img),
        GroupComponent("Julio Antonio Soares", "RA: 130005165", "juliotonny@gmail.com", "(19)981-100-840", R.drawable.julio_img),
        GroupComponent("Gabriel Abner Dantas", "RA: 170003869", "Abnner99@gmail.com", "(19)993-861-013", R.drawable.gdantas_img)
    )

    override fun onResume() {
        super.onResume()

        fragmentController.setToolbarTitle(R.string.info)
    }

}
