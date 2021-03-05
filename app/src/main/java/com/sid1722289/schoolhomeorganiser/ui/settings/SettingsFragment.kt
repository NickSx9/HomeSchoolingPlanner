package com.sid1722289.schoolhomeorganiser.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.R

class SettingsFragment : Fragment() {
    private lateinit var settingsViewModel: SettingsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_Home_Settings)
        val daySpinner: Spinner = root.findViewById(R.id.daySpinner)
        val lessonSpinner: Spinner =  root.findViewById(R.id.lessonSpinner)
        val startTimeSpinner: Spinner = root.findViewById(R.id.startTimeSpinner)
        val endTimeSpinner: Spinner = root.findViewById(R.id.endTimeSpinner)
        ArrayAdapter.createFromResource(
                activity as Context,
                R.array.settings_day,
                android.R.layout.simple_list_item_1).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            daySpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
                activity as Context,
                R.array.settings_lesson,
                android.R.layout.simple_list_item_1).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            lessonSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
                activity as Context,
                R.array.settings_start_time,
                android.R.layout.simple_list_item_1).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            startTimeSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
                activity as Context,
                R.array.settings_finish_time,
                android.R.layout.simple_list_item_1).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            endTimeSpinner.adapter = adapter
        }
        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }
     fun onClick(){

    }

}
