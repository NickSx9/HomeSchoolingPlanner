package com.sid1722289.schoolhomeorganiser.ui.schedule

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
import androidx.lifecycle.map
import com.sid1722289.schoolhomeorganiser.R
import com.sid1722289.schoolhomeorganiser.database.DayDatabase
import com.sid1722289.schoolhomeorganiser.database.ScheduleDatabase
import com.sid1722289.schoolhomeorganiser.formatSchedule
import com.sid1722289.schoolhomeorganiser.ui.settings.SettingViewModelFactory
import com.sid1722289.schoolhomeorganiser.ui.settings.SettingsViewModel

class ScheduleFragment : Fragment() {

    private var selectedDay = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_schedule, container, false)
        val textView: TextView = root.findViewById(R.id.text_schedule)
        val checkButton: Button = root.findViewById(R.id.buttonCheck)
        val displayText: TextView = root.findViewById(R.id.dataDisplay)

        val application = requireNotNull(this.activity).application
        val dataSource = ScheduleDatabase.getInstance(application).scheduleDatabaseDao
        val viewModelFactory = ScheduleViewModelFactory(dataSource,application)
        val scheduleViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(ScheduleViewModel::class.java)
        val daySpinner: Spinner = root.findViewById(R.id.dayScheduleSpinner)
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
        scheduleViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        checkButton.setOnClickListener{
            if(selectedDay != "")
            {
                scheduleViewModel.getDataFromDatabase(selectedDay)
                Log.d("TEST", scheduleViewModel.scheduleData.size.toString())
                displayText.text = formatSchedule(scheduleViewModel.scheduleData, application.resources)
            }
        }
        return root
    }
}


