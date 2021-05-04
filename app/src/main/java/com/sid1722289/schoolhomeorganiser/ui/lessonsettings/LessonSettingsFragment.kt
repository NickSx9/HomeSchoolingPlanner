package com.sid1722289.schoolhomeorganiser.ui.lessonsettings

import android.content.Context
import android.opengl.Visibility
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
import com.sid1722289.schoolhomeorganiser.database.DayData
import com.sid1722289.schoolhomeorganiser.database.DayDatabase
import com.sid1722289.schoolhomeorganiser.database.ScheduleDatabase
import com.sid1722289.schoolhomeorganiser.ui.schedule.ScheduleViewModel
import com.sid1722289.schoolhomeorganiser.ui.settings.SettingViewModelFactory
import com.sid1722289.schoolhomeorganiser.ui.settings.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_lesson_settings.*
import kotlinx.android.synthetic.main.fragment_settings.*

class LessonSettingsFragment : Fragment() {

    var dataBaseDay: ArrayList<String> = ArrayList()
    var dataBaseStartTime: ArrayList<String> = ArrayList()
    var dataBaseFinishTime: ArrayList<String> = ArrayList()

    var displayStartTime: ArrayList<String> = ArrayList()
    var displayFinishTime: ArrayList<String> = ArrayList()

    lateinit var selectedDay: String
    lateinit var selectedLessonName: String
    lateinit var selectedStartTime: String
    lateinit var selectedFinishTime: String


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_lesson_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_Home_Settings)
        var saveButton: Button = root.findViewById(R.id.saveButton)

        var dayLabel: TextView = root.findViewById(R.id.dayLabel)
        var lessonLabel: TextView = root.findViewById(R.id.lessonLabel)
        var startLabel: TextView = root.findViewById(R.id.startLabel)
        var finishLabel: TextView = root.findViewById(R.id.finishLabel)

        val application = requireNotNull(this.activity).application
        val dayDataSource = DayDatabase.getInstance(application).dayDatabaseDao
        val scheduleDataSource = ScheduleDatabase.getInstance(application).scheduleDatabaseDao
        val viewModelFactory = LessonSettingsViewModelFactory(dayDataSource, scheduleDataSource, application)
        val lessonSettingsViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(LessonSettingsViewModel::class.java)

        lessonSettingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        var mainText: TextView = root.findViewById(R.id.textSettings_Title)
        var isData: Boolean? = lessonSettingsViewModel.checkDatabase()

        if (isData == true) {
            sortDayData(lessonSettingsViewModel._dayDataList)

            saveButton.visibility = View.VISIBLE
            saveButton.text = "Save"
            mainText.text = "Add Lesson Data"
            val daySpinner: Spinner = root.findViewById(R.id.daySelectorSpinner)
            daySpinner.visibility = View.VISIBLE
            var adapter = ArrayAdapter(activity as Context, android.R.layout.simple_list_item_1, dataBaseDay)
            daySpinner.adapter = adapter
            daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("Day", "Nothing")
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    setStartTime(daySpinner.selectedItemPosition)
                    setFinishTime(daySpinner.selectedItemPosition)
                    selectedDay = daySpinner.selectedItem.toString()
                    //start Time
                    val startTimeSpinner: Spinner = root.findViewById(R.id.startTimeSpinner)
                    startTimeSpinner.visibility = View.VISIBLE
                    var startAdapter = ArrayAdapter(activity as Context, android.R.layout.simple_list_item_1, displayStartTime)
                    startTimeSpinner.adapter = startAdapter
                    startTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            Log.d("Day", "Nothing")
                        }
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            selectedStartTime = startTimeSpinner.selectedItem.toString()
                        }
                    }
                    val endTimeSpinner: Spinner = root.findViewById(R.id.finishTimeSpinner)
                    endTimeSpinner.visibility = View.VISIBLE
                    var finishAdapter = ArrayAdapter(activity as Context, android.R.layout.simple_list_item_1, displayFinishTime)
                    endTimeSpinner.adapter = finishAdapter
                    endTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            Log.d("Day", "Nothing")
                        }
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            selectedFinishTime = endTimeSpinner.selectedItem.toString()
                        }
                    }
                }
            }
            val lessonSpinner: Spinner = root.findViewById(R.id.lessonNameSpinner)
            lessonSpinner.visibility = View.VISIBLE
            ArrayAdapter.createFromResource(
                    activity as Context,
                    R.array.settings_lesson,
                    android.R.layout.simple_list_item_1).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                lessonSpinner.adapter = adapter
            }
            lessonSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("Day", "Nothing")
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedLessonName = lessonSpinner.selectedItem.toString()
                }
            }
            //finish Time
        }
        else {
            mainText.text = "        No Data Found \nPlease Configure Settings"
            dayLabel.visibility = View.INVISIBLE
            lessonLabel.visibility = View.INVISIBLE
            startLabel.visibility = View.INVISIBLE
            finishLabel.visibility = View.INVISIBLE
        }
        saveButton.setOnClickListener{
            lessonSettingsViewModel.saveScheduleData(selectedDay,selectedLessonName, selectedStartTime,selectedFinishTime)
        }
        return root
    }
    private fun sortDayData(dataList: List<DayData>){
        var counter: Int = 0
        for(data in dataList)
        {
            dataBaseDay.add(data.Day)
            dataBaseStartTime.add(data.DayStartTime)
            dataBaseFinishTime.add(data.DayFinishTime)
            counter++
        }
    }
    private fun setStartTime(index: Int){
        var startTime: String = dataBaseStartTime[index]
        displayStartTime.clear()
        if(startTime == ""){
            displayStartTime.add("No Record Found")
        }
        if(startTime == "9:00"){
            displayStartTime.add("09:00")
            displayStartTime.add("10:00")
            displayStartTime.add("11:00")
            displayStartTime.add("12:00")
            displayStartTime.add("13:00")
            displayStartTime.add("14:00")
            displayStartTime.add("15:00")
            displayStartTime.add("16:00")
            displayStartTime.add("17:00")
        }
        else if(startTime == "10:00"){
            displayStartTime.add("10:00")
            displayStartTime.add("11:00")
            displayStartTime.add("12:00")
            displayStartTime.add("13:00")
            displayStartTime.add("14:00")
            displayStartTime.add("15:00")
            displayStartTime.add("16:00")
            displayStartTime.add("17:00")
        }
        else if(startTime == "11:00"){
            displayStartTime.add("11:00")
            displayStartTime.add("12:00")
            displayStartTime.add("13:00")
            displayStartTime.add("14:00")
            displayStartTime.add("15:00")
            displayStartTime.add("16:00")
            displayStartTime.add("17:00")
        }
        else if(startTime == "12:00"){
            displayStartTime.add("12:00")
            displayStartTime.add("13:00")
            displayStartTime.add("14:00")
            displayStartTime.add("15:00")
            displayStartTime.add("16:00")
            displayStartTime.add("17:00")
        }
        else if(startTime == "13:00"){
            displayStartTime.add("13:00")
            displayStartTime.add("14:00")
            displayStartTime.add("15:00")
            displayStartTime.add("16:00")
            displayStartTime.add("17:00")
        }
        else if(startTime == "14:00"){
            displayStartTime.add("14:00")
            displayStartTime.add("15:00")
            displayStartTime.add("16:00")
            displayStartTime.add("17:00")
        }
        else if(startTime == "15:00"){
            displayStartTime.add("15:00")
            displayStartTime.add("16:00")
            displayStartTime.add("17:00")
        }
        else if(startTime == "16:00"){
            displayStartTime.add("16:00")
            displayStartTime.add("17:00")
        }
        else if(startTime == "17:00"){
            displayStartTime.add("17:00")
        }
    }

    private fun setFinishTime(index: Int){
        var finishTime: String = dataBaseFinishTime[index]
        var startTime: String = displayStartTime[0]
        displayFinishTime.clear()
        if(finishTime == ""){
            displayFinishTime.add("No Record Found")
        }
        if(finishTime == "17:00"){
            if(startTime == "09:00") {
                displayFinishTime.add("10:00")
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
                displayFinishTime.add("17:00")
            }
            if(startTime == "10:00"){
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
                displayFinishTime.add("17:00")
            }
            if(startTime == "11:00"){
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
                displayFinishTime.add("17:00")
            }
            if(startTime == "12:00"){
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
                displayFinishTime.add("17:00")
            }
            if(startTime == "13:00"){
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
                displayFinishTime.add("17:00")
            }
            if(startTime == "14:00"){
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
                displayFinishTime.add("17:00")
            }
            if(startTime == "15:00"){
                displayFinishTime.add("16:00")
                displayFinishTime.add("17:00")
            }
            if(startTime == "16:00"){
                displayFinishTime.add("17:00")
            }
        }
        else if(finishTime == "16:00") {
            if (startTime == "09:00") {
                displayFinishTime.add("10:00")
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
            }
            if(startTime == "10:00") {
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
            }
            if(startTime == "11:00") {
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
            }
            if(startTime == "12:00") {
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
            }
            if(startTime == "13:00") {
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
            }
            if(startTime == "14:00") {
                displayFinishTime.add("15:00")
                displayFinishTime.add("16:00")
            }
            if(startTime == "15:00") {
                displayFinishTime.add("16:00")
            }
        }
        else if(finishTime == "15:00"){
            if(startTime == "09:00") {
                displayFinishTime.add("10:00")
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
            }
            if(startTime == "10:00") {
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
            }
            if(startTime == "11:00") {
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
            }
            if(startTime == "12:00") {
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
            }
            if(startTime == "13:00") {
                displayFinishTime.add("14:00")
                displayFinishTime.add("15:00")
            }
            if(startTime == "14:00") {
                displayFinishTime.add("15:00")
            }
        }
        else if(finishTime == "14:00") {
            if(startTime == "9:00") {
                displayFinishTime.add("10:00")
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
            }
            if(startTime == "10:00") {
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
            }
            if(startTime == "11:00") {
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
            }
            if(startTime == "12:00") {
                displayFinishTime.add("13:00")
                displayFinishTime.add("14:00")
            }
            if(startTime == "13:00") {
                displayFinishTime.add("14:00")
            }
        }
        else if(finishTime == "13:00") {
            if (startTime == "09:00") {
                displayFinishTime.add("10:00")
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
            }
            if (startTime == "10:00") {
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
            }
            if (startTime == "11:00") {
                displayFinishTime.add("12:00")
                displayFinishTime.add("13:00")
            }
            if (startTime == "12:00") {
                displayFinishTime.add("13:00")
            }
        }
            else if(finishTime == "12:00") {
            if (startTime == "09:00") {
                displayFinishTime.add("10:00")
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
            }
            if (startTime == "10:00") {
                displayFinishTime.add("11:00")
                displayFinishTime.add("12:00")
            }
            if (startTime == "11:00") {
                displayFinishTime.add("12:00")
            }
        }
            else if(finishTime == "11:00") {
            if (startTime == "09:00") {
                displayFinishTime.add("10:00")
                displayFinishTime.add("11:00")
            }
            if (startTime == "10:00") {
                displayFinishTime.add("11:00")
            }
        }
    }
}
