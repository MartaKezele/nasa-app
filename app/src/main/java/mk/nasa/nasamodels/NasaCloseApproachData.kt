package mk.nasa.nasamodels

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class NasaCloseApproachData(
    @SerializedName("close_approach_date") val closeApproachDate : String,
    @SerializedName("close_approach_date_full") val closeApproachDateFull : String,
    @SerializedName("epoch_date_close_approach") val epochDateCloseApproach : BigInteger,
    @SerializedName("relative_velocity") val relativeVelocity : NasaRelativeVelocity,
    @SerializedName("miss_distance") val missDistance : NasaMissDistance,
    @SerializedName("orbiting_body") val orbitingBody : NasaOrbitingBody

)
