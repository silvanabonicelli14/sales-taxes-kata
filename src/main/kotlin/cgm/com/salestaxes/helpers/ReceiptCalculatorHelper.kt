package cgm.com.salestaxes.helpers

import kotlin.math.ceil
import kotlin.math.roundToLong

val Double.roundedDouble: Double
    get() {
        return (this * 100).roundToLong() / 100.0
    }

val Double.roundedToNearest05: Double
    get() {
        return ceil(this * 20) / 20;
    }