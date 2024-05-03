package org.velka.soundmanager.controllers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.velka.soundmanager.R
import org.velka.soundmanager.model.SoundProfile
import org.velka.soundmanager.ui.home.ProfileHomeFragment
import org.velka.soundmanager.utils.PROFILE_JSON_FILENAME

class ProfileHomeController(private val context: Context) {

    fun saveProfile(profile: SoundProfile) {
        if (!context.fileList().contains(PROFILE_JSON_FILENAME)) {
            val jsonArray = mutableListOf(profile)
            val jsonString = Gson().toJson(jsonArray)
            context.openFileOutput(PROFILE_JSON_FILENAME, Context.MODE_PRIVATE).use {
                it.write(jsonString.toByteArray())
            }
        }
        else {
            //Open the file and save it as a new member of a json array
            val file = context.openFileInput(PROFILE_JSON_FILENAME)
            val jsonStringArray = file.bufferedReader().use { it.readText() }
            val jsonArray = Gson().fromJson(jsonStringArray, Array<SoundProfile>::class.java).toMutableList()
            jsonArray.add(profile)
            val newJsonString = Gson().toJson(jsonArray)
            context.openFileOutput(PROFILE_JSON_FILENAME, Context.MODE_PRIVATE).use {
                it.write(newJsonString.toByteArray())
            }
        }
        // Get the NavHostFragment
        val navHostFragment = (context as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)

        // Get the currently displayed fragment from the NavHostFragment's child FragmentManager
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)


        if (currentFragment is ProfileHomeFragment) {
            // Add the new profile to the adapter and notify it that an item has been inserted
            currentFragment.addProfileToAdapter(profile)
        } else {
            // Handle the case where ProfileHomeFragment is not currently visible
            // This will depend on your specific application logic
        }

    }

    fun loadProfiles(): MutableList<SoundProfile> {
        val gson = Gson()
        val profiles = mutableListOf<SoundProfile>()


        val file = context?.getFileStreamPath(PROFILE_JSON_FILENAME)
        if (file == null || !file.exists()) {
            // If the file doesn't exist, return an empty list
            return profiles
        }

        // Open the file and read the JSON string
        val jsonString = context?.openFileInput(PROFILE_JSON_FILENAME)?.bufferedReader().use { it?.readText() }

        // Convert the JSON string to a SoundProfile object and add it to the list
        if (jsonString != null) {
            val type = object : TypeToken<List<SoundProfile>>() {}.type
            profiles.addAll(gson.fromJson(jsonString, type))
        }

        return profiles
    }
}