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
    fun `Sale price for article with exemption`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("Art1", 12.49, Category.Book, Country("ITA")), 1,false)
//        sale.addSales(Article("Art2", 2.0, Category.Other, Country("ITA")), 1,false)

        val taxCalculator = ReceiptCalculator(TaxCalculator())
        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 12.49
        receipt.totalTax shouldBe 0.0
        receipt.listArticle.size shouldBe 0
    }

    @Test
    fun `Sale price for article with exemption but imported no tax expected`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("Art1", 14.99, Category.Book, Country("SPA")), 1, true)

        val taxCalculator = ReceiptCalculator(TaxCalculator())
        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 14.99
        receipt.totalTax shouldBe 0.0
        receipt.listArticle.size shouldBe 0
    }

    @Test
    fun `Sale price for article with no exemption NOT imported no tax expected`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("Art1", 14.99, Category.Other, Country("ITA")), 1, true)

        val taxCalculator = ReceiptCalculator(TaxCalculator())
        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 16.49
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