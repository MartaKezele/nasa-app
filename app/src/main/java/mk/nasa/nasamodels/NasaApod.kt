package mk.nasa.nasamodels

import com.google.gson.annotations.SerializedName

data class NasaApod(
    @SerializedName("copyright") val copyright: String? = null,
    @SerializedName("date") val date: String,
    @SerializedName("explanation") val explanation: String,
    @SerializedName("hdurl") val hdurl: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("service_version") val serviceVersion: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)