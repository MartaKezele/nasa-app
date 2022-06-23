package mk.nasa.models

import mk.nasa.nasamodels.NasaBaseLinks
import java.time.LocalDate

data class NeoBase(
    var links: NasaBaseLinks,
    var elementCount: Long,
    val neoMap : Map<LocalDate, List<Neo>>
)
