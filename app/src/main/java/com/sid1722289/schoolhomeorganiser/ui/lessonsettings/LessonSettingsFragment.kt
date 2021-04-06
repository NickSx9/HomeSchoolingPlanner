package com.sid1722289.schoolhomeorganiser.ui.lessonsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.R
import com.sid1722289.schoolhomeorganiser.database.DayDatabase
import com.sid1722289.schoolhomeorganiser.database.ScheduleDatabase
import com.sid1722289.schoolhomeorganiser.ui.schedule.ScheduleViewModel
import com.sid1722289.schoolhomeorganiser.ui.settings.SettingViewModelFactory
import com.sid1722289.schoolhomeorganiser.ui.settings.SettingsViewModel

class LessonSettingsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_lesson_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_Home_Settings)

        val application = requireNotNull(this.activity).application
        val dayDataSource = DayDatabase.getInstance(application).dayDatabaseDao
        val scheduleDataSource = ScheduleDatabase.getInstance(application).scheduleDatabaseDao
        val viewModelFactory = LessonSettingsViewModelFactory(dayDataSource,scheduleDataSource, application)
        val lessonSettingsViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(LessonSettingsViewModel::class.java)

        lessonSettingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        var mainText: TextView = root.findViewById(R.id.textSettings_Title)

        var isData: Boolean = lessonSettingsViewModel.checkDatabase("Monday")

        if(isData)
        {
            mainText.text = "Data Found"
        }
        else{
            mainText.text = "        No Data Found \nPlease Configure Settings"
        }
        return root
    }

}
