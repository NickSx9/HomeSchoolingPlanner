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
import com.sid1722289.schoolhomeorganiser.database.DayDatabase
import com.sid1722289.schoolhomeorganiser.database.LessonDatabase
import com.sid1722289.schoolhomeorganiser.ui.lesson.LessonViewModel
import com.sid1722289.schoolhomeorganiser.ui.lesson.LessonViewModelFactory

class SettingsFragment : Fragment() {
//    private lateinit var settingsViewModel: SettingsViewModel
    var selectedDay = ""
    var selectedStart = ""
    var selectedEnd = ""
    var selectedBreak = ""


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        settingsViewModel =
//                ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        //can be moved into a fun ?
        val application = requireNotNull(this.activity).application
        val dataSource = DayDatabase.getInstance(application).dayDatabaseDao
        val viewModelFactory = SettingViewModelFactory(dataSource,application)
        val settingsViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(SettingsViewModel::class.java)

        //labels
        val startTimeLabel: TextView = root.findViewById(R.id.tv_LabelLesson)
        val endTimeLabel: TextView = root.findViewById(R.id.tv_StartTimeLabel)
        val breakTimeLabel: TextView = root.findViewById(R.id.tv_EndTimeLabel)
        //Button
        val button: Button = root.findViewById(R.id.saveButton)

        button.visibility = View.INVISIBLE
        startTimeLabel.visibility = View.INVISIBLE
        endTimeLabel.visibility = View.INVISIBLE
        breakTimeLabel.visibility = View.INVISIBLE

        val textView: TextView = root.findViewById(R.id.text_Home_Settings)
        //Day spinner starts here
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
                if (daySpinner.selectedItem.toString() != "- Select Day -") {
                    startTimeLabel.visibility = View.VISIBLE
                    endTimeLabel.visibility = View.VISIBLE
                    breakTimeLabel.visibility = View.VISIBLE
                    button.visibility = View.VISIBLE
                    // saving the selected day
                    selectedDay = daySpinner.selectedItem.toString()

                    val startSpinner: Spinner = root.findViewById(R.id.lessonSelectSpinner)
                    ArrayAdapter.createFromResource(
                            activity as Context,
                            R.array.settings_start_time,
                            android.R.layout.simple_list_item_1).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        startSpinner.adapter = adapter
                    }
                    startSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            Log.d("Lesson", "Nothing")
                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                            selectedStart = startSpinner.selectedItem.toString()
                            val endTimeSpinner: Spinner = root.findViewById(R.id.StartTimeSpinner)
                            when (startSpinner.selectedItem.toString()) {
                                "9:00" -> ArrayAdapter.createFromResource(
                                        activity as Context,
                                        R.array.settings_finish_time_one,
                                        android.R.layout.simple_list_item_1).also { adapter ->
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    endTimeSpinner.adapter = adapter
                                }
                                "10:00" -> ArrayAdapter.createFromResource(
                                        activity as Context,
                                        R.array.settings_finish_time_two,
                                        android.R.layout.simple_list_item_1).also { adapter ->
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    endTimeSpinner.adapter = adapter
                                }
                                "11:00" -> ArrayAdapter.createFromResource(
                                        activity as Context,
                                        R.array.settings_finish_time_three,
                                        android.R.layout.simple_list_item_1).also { adapter ->
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    endTimeSpinner.adapter = adapter
                                }
                                "12:00" -> ArrayAdapter.createFromResource(
                                        activity as Context,
                                        R.array.settings_finish_time_four,
                                        android.R.layout.simple_list_item_1).also { adapter ->
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    endTimeSpinner.adapter = adapter
                                }
                                "13:00" -> ArrayAdapter.createFromResource(
                                        activity as Context,
                                        R.array.settings_finish_time_five,
                                        android.R.layout.simple_list_item_1).also { adapter ->
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    endTimeSpinner.adapter = adapter
                                }
                                "14:00" -> ArrayAdapter.createFromResource(
                                        activity as Context,
                                        R.array.settings_finish_time_six,
                                        android.R.layout.simple_list_item_1).also { adapter ->
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    endTimeSpinner.adapter = adapter
                                }
                                "15:00" -> ArrayAdapter.createFromResource(
                                        activity as Context,
                                        R.array.settings_finish_time_seven,
                                        android.R.layout.simple_list_item_1).also { adapter ->
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    endTimeSpinner.adapter = adapter
                                }
                                "16:00" -> ArrayAdapter.createFromResource(
                                        activity as Context,
                                        R.array.settings_finish_time_eight,
                                        android.R.layout.simple_list_item_1).also { adapter ->
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    endTimeSpinner.adapter = adapter
                                }
                            }
                            endTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    Log.d("Lesson", "Nothing")
                                }

                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                    selectedEnd = endTimeSpinner.selectedItem.toString()
                                    val breakTime: Spinner = root.findViewById(R.id.EndTimeSpinner)
                                    ArrayAdapter.createFromResource(
                                            activity as Context,
                                            R.array.settings_break_time,
                                            android.R.layout.simple_list_item_1).also { adapter ->
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                        breakTime.adapter = adapter
                                    }
                                    breakTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                        override fun onNothingSelected(parent: AdapterView<*>?) {
                                            Log.d("Lesson", "Nothing")
                                        }

                                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                            selectedBreak = breakTime.selectedItem.toString()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        button.setOnClickListener { view ->
            Log.d("SAVE Clicked", selectedDay + selectedStart + selectedEnd + selectedBreak )
            settingsViewModel.addDayData(selectedDay,selectedStart,selectedEnd)
            Toast.makeText(activity as Context, "Data Saved", Toast.LENGTH_SHORT).show()
        }
        return root
    }
    fun showHidden(view: View) {
        view.visibility = if(view.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else{
            View.VISIBLE
        }
    }
}




// val appFab = inflater.inflate(R.layout.app_bar_main, container, false)
// val fab: FloatingActionButton = appFab.findViewById(R.id.fab)
// fab.visibility = View.GONE
// fab.setOnClickListener() {
//     Toast.makeText(activity as Context , "ON THIS PAGE", Toast.LENGTH_SHORT).show()
// }
//selectedDay = daySpinner.selectedItem.toString()
//selectedLesson = lessonSpinner.selectedItem.toString()
//selectedLesson = lessonSpinner.selectedItem.toString()
//selectedStart = endTimeSpinner.selectedItem.toString()
//Dropdown menu for day selection