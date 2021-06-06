package com.bangkit.slinguage.data.source.remote

import com.google.gson.annotations.SerializedName

data class Predict(
    @field:SerializedName("result")
    val result: String
)
