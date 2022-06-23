package mk.nasa.nasamodels

import com.google.gson.annotations.SerializedName

data class NasaBaseLinks(
    @SerializedName("next") val next: String,
    @SerializedName("prev") val prev: String,
    @SerializedName("self") val self: String
)