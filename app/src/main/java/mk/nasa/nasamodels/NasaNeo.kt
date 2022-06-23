package mk.nasa.nasamodels

import com.google.gson.annotations.SerializedName

data class NasaNeo(
    @SerializedName("links") val links: NasaNeoLinks,
    @SerializedName("id") val id: String,
    @SerializedName("neo_reference_id") val neoReferenceId: String,
    @SerializedName("name") val name: String,
    @SerializedName("nasa_jpl_url") val nasaJplUrl: String,
    @SerializedName("absolute_magnitude_h") val absoluteMagnitudeH: Double,
    @SerializedName("estimated_diameter") val estimatedDiameter: NasaEstimatedDiameter,
    @SerializedName("is_potentially_hazardous_asteroid") val isPotentiallyHazardousAsteroid: Boolean,
    @SerializedName("close_approach_data") val closeApproachData: List<NasaCloseApproachData>,
    @SerializedName("is_sentry_object") val isSentryObject: Boolean
)