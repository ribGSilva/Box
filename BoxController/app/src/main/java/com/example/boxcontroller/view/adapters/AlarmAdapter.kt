package com.example.boxcontroller.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boxcontroller.R
import com.example.boxcontroller.view.viewmodel.Alarm
import kotlinx.android.synthetic.main.alarm_card_layout.view.*

class AlarmAdapter(
    private val context: Context
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    private val alarms = ArrayList<Alarm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder =
        LayoutInflater.from(context).inflate(R.layout.alarm_card_layout, parent, false).run {
            AlarmViewHolder(this)
        }

    override fun getItemCount() = alarms.size

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.alarmBoxName.text = alarms[position].alarmBoxName
        holder.alarmTime.text = alarms[position].alarmTime
    }

    fun clear() {
        val positions = alarms.size
        alarms.clear()
        notifyItemRangeRemoved(0, positions)
    }

    fun addAll(items: List<Alarm>) {
        alarms.addAll(items)
        notifyItemRangeInserted(0, alarms.size)
    }


    class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var alarmBoxName = itemView.alarm_box_name!!
        var alarmTime = itemView.alarm_time!!
    }

}