package converter

fun main() {
    do {
        println("Enter what you want to convert (or exit):")
        val input = readln().split(" ")
        // val input = mutableListOf<String>("-100.0", "kelvin", "to", "Y")
        if (input.contains("exit")) {
            return
        }
        var flag = true

        // PROVJERAVAMO DA LI JE NA PRVOM MJESTU BROJ
        var inputNumber: Double = 0.0
        try{
            inputNumber = input.first().toDouble()
        }catch (e: Exception){
            println("Parse error")
            flag = false
        }



        // ODREĐUJEMO UNIT INPUTA. PROVJERAVAMO AKO JE PRVO DEGREE. AKO JE, ONDA ZNAMO DA JE TEMPERATURA
        var firstUnit = input[1].lowercase()
        firstUnit = if(firstUnit == "degree"|| firstUnit =="degrees"){
            input[2].lowercase()
        } else {
            input[1].lowercase()
        }

        // ODREĐUJEMO KOJI JE OUTPUT UNIT
        val secondUnit = input.last().lowercase()

        // NA TEMELJU IMENA, ODREĐUJEMO KOJEG TIPA SU INPUT JEDINICE KOJE CONVERTAMO
        val inputType = if (Distance.unitType(firstUnit) != null) {
            Distance.unitType(firstUnit)
        } else if (Weight.unitType(firstUnit) != null) {
            Weight.unitType(firstUnit)
        } else if(Temperature.unitType(firstUnit) != null){
            Temperature.unitType(firstUnit)
        } else {
            null
        }

        // NA TEMELJU IMENA, ODREĐUJEMO KOJEG TIPA SU OUTPUT JEDINICE
        val outputType = if (Distance.unitType(secondUnit) != null) {
            Distance.unitType(secondUnit)
        } else if (Weight.unitType(secondUnit) != null) {
            Weight.unitType(secondUnit)
        } else if(Temperature.unitType(secondUnit) != null) {
            Temperature.unitType(secondUnit)
        } else {
            null
        }

        // GLEDAMO DA LI JE INPUT BROJ JEDINICA ILI MNOŽINA
        val inputAmountType = Measure.setAmount(inputNumber)

        // NA TEMELJU TIPA PODATKA I BROJA, DAJEMO PRAVILNO IME INPUTU U JEDNINI/MNOŽINI
        val inputTypeName = typeName(inputType, inputAmountType)

        // SETUPIRAMO IME OUTPUTA i INPUTA SAMO ZA HENDLANJE ERRORA
        val inputTypeNameError = typeName(inputType, Measure.PLURAL)
        val outputTypeNameError = typeName(outputType, Measure.PLURAL)

        // PAZIMO DA BROJ NIJE NEGATIVAN
        if(inputNumber < 0){
            if(inputType is Distance){
                println("Length shouldn't be negative")
                flag = false
            }else if(inputType is Weight){
                println("Weight shouldn't be negative")
                flag = false
            }
        }

        // AKO NE PREPOZNAJEMO NEKU JEDINICU, PRINTAMO ERROR PORUKU
        if ((inputType == null || outputType == null) && flag) {
            println("Conversion from $inputTypeNameError to $outputTypeNameError is impossible\n")
        } else if (flag) {
            // GLEDAMO DA LI SU INPUT I OUTPUT ISTOG TIPA. AKO JESU, CONVERTIRAMO I PRINTAMO
            if (inputType is Distance) {
                if (outputType is Distance) {
                    val convertedAmount = distanceConverter(inputNumber, inputType, outputType)
                    val outputAmountType = Measure.setAmount(convertedAmount)
                    val outputTypeName = typeName(outputType, outputAmountType)
                    println("$inputNumber $inputTypeName is $convertedAmount $outputTypeName\n")
                } else {
                    val outputTypeName = typeName(outputType, Measure.PLURAL)
                    println("Conversion from $inputTypeNameError to $outputTypeName is impossible\n")
                }
            } else if (inputType is Weight) {
                if (outputType is Weight) {
                    val convertedAmount = weightConverter(inputNumber, inputType, outputType)
                    val outputAmountType = Measure.setAmount(convertedAmount)
                    val outputTypeName = typeName(outputType, outputAmountType)
                    println("$inputNumber $inputTypeName is $convertedAmount $outputTypeName\n")
                } else {
                    val outputTypeName = typeName(outputType, Measure.PLURAL)
                    println("Conversion from $inputTypeNameError to $outputTypeName is impossible\n")
                }
            } else if (inputType is Temperature) {
                if (outputType is Temperature) {
                    val convertedAmount = Temperature.tempConverter(inputNumber, inputType, outputType)
                    val outputAmountType = Measure.setAmount(convertedAmount)
                    val outputTypeName = typeName(outputType, outputAmountType)
                    println("$inputNumber $inputTypeName is $convertedAmount $outputTypeName\n")
                } else {
                    val outputTypeName = typeName(outputType, Measure.PLURAL)
                    println("Conversion from $inputTypeNameError to $outputTypeName is impossible\n")
                }
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
        Temperature.CELSIUS -> wordName = "degree Celsius"
        Temperature.FAHRENHEIT -> wordName = "degree Fahrenheit"
        Temperature.KELVIN -> wordName = "kelvin"
        else -> wordName = "???"
    }

    if (amount == Measure.PLURAL && wordName != "???") {
        val pluralAdd = "s"
        when (type) {
            Distance.FOOT -> wordName = "feet"
            Distance.INCH -> wordName = "inches"
            Weight.OUNCE -> wordName = "ounces"
            Temperature.CELSIUS -> wordName = "degrees Celsius"
            Temperature.FAHRENHEIT -> wordName = "degrees Fahrenheit"
            else -> wordName += pluralAdd
        }

    }
    return wordName
}

