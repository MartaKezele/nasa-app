package mk.nasa.nasamodels

import com.google.gson.annotations.SerializedName

data class NasaMissDistance(
    @SerializedName("astronomical") val astronomical : String,
    @SerializedName("lunar") val lunar : String,
    @SerializedName("kilometers") val kilometers : String,
    @SerializedName("miles") val miles : String
)
