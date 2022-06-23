package mk.nasa.nasamodels

import com.google.gson.annotations.SerializedName

data class NasaNeoBase (
    @SerializedName("links") val links : NasaBaseLinks,
    @SerializedName("element_count") val elementCount : Long,
    @SerializedName("near_earth_objects") val nasaNeoMap : Map<String, List<NasaNeo>>
)