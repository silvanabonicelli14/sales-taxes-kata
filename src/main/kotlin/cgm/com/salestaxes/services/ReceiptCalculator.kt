package cgm.com.salestaxes.services

import cgm.com.salestaxes.entities.*
import cgm.com.salestaxes.helpers.round
import cgm.com.salestaxes.helpers.roundedDouble
import cgm.com.salestaxes.interfaces.TaxService
import kotlin.math.roundToLong

class ReceiptCalculator(private val taxService: TaxService) {

    fun receipt(sale: Sale): Receipt {
        var totalPrice: Double = 0.0
        var totalTax: Double = 0.0
        var tax : Double = 0.0
        sale.salesList.forEach {

            tax = taxService.applyTax(it.article, sale.country)
            it.taxedPrice = getTaxedPriceOfArticle(it.article, it.quantity, tax).round(it.soldByShelf)
            totalTax += getTaxedPrice(it.article, it.quantity, tax)

            totalPrice += it.taxedPrice

        }
        return Receipt(totalPrice, totalTax, sale.salesList)
    }

    private fun getTaxedPriceOfArticle(article: Article, quantity: Int, tax: Double): Double {
        return ((article.price * quantity) + ((article.price * quantity) * tax))
    }

    private fun getTaxedPrice(article: Article, quantity: Int, tax: Double): Double {
        return ((article.price * quantity) * tax).roundedDouble
    }
}