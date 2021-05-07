package cgm.com.salestaxes

import cgm.com.salestaxes.entities.Article
import cgm.com.salestaxes.entities.Category
import cgm.com.salestaxes.entities.Country
import cgm.com.salestaxes.entities.Sale
import cgm.com.salestaxes.services.ReceiptCalculator
import cgm.com.salestaxes.services.TaxCalculator
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class MainTests {
    @Test
    internal fun `Sale price for article`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article.ArticleByPiece("Art1", 1.0, Category.Book, Country("ITA")), 2)
        sale.addSales(Article.ArticleByShelf("Art2", 2.0, Category.Other, Country("ITA")), 1)

        val taxCalculator = ReceiptCalculator(TaxCalculator())
        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 4.0
        receipt.totalTax shouldBe 0.0
        receipt.listArticle.size shouldBe 0
    }

    @Test
    internal fun `Sale price for article free of taxed but imported`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article.ArticleByPiece("Art1", 1.0, Category.Book, Country("SPA")), 2)

        val taxCalculator = ReceiptCalculator(TaxCalculator())
        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 3
        receipt.totalTax shouldBe 0.0
        receipt.listArticle.size shouldBe 0
    }
}


sealed class Tax{
    class TaxForCategory: Tax()
    class TaxForImported: Tax()
    class TaxForFree: Tax()
}

/*
  Articolopezzo
*
 ArticoloScaffale
*
* categoria
*
* Imported (origin)
*
* Tax
*
* */