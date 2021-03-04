package com.sid1722289.schoolhomeorganiser.ui.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.R

class LessonFragment : Fragment() {
    private lateinit var lessonViewModel: LessonViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lessonViewModel =
            ViewModelProvider(this).get(LessonViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_lesson, container, false)
        //val textView: TextView = root.findViewById(R.id.text_lesson)
        //lessonViewModel.text.observe(viewLifecycleOwner, Observer {
        //    textView.text = it
        //})
        return root
    }

}
