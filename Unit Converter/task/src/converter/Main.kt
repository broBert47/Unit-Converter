package converter

fun main() {
    do {
        println("Enter what you want to convert (or exit):")
        val input = readln().split(" ")
        if (input.contains("exit")) {
            return
        }
        val firstNumber = input[0].toDouble()
        val firstUnit = input[1].lowercase()
        val secondUnit = input[3].lowercase()

        // NA TEMELJU IMENA, ODREĐUJEMO KOJEG TIPA SU INPUT JEDINICE KOJE CONVERTAMO
        val inputType = if (Distance.unitType(firstUnit) != null) {
            Distance.unitType(firstUnit)
        } else if (Weight.unitType(firstUnit) != null) {
            Weight.unitType(firstUnit)
        } else {
            "???"
        }

        // NA TEMELJU IMENA, ODREĐUJEMO KOJEG TIPA SU OUTPUT JEDINICE
        val outputType = if (Distance.unitType(secondUnit) != null) {
            Distance.unitType(secondUnit)
        } else if (Weight.unitType(secondUnit) != null) {
            Weight.unitType(secondUnit)
        } else {
            "???"
        }

        // GLEDAMO DA LI JE BROJ JEDINICA ILI MNOŽINA
        val inputAmountType = Measure.setAmount(firstNumber)
        // NA TEMELJU TIPA PODATKA I BROJA, DAJEMO PRAVILNO IME INPUT-U
        val inputTypeName = typeName(inputType, inputAmountType)
        // SETUPIRAMO IME OUTPUTA i INPUTA SAMO ZA HENDLANJE ERRORA
        val inputTypeNameError = typeName(inputType, Measure.PLURAL)
        val outputTypeNameError = typeName(outputType, Measure.PLURAL)

        if (inputType == "???" || outputType == "???") {
            println("Conversion from $inputTypeNameError to $outputTypeNameError is impossible")
        }

        // GLEDAMO DA LI SU INPUT I OUTPUT ISTOG TIPA. AKO JESU, CONVERTIRAMO I PRINTAMO
        if (inputType is Distance) {
            if (outputType is Distance) {
                val convertedAmount = distanceConverter(firstNumber, inputType, outputType)
                val outputAmountType = Measure.setAmount(convertedAmount)
                val outputTypeName = typeName(outputType, outputAmountType)
                println("$firstNumber $inputTypeName is $convertedAmount $outputTypeName\n")
            } else {
                println("Conversion from $inputTypeNameError to $outputTypeNameError is impossible")
            }
        } else if (inputType is Weight) {
            if (outputType is Weight) {
                val convertedAmount = weightConverter(firstNumber, inputType, outputType)
                val outputAmountType = Measure.setAmount(convertedAmount)
                val outputTypeName = typeName(outputType, outputAmountType)
                println("$firstNumber $inputTypeName is $convertedAmount $outputTypeName\n")
            } else {
                println("Conversion from $inputTypeNameError to $outputTypeNameError is impossible")
            }
        }
    } while (true)
}

fun distanceConverter(number: Double, inputType: Distance, outputType: Distance): Double {
    var result: Double = 0.0
    val intermediate = Distance.convertToMeters(number, inputType)
    when (outputType) {
        Distance.METER -> result = intermediate / 1
        Distance.KILOMETER -> result = intermediate / 1000
        Distance.CENTIMETER -> result = intermediate / 0.01
        Distance.MILIMETER -> result = intermediate / 0.001
        Distance.MILE -> result = intermediate / 1609.35
        Distance.YARD -> result = intermediate / 0.9144
        Distance.FOOT -> result = intermediate / 0.3048
        Distance.INCH -> result = intermediate / 0.0254
    }
    return result
}

fun weightConverter(number: Double, inputType: Weight, outputType: Weight): Double {
    var result: Double = 0.0
    val intermediate = Weight.convertToGrams(number, inputType)
    when (outputType) {
        Weight.GRAM -> result = intermediate / 1.0
        Weight.KILOGRAM -> result = intermediate / 1000.0
        Weight.MILLIGRAM -> result = intermediate / 0.001
        Weight.POUND -> result = intermediate / 453.592
        Weight.OUNCE -> result = intermediate / 28.3495
    }
    return result
}

fun typeName(type: Any?, amount: Measure): String {
    var wordName = ""
    when (type) {
        Distance.METER -> wordName = "meter"
        Distance.KILOMETER -> wordName = "kilometer"
        Distance.CENTIMETER -> wordName = "centimeter"
        Distance.MILIMETER -> wordName = "millimeter"
        Distance.MILE -> wordName = "mile"
        Distance.YARD -> wordName = "yard"
        Distance.FOOT -> wordName = "foot"
        Distance.INCH -> wordName = "inch"
        Weight.GRAM -> wordName = "gram"
        Weight.KILOGRAM -> wordName = "kilogram"
        Weight.MILLIGRAM -> wordName = "milligram"
        Weight.POUND -> wordName = "pound"
        Weight.OUNCE -> wordName = "ounce"
        else -> wordName = "???"
    }

    if (amount == Measure.PLURAL && wordName != "???") {
        val pluralAdd = "s"
        when (type) {
            Distance.FOOT -> wordName = "feet"
            Distance.INCH -> wordName = "inches"
            Weight.OUNCE -> wordName = "ounces"
            else -> wordName += pluralAdd
        }

    }
    return wordName
}

