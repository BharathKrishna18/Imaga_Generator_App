package com.bcorp.imagageneratorapp.config

import com.google.gson.annotations.SerializedName

data class ImageRequest(
    @SerializedName("inputs") val prompt : String
)
