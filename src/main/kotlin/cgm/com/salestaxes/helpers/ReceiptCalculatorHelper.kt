package cgm.com.salestaxes.helpers

import kotlin.math.ceil
import kotlin.math.roundToLong

fun Double.round(soldByShelf: Boolean): Double {

    return when (soldByShelf){
        true -> this.roundedToNearest05
        else -> this.roundedDouble
    }
}


val Double.roundedDouble: Double
    get() {
        return (this * 100).roundToLong() / 100.0
    }

val Double.roundedToNearest05: Double
    get() {
        return ceil(this * 20) / 20;
    }