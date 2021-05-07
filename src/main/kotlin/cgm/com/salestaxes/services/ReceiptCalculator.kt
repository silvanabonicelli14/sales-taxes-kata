package cgm.com.salestaxes.services

import cgm.com.salestaxes.entities.*
import cgm.com.salestaxes.interfaces.TaxService
import kotlin.math.roundToLong

class ReceiptCalculator(private val taxService: TaxService) {

    fun receipt(sale: Sale): Receipt {
        var totalPrice: Double = 0.0
        var totalTax: Double = 0.0
        var tax : Double = 0.0
        sale.salesList.forEach {

            tax = taxService.applyTax(it.article, sale.country)
            it.taxedPrice = getPriceOfTaxedArticle(it.article, it.quantity, tax)

            totalPrice += it.taxedPrice
            totalTax += tax
        }
        return Receipt(totalPrice, 0.0, mutableListOf())
    }

    private fun getPriceOfTaxedArticle(article: Article, quantity: Int, tax: Double): Double {
        return ((article.price * quantity) + ((article.price * quantity) * tax)).roundedDouble
    }
}

private val Double.roundedDouble: Double
    get() {
       return (this * 100).roundToLong() / 100.0
    }

