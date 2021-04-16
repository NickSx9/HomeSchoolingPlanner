package com.sid1722289.schoolhomeorganiser.ui.lesson

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import com.sid1722289.schoolhomeorganiser.R
import com.sid1722289.schoolhomeorganiser.database.LessonDatabase
import com.sid1722289.schoolhomeorganiser.ui.schedule.ScheduleViewModel
import kotlinx.coroutines.awaitAll
import okhttp3.internal.wait
import kotlin.concurrent.thread

class LessonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_lesson, container, false)
        /*
        the four lines below are required to link this class to the methods in the view model.
        */
        val textView: TextView = root.findViewById(R.id.text_LessonPage)
        textView.text = "Home Schooling"
        val application = requireNotNull(this.activity).application
        val dataSource = LessonDatabase.getInstance(application).lessonDatabaseDao
        val viewModelFactory = LessonViewModelFactory(dataSource,application)
        val lessonViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(LessonViewModel::class.java)
        var selectedLessonName: String = ""
        //hide all the options here
        val addlessonText: TextView = root.findViewById(R.id.lessonText)
        val lessonSpinner: Spinner = root.findViewById(R.id.lessonSpinner)
        val addButton: Button = root.findViewById(R.id.buttonAdd)
        val inputBox: TextInputLayout = root.findViewById(R.id.textInputLayout)
        val readButton: Button = root.findViewById(R.id.buttonRead)
        val lessonNotes: TextView = root.findViewById(R.id.lessonNotes)
        val backButton: Button = root.findViewById(R.id.backButton)

        readButton.text = "Read"
        addlessonText.visibility = View.INVISIBLE
        lessonSpinner.visibility = View.INVISIBLE
        addButton.visibility = View.INVISIBLE
        readButton.visibility = View.INVISIBLE
        inputBox.visibility = View.INVISIBLE
        lessonNotes.visibility = View.INVISIBLE
        backButton.visibility = View.INVISIBLE

        val addNotes: Button = root.findViewById(R.id.buttonAddNotes)
        val readNotes: Button = root.findViewById(R.id.buttonReadNotes)

        readNotes.setOnClickListener { view ->
            addNotes.visibility = View.INVISIBLE
            readNotes.visibility = View.INVISIBLE
            addlessonText.visibility = View.VISIBLE
            lessonSpinner.visibility = View.VISIBLE
            readButton.visibility = View.VISIBLE
            backButton.visibility = View.VISIBLE

            addlessonText.text = "Read Lesson Notes"
            inputBox.apply {
                isFocusable = false
                isClickable = false
                isEnabled = false
            }
        }
        readButton.setOnClickListener{
            var note = lessonViewModel.note(selectedLessonName)
            if(note == "")
            {
                lessonNotes.visibility = View.VISIBLE
                Toast.makeText(activity as Context, "No Record Found", Toast.LENGTH_SHORT).show()
                lessonNotes.text = "Notes Not Found!"
            }
           else{
                Toast.makeText(activity as Context, note, Toast.LENGTH_SHORT).show()
                lessonNotes.visibility = View.VISIBLE
                lessonNotes.text = note
                lessonViewModel.clearNoteData()
            }
        }
        addNotes.setOnClickListener { view ->
            Log.d("add note clicked", "")
            addNotes.visibility = View.INVISIBLE
            readNotes.visibility = View.INVISIBLE
            addlessonText.visibility = View.VISIBLE
            lessonSpinner.visibility = View.VISIBLE
            addButton.visibility = View.VISIBLE
            inputBox.visibility = View.VISIBLE
            backButton.visibility = View.VISIBLE
        }
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
        addButton.setOnClickListener { view ->
            if(inputBox.editText?.text.toString() != "")
            {
                Log.d("TESTING_WRITE_TO_DB", selectedLessonName)
                lessonViewModel.addNotes(selectedLessonName, inputBox.editText?.text.toString())
                inputBox.editText?.text?.clear()
                Toast.makeText(activity as Context, "Note Saved", Toast.LENGTH_SHORT).show()
            }
        }
        backButton.setOnClickListener{
            addlessonText.visibility = View.INVISIBLE
            lessonSpinner.visibility = View.INVISIBLE
            addButton.visibility = View.INVISIBLE
            readButton.visibility = View.INVISIBLE
            inputBox.visibility = View.INVISIBLE
            lessonNotes.visibility = View.INVISIBLE
            backButton.visibility = View.INVISIBLE
            addNotes.visibility = View.VISIBLE
            readNotes.visibility = View.VISIBLE

        }
        return root
    }
}

//                var data = lessonViewModel.getData()
////                Log.d(data)
////                Log.d(data)
//                //Toast.makeText(activity as Context, data?.lessonName , Toast.LENGTH_SHORT).show()
//                //lessonViewModel.addNotesToDatabase(selectedLessonName,inputBox.editText.toString())