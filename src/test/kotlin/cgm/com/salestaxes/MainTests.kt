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
    private val taxCalculator = ReceiptCalculator(TaxCalculator())

    @Test
    fun `Sale price for article with exemption not imported no tax expected`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("Art1", 12.49, Category.Book, Country("ITA")), 1)
        sale.addSales(Article("Art2", 1.0, Category.Book, Country("ITA")), 1)

        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 13.49
        receipt.totalTax shouldBe 0.0
        receipt.listArticle.size shouldBe 2
        receipt.listArticle[0].taxedPrice shouldBe 12.49
        receipt.listArticle[1].taxedPrice shouldBe 1.0
    }

    @Test
    fun `Sale price for article with exemption imported no tax expected`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("Art1", 14.99, Category.Book, Country("SPA")), 1)

        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 15.74
        receipt.totalTax shouldBe 0.75
        receipt.listArticle.size shouldBe 1
        receipt.listArticle[0].taxedPrice shouldBe 15.74
    }

    @Test
    fun `Sale price for article with no exemption not imported local tax expected`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("Art1", 47.50, Category.Other, Country("SPA")), 1)

        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 54.65
        receipt.totalTax shouldBe 7.15
        receipt.listArticle.size shouldBe 1
        receipt.listArticle[0].taxedPrice shouldBe 54.65
    }

    @Test
    fun `Sale price for article with no exemption imported  sum of tax imported and local expected`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("Art1", 47.50, Category.Other, Country("SPA")), 1)

        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 54.65
        receipt.totalTax shouldBe 7.15
        receipt.listArticle.size shouldBe 1
        receipt.listArticle[0].taxedPrice shouldBe 54.65
    }

    @Test
    fun `Purchase 1 Happy Path`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("book", 12.49, Category.Book, Country("ITA")), 1)
        sale.addSales(Article("music cd", 14.99, Category.Other, Country("ITA")), 1)
        sale.addSales(Article("chocolate bar", 0.85, Category.Food, Country("ITA")), 1)

        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 29.83
        receipt.totalTax shouldBe 1.50
        receipt.listArticle.size shouldBe 3
        receipt.listArticle[0].taxedPrice shouldBe 12.49
        receipt.listArticle[1].taxedPrice shouldBe 16.49
        receipt.listArticle[2].taxedPrice shouldBe 0.85
    }

    @Test
    fun `Purchase 2 Happy Path`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("imported box of chocolates", 10.00, Category.Food, Country("SPA")), 1)
        sale.addSales(Article("imported bottle of perfume", 47.50, Category.Other, Country("DEU")), 1)

        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 65.15
        receipt.totalTax shouldBe 7.65
        receipt.listArticle.size shouldBe 2
        receipt.listArticle[0].taxedPrice shouldBe 10.50
        receipt.listArticle[1].taxedPrice shouldBe 54.65
    }

    @Test
    fun `Purchase 3 Happy Path`() {

        val sale = Sale(Country("ITA"))
        sale.addSales(Article("imported bottle of perfume", 27.99, Category.Other, Country("SPA")), 1)
        sale.addSales(Article("bottle of perfume", 18.99, Category.Other, Country("ITA")), 1)
        sale.addSales(Article("packet of headache pills", 9.75, Category.Medical, Country("ITA")), 1)
        sale.addSales(Article(" box of imported chocolates", 11.25, Category.Food, Country("DEU")), 1)

        val receipt = taxCalculator.receipt(sale)
        receipt.totalPrice shouldBe 74.68
        receipt.totalTax shouldBe 6.70
        receipt.listArticle.size shouldBe 4
        receipt.listArticle[0].taxedPrice shouldBe 32.19
        receipt.listArticle[1].taxedPrice shouldBe 20.89
        receipt.listArticle[2].taxedPrice shouldBe 9.75
        receipt.listArticle[3].taxedPrice shouldBe 11.85
    }
}