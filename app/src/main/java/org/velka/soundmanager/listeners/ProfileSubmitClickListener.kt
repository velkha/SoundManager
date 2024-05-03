package org.velka.soundmanager.listeners

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TimePicker
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.velka.soundmanager.R
import org.velka.soundmanager.adapters.ProfileGridAdapter
import org.velka.soundmanager.controllers.ProfileHomeController
import org.velka.soundmanager.model.SoundProfile
import org.velka.soundmanager.ui.home.ProfileHomeFragment
import org.velka.soundmanager.utils.PROFILE_JSON_FILENAME

class ProfileSubmitClickListener(private val dialog: Dialog, private val context: Context) : View.OnClickListener {
    override fun onClick(v: View?) {
        // Retrieve the data from the form
        val name = dialog.findViewById<EditText>(R.id.editTextName).text.toString()
        val startTime = dialog.findViewById<TimePicker>(R.id.timePickerStart).hour.toString() + ":" + dialog.findViewById<TimePicker>(R.id.timePickerStart).minute.toString()
        val endTime = dialog.findViewById<TimePicker>(R.id.timePickerEnd).hour.toString() + ":" + dialog.findViewById<TimePicker>(R.id.timePickerEnd).minute.toString()
        /*val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").filter { day ->
            dialog.findViewById<ToggleButton>(context.resources.getIdentifier("toggleButton$day", "id", context.packageName)).isChecked
        }*/
        val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").filter { day ->
            val toggleButtonId = when(day) {
                "Mon" -> R.id.toggleButtonMon
                "Tue" -> R.id.toggleButtonTue
                "Wed" -> R.id.toggleButtonWed
                "Thu" -> R.id.toggleButtonThu
                "Fri" -> R.id.toggleButtonFri
                "Sat" -> R.id.toggleButtonSat
                "Sun" -> R.id.toggleButtonSun
                else -> throw IllegalArgumentException("Invalid day: $day")
            }
            dialog.findViewById<ToggleButton>(toggleButtonId).isChecked
        }

        // Create a new Profile object
        val profile = SoundProfile(name, startTime, endTime, daysOfWeek, null, null)

        // Save the profile to the local storage
        saveProfile(context, profile)

        // Dismiss the dialog
        dialog.dismiss()
    }

    private fun saveProfile(context: Context, profile: SoundProfile) {
        val profileHomeController = ProfileHomeController(context)
        profileHomeController.saveProfile(profile)
    }
}