package org.velka.soundmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.velka.soundmanager.R
import org.velka.soundmanager.listeners.ProfileClickListener
import org.velka.soundmanager.model.SoundProfile

class ProfileGridAdapter(private var profiles: MutableList<SoundProfile>) : RecyclerView.Adapter<ProfileGridAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileIcon: ImageView = view.findViewById(R.id.profileIcon)
        val profileName: TextView = view.findViewById(R.id.profileName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile = profiles[position]
        holder.profileName.text = profile.name
        holder.profileIcon.setImageResource(R.drawable.ic_menu_camera)
        holder.itemView.setOnClickListener(ProfileClickListener(profile, holder.itemView.context))
    }

    override fun getItemCount() = profiles.size

    fun addProfile(profile: SoundProfile) {
        profiles.add(profile)
        notifyItemInserted(profiles.size - 1)
    }
}