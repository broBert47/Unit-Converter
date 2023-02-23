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
            var numberMeters = 0.0
            when (unit) {
                METER -> numberMeters = number * 1.0
                KILOMETER -> numberMeters = number * 1000.0
                CENTIMETER -> numberMeters = number * 0.01
                MILIMETER -> numberMeters = number * 0.001
                MILE -> numberMeters = number * 1609.35
                YARD -> numberMeters = number * 0.9144
                FOOT -> numberMeters = number * 0.3048
                INCH -> numberMeters = number * 0.0254
            }
            return numberMeters
        }
    }
}


// probati sealed klase