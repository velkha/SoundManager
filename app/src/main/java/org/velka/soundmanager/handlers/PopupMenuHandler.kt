package org.velka.soundmanager.handlers

import android.app.Dialog
import android.content.Context
import android.provider.ContactsContract
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.PopupMenu
import android.widget.TimePicker
import android.widget.ToggleButton
import org.velka.soundmanager.R
import org.velka.soundmanager.listeners.ProfileSubmitClickListener
import org.velka.soundmanager.model.SoundProfile

class PopupMenuHandler {
    fun showMenu(context: Context, view: View) {
        // Create a PopupMenu
        val popup = PopupMenu(context, view)
        // Inflate the menu from xml
        popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
        // Set a click listener
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.new_profile -> {
                    // Handle new profile here
                    showNewProfileDialog(context)
                    true
                }
                R.id.new_event -> {
                    // Handle new event here
                    true
                }
                else -> false
            }
        }
        // Show the PopupMenu
        popup.show()
    }

    private fun showNewProfileDialog(context: Context) {
        // Create a new Dialog
        val dialog = Dialog(context)
        // Set the content view to be the new_profile_form layout
        dialog.setContentView(R.layout.new_profile_form)

        // Get the TimePicker widgets
        val timePickerStart = dialog.findViewById<TimePicker>(R.id.timePickerStart)
        val timePickerEnd = dialog.findViewById<TimePicker>(R.id.timePickerEnd)

        // Set the is24HourView attribute
        timePickerStart.setIs24HourView(true)
        timePickerEnd.setIs24HourView(true)

        // Show the Dialog
        dialog.show()

        // Set the dialog to match the width of the window
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams().apply {
                copyFrom(dialog.window?.attributes)
                width = WindowManager.LayoutParams.MATCH_PARENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            dialog.window?.attributes = layoutParams
        }
        val buttonSubmit = dialog.findViewById<Button>(R.id.buttonSubmit)
        buttonSubmit.setOnClickListener(ProfileSubmitClickListener(dialog, context))

    }

}