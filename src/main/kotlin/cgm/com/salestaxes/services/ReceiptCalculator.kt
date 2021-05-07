package cgm.com.salestaxes.services

import cgm.com.salestaxes.entities.*
import cgm.com.salestaxes.interfaces.TaxService

class ReceiptCalculator(private val taxService: TaxService) {

    fun receipt(sale: Sale): Receipt {
        var totalPrice: Double = 0.0
        var totalTax: Double = 0.0

        sale.salesList.forEach {
            it.taxedPrice = getPriceOfTaxedArticle(it.article, it.quantity)
            totalPrice += it.taxedPrice
            totalTax += getTax(it.article, sale.country)
        }
        return Receipt(totalPrice, 0.0, mutableListOf())
    }

    private fun getTax(article: Article, countryOfSale: Country): Double {
        return taxService.applyTax(article,countryOfSale)
    }

    private fun getPriceOfTaxedArticle(article: Article, quantity: Int): Double {
        return when (article) {
            is Article.ArticleByShelf -> (article.price * quantity) + 1.0
            is Article.ArticleByPiece -> (article.price * quantity) + 1.0
        }
    }
}

