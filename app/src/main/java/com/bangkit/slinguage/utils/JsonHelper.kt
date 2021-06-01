package com.bangkit.slinguage.utils

import android.content.Context
import android.util.Log
import com.bangkit.slinguage.data.source.model.Education
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {
    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadEducation(): List<Education> {
        val list = ArrayList<Education>()
        try {
            val responseObject = JSONObject(parsingFileToString("education.json").toString())
            val listArray = responseObject.getJSONArray("educations")
            Log.d("TAG", "loadEducation: ${listArray.length()}")
            for (i in 0 until listArray.length()) {
                val course = listArray.getJSONObject(i)
                val title = course.getString("title")
                val image = course.getString("image")
                Log.d("TAG", "Aplhabet: $title")
                Log.d("TAG", "Aplhabet: $image")

                val courseResponse = Education(title = title, image = image)
                list.add(courseResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d("TAG", "LIST: ${list.size}")

        return list
    }



}
