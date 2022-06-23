package mk.nasa.models

import java.time.LocalDate

data class Apod(
    var _id: Long?,
    val date: LocalDate,
    val explanation: String,
    val title: String,
    val picturePath: String,
    var read: Boolean
)
