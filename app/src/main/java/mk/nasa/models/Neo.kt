package mk.nasa.models

import mk.nasa.nasamodels.NasaCloseApproachData
import mk.nasa.nasamodels.NasaOrbitingBody
import java.math.BigInteger

data class Neo(
    var _id: Long?,
    var neoReferenceId: String,
    var name: String,
    var absoluteMagnitudeH : Double,
    var minEstimatedDiameterInKm : Double,
    var maxEstimatedDiameterInKm : Double,
    var isPotentiallyHazardousAsteroid: Boolean,
    var epochDateCloseApproach : BigInteger,
    var relativeVelocityKmph : Double,
    var orbitingBody : NasaOrbitingBody,
    var isSentryObject: Boolean
)