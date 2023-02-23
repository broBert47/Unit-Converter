package converter

enum class Temperature {
    CELSIUS,
    FAHRENHEIT,
    KELVIN;

    companion object {
        fun unitType(word: String): Temperature? {
            val ceList = arrayListOf("degrees celsius", "degree celsius", "celsius", "dc", "c")
            val fhList = arrayListOf("degrees fahrenheit", "degree fahrenheit", "fahrenheit", "df", "f")
            val keList = arrayListOf("kelvins", "kelvin", "k")

            return when {
                ceList.contains(word) -> CELSIUS
                fhList.contains(word) -> FAHRENHEIT
                keList.contains(word) -> KELVIN
                else -> null
            }
        }

        fun tempConverter(number: Double, inputType: Temperature, outputType: Temperature): Double {
            var result = 0.0
            when (inputType) {
                CELSIUS -> {
                    result = when (outputType) {
                        FAHRENHEIT -> number * (9 / 5) + 32
                        KELVIN -> number + 273.15
                        CELSIUS -> number
                    }
                }

                FAHRENHEIT -> {
                    result = when (outputType) {
                        CELSIUS -> (number - 32) * (5 / 9)
                        KELVIN -> (number + 459.67) * (5 / 9)
                        FAHRENHEIT -> number
                    }
                }

                KELVIN -> {
                    result = when (outputType) {
                        FAHRENHEIT -> number * (9 / 5) - 459.67
                        CELSIUS -> number - 273.15
                        KELVIN -> number
                    }
                }
            }

            return result
        }
    }
}