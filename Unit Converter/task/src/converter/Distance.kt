package converter

enum class Distance {
    METER,
    KILOMETER,
    CENTIMETER,
    MILIMETER,
    MILE,
    YARD,
    FOOT,
    INCH;

    companion object {
        fun unitType(word: String): Distance? {
            val mtList = arrayListOf("m", "meter", "meters")
            val kmList = arrayListOf("km", "kilometer", "kilometers")
            val cmList = arrayListOf("cm", "centimeter", "centimeters")
            val miList = arrayListOf("mm", "millimeter", "millimeters")
            val mlList = arrayListOf("mi", "mile", "miles")
            val yaList = arrayListOf("yd", "yard", "yards")
            val feList = arrayListOf("ft", "foot", "feet")
            val inList = arrayListOf("in", "inch", "inches")

            return when {
                mtList.contains(word) -> METER
                kmList.contains(word) -> KILOMETER
                cmList.contains(word) -> CENTIMETER
                miList.contains(word) -> MILIMETER
                mlList.contains(word) -> MILE
                yaList.contains(word) -> YARD
                feList.contains(word) -> FOOT
                inList.contains(word) -> INCH
                else -> null
            }
        }

        fun convertToMeters(number: Double, unit: Distance): Double {
            val numberMeters = when (unit) {
                METER -> number * 1.0
                KILOMETER -> number * 1000.0
                CENTIMETER -> number * 0.01
                MILIMETER -> number * 0.001
                MILE -> number * 1609.35
                YARD -> number * 0.9144
                FOOT -> number * 0.3048
                INCH -> number * 0.0254
            }
            return numberMeters
        }
    }
}


// probati sealed klase