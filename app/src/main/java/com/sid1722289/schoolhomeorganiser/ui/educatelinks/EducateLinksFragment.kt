package com.sid1722289.schoolhomeorganiser.ui.educatelinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        val byteSize = root.findViewById<TextView>(R.id.byteSize)
        
        return root
    }

}
