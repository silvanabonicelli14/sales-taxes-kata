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
        sale.addSales(Article("Art2", 1.0, Category.Book, Country("ITA")), 1,false)

        val taxCalculator = ReceiptCalculator(TaxCalculator())
        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 13.49
        receipt.totalTax shouldBe 0.0
        receipt.listArticle.size shouldBe 2
    }

    @Test
    fun `Sale price for article with exemption but imported no tax expected`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("Art1", 14.99, Category.Book, Country("SPA")), 1, false)

        val taxCalculator = ReceiptCalculator(TaxCalculator())
        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 14.99
        receipt.totalTax shouldBe 0.0
        receipt.listArticle.size shouldBe 1
    }

    @Test
    fun `Sale price for article with no exemption  imported tax expected`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("Art1", 47.50, Category.Other, Country("SPA")), 1, true)

        val taxCalculator = ReceiptCalculator(TaxCalculator())
        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 54.65
        receipt.totalTax shouldBe 7.13
        receipt.listArticle.size shouldBe 1
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