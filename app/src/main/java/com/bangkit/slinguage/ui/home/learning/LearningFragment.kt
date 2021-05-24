package com.bangkit.slinguage.ui.home.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.slinguage.R

class LearningFragment : Fragment() {

    private lateinit var learningViewModel: LearningViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        learningViewModel =
            ViewModelProvider(this).get(LearningViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}