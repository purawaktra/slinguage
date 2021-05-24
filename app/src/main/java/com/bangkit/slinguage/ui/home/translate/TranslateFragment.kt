package com.bangkit.slinguage.ui.home.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.slinguage.R

class TranslateFragment : Fragment() {

    private lateinit var translateViewModel: TranslateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        translateViewModel =
            ViewModelProvider(this).get(TranslateViewModel::class.java)
        //        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        translateViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }
}