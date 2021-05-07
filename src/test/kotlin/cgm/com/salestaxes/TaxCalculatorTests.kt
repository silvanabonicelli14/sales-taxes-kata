package cgm.com.salestaxes

import cgm.com.salestaxes.entities.Article
import cgm.com.salestaxes.entities.Category
import cgm.com.salestaxes.entities.Country
import cgm.com.salestaxes.entities.SaleArticle
import cgm.com.salestaxes.services.TaxCalculator
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class TaxCalculatorTests {
    @ParameterizedTest(name = "ApplyTax function should return {2} for {0}")
    @MethodSource("valuesForTax")
    internal fun `ApplyTax For`(saleArticle: SaleArticle, countryOfSale: Country, expectedTax: Double) {
        val taxCalculator = TaxCalculator()
        taxCalculator.applyTax(saleArticle.article, countryOfSale) shouldBe expectedTax
    }

    companion object {
        @JvmStatic
        fun valuesForTax(): List<Arguments> =
            listOf(
                Arguments.of(
                    SaleArticle(Article("Art1", 47.50, Category.Other, Country("ITA")), 1),
                    Country("ITA"),
                    0.10
                ),
                Arguments.of(
                    SaleArticle(Article("Art1", 25.50, Category.Other, Country("ITA")), 1),
                    Country("SPA"),
                    0.15
                ),
                Arguments.of(
                    SaleArticle(Article("Art1", 25.50, Category.Food, Country("ITA")), 1),
                    Country("SPA"),
                    0.05
                ),
                Arguments.of(
                    SaleArticle(Article("Art1", 25.50, Category.Food, Country("ITA")), 1),
                    Country("ITA"),
                    0.0
                )
            )
    }
}