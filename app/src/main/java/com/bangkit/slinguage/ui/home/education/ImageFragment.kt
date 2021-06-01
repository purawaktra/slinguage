package com.bangkit.slinguage.ui.home.education

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.bangkit.slinguage.R
import com.bangkit.slinguage.databinding.FragmentImageBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions




class ImageFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var image: String? = null
    private var title: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            image = it.getString(ARG_PARAM1)
            title = it.getString(ARG_PARAM2)
        }
    }


    private lateinit var binding: FragmentImageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        binding = FragmentImageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Glide.with(requireActivity())
            .load(arguments?.getString(ARG_PARAM1))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_baseline_broken_image_24))
            .into(binding.imgView)

        binding.tvAlphabet.text = arguments?.getString(ARG_PARAM2)
        binding.tvClose.setOnClickListener {
            dismiss()
        }

    }



    override fun onStart() {
        super.onStart()
//        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
    companion object {
        const val TAG = "SimpleDialog"
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)

                }
            }
    }
}