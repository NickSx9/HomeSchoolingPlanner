package com.sid1722289.schoolhomeorganiser.ui.educatelinks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sid1722289.schoolhomeorganiser.R


class EducateLinksFragment : Fragment() {

    private lateinit var educateLinksViewModel : EducateLinksViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        educateLinksViewModel =
            ViewModelProvider(this).get(EducateLinksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_educatelinks, container, false)
        val textView: TextView = root.findViewById(R.id.text_educatelinks)
        educateLinksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val openURL = Intent(Intent.ACTION_VIEW)
        val byteSize: Button = root.findViewById(R.id.buttonOne)
        val twinkl: Button = root.findViewById(R.id.buttonTwo)
        val myTutor: Button = root.findViewById(R.id.buttonThree)
        val tedEd: Button = root.findViewById(R.id.buttonFour)
        val workSheet: Button = root.findViewById(R.id.buttonFive)
        val schoolRun: Button = root.findViewById(R.id.buttonSix)
        val otherTwo: Button = root.findViewById(R.id.buttonSeven)
        val otherThree: Button = root.findViewById(R.id.buttonEight)
        byteSize.setOnClickListener {
            openURL.data = Uri.parse("https://www.bbc.co.uk/bitesize")
            startActivity(openURL)
        }
        myTutor.setOnClickListener() {
            openURL.data = Uri.parse("https://www.mytutor.co.uk/")
            startActivity(openURL)
        }
        twinkl.setOnClickListener() {
            openURL.data = Uri.parse("https://www.twinkl.co.uk/")
            startActivity(openURL)
        }
        tedEd.setOnClickListener() {
            openURL.data = Uri.parse("https://ed.ted.com/")
            startActivity(openURL)
        }
        workSheet.setOnClickListener() {
            openURL.data = Uri.parse("https://www.worksheetfun.com/")
            startActivity(openURL)
        }
        schoolRun.setOnClickListener() {
            openURL.data = Uri.parse("https://www.theschoolrun.com/")
            startActivity(openURL)
        }
        return root
    }
}
