package com.sid1722289.schoolhomeorganiser.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.R

class SettingsFragment : Fragment() {
    private lateinit var settingsViewModel: SettingsViewModel
    var selectedDay = ""
    var selectedLesson = ""
    var selectedStart = ""
    var selectedEnd = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_Home_Settings)



        //Dropdown menu for day selection
        val daySpinner: Spinner = root.findViewById(R.id.daySpinner)
        ArrayAdapter.createFromResource(
                activity as Context,
                R.array.settings_day,
                android.R.layout.simple_list_item_1).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            daySpinner.adapter = adapter
        }
        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("Day", "Nothing")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedDay = daySpinner.selectedItem.toString()
            }
        }

        //Dropdown menu for lesson selection
        val lessonSpinner: Spinner =  root.findViewById(R.id.lessonSpinner)
        ArrayAdapter.createFromResource(
                activity as Context,
                R.array.settings_lesson,
                android.R.layout.simple_list_item_1).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            lessonSpinner.adapter = adapter
        }
        lessonSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("Lesson", "Nothing")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedLesson = lessonSpinner.selectedItem.toString()
            }
        }

        //Dropdown menu for start time selected
        val startTimeSpinner: Spinner = root.findViewById(R.id.startTimeSpinner)
        ArrayAdapter.createFromResource(
                activity as Context,
                R.array.settings_start_time,
                android.R.layout.simple_list_item_1).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            startTimeSpinner.adapter = adapter
        }
        startTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("Lesson", "Nothing")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedStart = startTimeSpinner.selectedItem.toString()
            }
        }

        //Dropdown menu for end time selected
        val endTimeSpinner: Spinner = root.findViewById(R.id.endTimeSpinner)
        ArrayAdapter.createFromResource(
                activity as Context,
                R.array.settings_finish_time,
                android.R.layout.simple_list_item_1).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            endTimeSpinner.adapter = adapter
        }
        endTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("Lesson", "Nothing")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedEnd = endTimeSpinner.selectedItem.toString()
            }
        }

        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val button: Button = root.findViewById(R.id.saveButton)
        button.setOnClickListener { view ->
            Log.d("SAVE Clicked", selectedDay + selectedLesson + selectedStart + selectedEnd)
            Toast.makeText(activity as Context, "New Lesson Saved", Toast.LENGTH_SHORT).show()
        }
        return root
    }
}
