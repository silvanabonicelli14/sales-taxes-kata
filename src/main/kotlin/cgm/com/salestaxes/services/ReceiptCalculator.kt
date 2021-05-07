package cgm.com.salestaxes.services

import cgm.com.salestaxes.entities.*
import cgm.com.salestaxes.helpers.*
import cgm.com.salestaxes.interfaces.TaxService

class ReceiptCalculator(private val taxService: TaxService) {

    fun receipt(sale: Sale): Receipt {
        var totalPrice: Double = 0.0
        var totalTax: Double = 0.0
        var priceTax = 0.0

        sale.salesList.forEach {

            priceTax = getTaxedPrice(it, sale.country).roundedToNearest05
            it.taxedPrice = getTaxedPriceOfArticle(it, priceTax)

            totalTax += priceTax
            totalPrice += it.taxedPrice

        }
        return Receipt(totalPrice.roundedDouble, totalTax.roundedToNearest05, sale.salesList)
    }

    private fun getTaxedPriceOfArticle(saleArticle: SaleArticle, priceTax: Double): Double {
        return ((saleArticle.article.price * saleArticle.quantity) + priceTax).roundedDouble
    }

    private fun getTaxedPrice(saleArticle: SaleArticle, country: Country): Double {
        val tax = taxService.applyTax(saleArticle.article, country)
        return ((saleArticle.article.price * saleArticle.quantity) * tax)
    }
}