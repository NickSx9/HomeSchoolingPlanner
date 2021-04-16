package com.sid1722289.schoolhomeorganiser.ui.schedule

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.R
import com.sid1722289.schoolhomeorganiser.database.DayDatabase
import com.sid1722289.schoolhomeorganiser.database.ScheduleDatabase
import com.sid1722289.schoolhomeorganiser.ui.settings.SettingViewModelFactory
import com.sid1722289.schoolhomeorganiser.ui.settings.SettingsViewModel

class ScheduleFragment : Fragment() {

    private lateinit var scheduleViewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        scheduleViewModel =
//            ViewModelProvider(this).get(ScheduleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_schedule, container, false)
        val textView: TextView = root.findViewById(R.id.text_schedule)


        val application = requireNotNull(this.activity).application
        val dataSource = ScheduleDatabase.getInstance(application).scheduleDatabaseDao
        val viewModelFactory = ScheduleViewModelFactory(dataSource,application)
        val scheduleViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(ScheduleViewModel::class.java)
        // set up spinner
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

            }
        }
        scheduleViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}


