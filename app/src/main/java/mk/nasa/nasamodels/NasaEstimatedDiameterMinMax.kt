package mk.nasa.nasamodels

import com.google.gson.annotations.SerializedName

data class NasaEstimatedDiameterMinMax (
    @SerializedName("estimated_diameter_min") val estimatedDiameterMin : Double,
    @SerializedName("estimated_diameter_max") val estimatedDiameterMax : Double
)