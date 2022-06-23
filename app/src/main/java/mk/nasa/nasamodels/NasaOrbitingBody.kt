package mk.nasa.nasamodels

enum class NasaOrbitingBody(val value: String) {
    Earth("Earth");

    companion object {
        public fun fromValue(value: String): NasaOrbitingBody = when (value) {
            "Earth" -> Earth
            else    -> throw IllegalArgumentException()
        }
    }
}