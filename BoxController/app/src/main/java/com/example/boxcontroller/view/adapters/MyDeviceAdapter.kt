package com.example.boxcontroller.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.boxcontroller.R
import com.example.boxcontroller.view.viewmodel.MyDevice
import kotlinx.android.synthetic.main.my_device_card_layout.view.*

class MyDeviceAdapter(
    private val context: Context,
    private val listener: (View) -> Unit
) : RecyclerView.Adapter<MyDeviceAdapter.MyDeviceViewHolder>() {

    private val animation = AnimationUtils.loadAnimation(context, R.anim.device_detail_animation).apply {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) { listener.invoke(deviceSelected) }

            override fun onAnimationStart(animation: Animation?) {}
        })
    }
    private val devices = ArrayList<MyDevice>()
    private lateinit var deviceSelected : View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDeviceViewHolder =
        LayoutInflater.from(context)
            .inflate(R.layout.my_device_card_layout, parent, false)
            .run { MyDeviceViewHolder(this) }

    override fun getItemCount(): Int = devices.size

    override fun onBindViewHolder(holder: MyDeviceViewHolder, position: Int) {
        holder.itemView.tag = devices[position]

        holder.boxName.text = devices[position].boxName
        holder.remaining.text = devices[position].pillsRemaining.toString()
        holder.nextPill.text = devices[position].nextPill
        holder.lastPill.text = devices[position].lastPill
        holder.buyPills.text = devices[position].buyPillsOn

        deviceSelected = holder.itemView

        holder.itemView.setOnClickListener {
            holder.itemView.startAnimation(animation)
        }
    }

    fun clear() {
        val positions = devices.size
        devices.clear()
        notifyItemRangeRemoved(0, positions)
    }

    fun addAll(items: List<MyDevice>) {
        devices.addAll(items)
        notifyItemRangeInserted(0, devices.size)
    }

    class MyDeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var boxName = itemView.box_name!!
        var remaining = itemView.remaining!!
        var nextPill = itemView.next_pill!!
        var lastPill = itemView.last_pill!!
        var buyPills = itemView.buy_pills!!
    }
}
