package mk.nasa.nasamodels

import com.google.gson.annotations.SerializedName

data class NasaRelativeVelocity(
    @SerializedName("kilometers_per_second") val kilometersPerSecond : String,
    @SerializedName("kilometers_per_hour") val kilometersPerHour : String,
    @SerializedName("miles_per_hour") val milesPerHour : String
)
