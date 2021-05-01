package com.sid1722289.schoolhomeorganiser.ui.lesson

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sid1722289.schoolhomeorganiser.database.LessonData
import kotlinx.coroutines.launch
import com.sid1722289.schoolhomeorganiser.database.LessonDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class LessonViewModel(
    val database: LessonDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is meal planner Fragment"
    }

    private var lesson = MutableLiveData<String>()
    private var lessonNotes = ""

    val text: LiveData<String> = _text

    private suspend fun insert(lesson: LessonData) {
        database.insert(lesson)
    }


    fun addNotes(lessonName: String, lessonNotes: String){
        viewModelScope.launch {
            Log.d("LVM - Method", "addNotes")
            var lessonDetails: LessonData? = database.get(lessonName)
            if(lessonDetails == null) {
                Log.d("LVM - lessonDetails", "= "+ lessonDetails)
                initializeDatabase()
                lessonDetails = database.getLastLessonDetails()
                if(lessonDetails == null)
                {
                    Log.d("LVM - lessonDetails", "= STILL null")
                }
                else
                {
                    Log.d("LVM - lessonDetails", "lesson name of something from the db " + lessonDetails.lessonName)
                }
            }
            else{
                database.updateLessonNotes(lessonName, lessonNotes)
            }
        }
    }

    private suspend fun clear() {
        database.clear()
    }

    private fun initializeDatabase() {
        viewModelScope.launch {
            clear()
            val englishReadingLesson = LessonData()
            val englishWritingLesson = LessonData()
            val mathsLesson = LessonData()
            val geographyLesson = LessonData()
            val scienceLesson = LessonData()
            val peLesson = LessonData()

            englishReadingLesson.lessonName = "English - Reading"
            englishWritingLesson.lessonName = "English - Writing"
            mathsLesson.lessonName = "Maths"
            geographyLesson.lessonName = "Geography"
            scienceLesson.lessonName = "Science"
            peLesson.lessonName = "PE"

            insert(englishReadingLesson)
            insert(englishWritingLesson)
            insert(mathsLesson)
            insert(geographyLesson)
            insert(scienceLesson)
            insert(peLesson)
        }
    }

    fun note(lesson: String) : String
    {
        Log.d("TESTING_READ", "lesson name is $lesson")
        viewModelScope.launch {
             getNotesForLesson(lesson).await()
        }
        Thread.sleep(500)
        return  lessonNotes
    }
    private fun getNotesForLesson(lessonName: String) = GlobalScope.async{
            lessonNotes = database.getNotesForLesson(lessonName)
            Log.d("TAG", "notes are $lessonNotes")
    }
    fun clearNoteData()
    {
        lessonNotes = ""
    }
}
/*not used
*     fun onStart(lessonName: String, lessonNotes: String){
        viewModelScope.launch {
            Log.d("LVM - Method", "onStart")
            var lessonDetails = database.get(lessonName)
            if(lessonDetails?.lessonName == null)
            {
                Log.d("LVM", "lesson name was empty")
                initializeDatabase()
            }else{
                Log.d("LVM", "lesson notes = " + lessonDetails?.lessonName)
            }
            lessonDetails?.lessonName = lessonName
            lessonDetails?.LessonNotes = lessonNotes
            //update(lessonDetails)
        }
    }
    *    fun returnNote() : String
    {
        return lessonNotes
    }
    *    private suspend fun getLessonData(lesson: String): String? {
        Log.d("TESTING_READ", "inside getLessonData")
        var data: LessonData? = database.get(lesson)
        if(data?.LessonNotes != null)
        {
            var notes = data.LessonNotes
            Log.d("TESTING_READ", notes)
        }
        Log.d("TESTING_READ", "data should be =  "+data?.LessonNotes)
        return data?.LessonNotes
    }
    *     fun getData(): LessonData? {
        var data = LessonData()
        viewModelScope.launch {
            data = get()
            Log.d("db check", data.LessonNotes)
        }
        return data
    }
    *
    private suspend fun get(): LessonData {
        return database.getLastLessonDetails()
    }
    *
    private suspend fun update(lesson: LessonData) {
        database.update(lesson)
    }
    *
    private suspend fun displayLessonData(): LessonData?
    {
      val savedLessonData = database.getLastLessonDetails()
      if(savedLessonData != null)
      {
          return savedLessonData
      }
        else{
            return savedLessonData
      }
    }

*/