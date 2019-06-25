package model

import api.JSONConvertable
import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name") val name: String,
    @SerializedName("capital") val capital: String
): JSONConvertable