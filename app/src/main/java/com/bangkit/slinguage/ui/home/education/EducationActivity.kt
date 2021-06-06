package com.bangkit.slinguage.ui.home.education

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.slinguage.data.source.Resource
import com.bangkit.slinguage.data.source.model.Education
import com.bangkit.slinguage.databinding.ActivityEducationBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.koin.android.viewmodel.ext.android.viewModel


class EducationActivity : AppCompatActivity() , ClickItemCallback{


    private lateinit var binding: ActivityEducationBinding
    private lateinit var educationAdapter: EducationAdapter
    private val viewModel: EducationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        educationAdapter = EducationAdapter(this)

        viewModel.getListAlphabet().observe(this, {
            when (it) {
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, " ${it.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    educationAdapter.setData(it.data)
                    educationAdapter.notifyDataSetChanged()
                }
            }
        })

        with(binding) {
            rvEducation.layoutManager = GridLayoutManager(this@EducationActivity, 3)
            rvEducation.setHasFixedSize(true)
            rvEducation.adapter = educationAdapter
        }
    }

//    fun getData(){
//        val mDatabase = FirebaseDatabase.getInstance().reference
//        val mReference = mDatabase.child("education-resource")
//
//        mReference.get().addOnCompleteListener {
//            if (it.isSuccessful) {
//                val result = it.result
//                Log.d("TAG", "getEducation: SUCCES atas")
//                educationAdapter.setData(result.children.map { snapshot ->
//                    snapshot.getValue(Education::class.java)!!
//                })
//
//            } else {
//                Log.d("TAG", "getEducation: ERROR atas")
////                education.postValue(Resource.Error(it.exception.toString()))
//            }
//        }
//    }


    override fun <T> onClick(data: T) {
        data as Education
        ImageFragment.newInstance(data.image, data.title).show(supportFragmentManager, ImageFragment.TAG)

    }
}