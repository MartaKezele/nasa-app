package mk.nasa.nasamodels

import com.google.gson.annotations.SerializedName

data class NasaEstimatedDiameter(
    @SerializedName("kilometers") val kilometers : NasaEstimatedDiameterMinMax,
    @SerializedName("meters") val meters : NasaEstimatedDiameterMinMax,
    @SerializedName("miles") val miles : NasaEstimatedDiameterMinMax,
    @SerializedName("feet") val feet : NasaEstimatedDiameterMinMax
)