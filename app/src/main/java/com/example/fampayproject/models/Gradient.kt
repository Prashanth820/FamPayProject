package com.example.fampayproject.models

import com.google.gson.annotations.SerializedName



data class Gradient(
    @SerializedName("colors")
    val colorList: List<String>,

    val angle: Double

)
