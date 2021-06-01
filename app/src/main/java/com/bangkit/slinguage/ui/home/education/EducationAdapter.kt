package com.bangkit.slinguage.ui.home.education

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.slinguage.data.source.model.Education
import com.bangkit.slinguage.databinding.ItemEducationBinding

import java.util.*

class EducationAdapter(private val callback: ClickItemCallback) : RecyclerView.Adapter<EducationAdapter.ListViewHolder>() {

    private var listData = ArrayList<Education>()
    fun setData(newListData: List<Education>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationAdapter.ListViewHolder {
        val itemMovieBinding = ItemEducationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemMovieBinding)
    }
    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: EducationAdapter.ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(private val binding: ItemEducationBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(edu: Education){
            binding.tvName.text = edu.title
            itemView.setOnClickListener{
                callback.onClick(edu)
            }
        }

    }
}