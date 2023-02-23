package converter

enum class Measure {
    NORMAL,
    PLURAL;

    companion object{
        fun setAmount(amount: Double): Measure {
           if(amount == 1.0){
               return NORMAL
           }
            return PLURAL
        }
    }

}
