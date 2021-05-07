package cgm.com.salestaxes.services

import cgm.com.salestaxes.entities.Article
import cgm.com.salestaxes.entities.Category
import cgm.com.salestaxes.entities.Country
import cgm.com.salestaxes.interfaces.TaxService

val listOfExemptions  = mutableListOf(Category.Book,Category.Food,Category.Medical)
const val localTax = 0.10
const val importationTax = 0.05

class TaxCalculator: TaxService {
    override fun applyTax(article: Article, countryOfSale: Country): Double {
        var tax: Double  = localTax

        when {
            listOfExemptions.contains(article.category) -> {
                return 0.0
            }
            article.country.name != countryOfSale.name -> {
                tax += importationTax
            }
        }
        return tax
    }
}