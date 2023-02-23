package converter

enum class Weight {
    GRAM,
    KILOGRAM,
    MILLIGRAM,
    POUND,
    OUNCE;

    companion object {
        fun unitType(word: String): Weight? {
            val grList = arrayListOf<String>("g", "gram", "grams")
            val kgList = arrayListOf<String>("kg", "kilogram", "kilograms")
            val miList = arrayListOf<String>("mg", "milligram", "milligrams")
            val poList = arrayListOf<String>("lb", "pound", "pounds")
            val ouList = arrayListOf<String>("oz", "ounce", "ounces")

            return when {
                grList.contains(word) -> GRAM
                kgList.contains(word) -> KILOGRAM
                miList.contains(word) -> MILLIGRAM
                poList.contains(word) -> POUND
                ouList.contains(word) -> OUNCE
                else -> null
            }
        }

        fun convertToGrams(number: Double, unit: Weight): Double {
            val numberGrams = when (unit) {
                GRAM -> number * 1.0
                KILOGRAM -> number * 1000.0
                MILLIGRAM -> number * 0.001
                POUND -> number * 453.592
                OUNCE -> number * 28.3495
            }
            return numberGrams
        }
    }

}
