package com.sid1722289.schoolhomeorganiser.ui.mealplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.R


class MealPlannerFragment : Fragment() {

    private lateinit var mealPlannerViewModel: MealPlannerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mealPlannerViewModel =
            ViewModelProvider(this).get(MealPlannerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mealplanner, container, false)
        val textView: TextView = root.findViewById(R.id.text_mealplanner)
        mealPlannerViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}

