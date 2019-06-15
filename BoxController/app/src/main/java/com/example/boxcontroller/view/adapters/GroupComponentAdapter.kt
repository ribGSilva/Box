package com.example.boxcontroller.view.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.boxcontroller.R
import com.example.boxcontroller.view.viewmodel.GroupComponent
import kotlinx.android.synthetic.main.info_component_card.view.*

@Suppress("DEPRECATION")
class GroupComponentAdapter(
    private val components: List<GroupComponent>,
    private val context: Context
) : RecyclerView.Adapter<GroupComponentAdapter.GroupComponentViewHolder>() {

    private val animation = AnimationUtils.loadAnimation(context, R.anim.image_group_component_animation)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupComponentViewHolder =
        LayoutInflater.from(context)
            .inflate(R.layout.info_component_card, parent, false)
            .run { GroupComponentViewHolder(this) }

    override fun getItemCount() = components.size

    override fun onBindViewHolder(holder: GroupComponentViewHolder, position: Int) {
        holder.name.text = components[position].name
        holder.ra.text = components[position].ra
        holder.email.text = components[position].email
        holder.contact.text = components[position].contact
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.image.background = context.getDrawable(components[position].image)
        } else {
            holder.image.background = context.resources.getDrawable(components[position].image)
        }

        holder.itemView.setOnClickListener { holder.image.startAnimation(animation) }

        holder.image.startAnimation(animation)
    }

    class GroupComponentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.txt_component!!
        var email = itemView.txt_email!!
        var ra = itemView.txt_ra!!
        var contact = itemView.txt_contact!!
        var image = itemView.image_component!!
    }
}