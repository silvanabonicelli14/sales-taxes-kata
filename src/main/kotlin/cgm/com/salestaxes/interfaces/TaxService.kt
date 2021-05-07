package cgm.com.salestaxes.interfaces

import cgm.com.salestaxes.entities.Article
import cgm.com.salestaxes.entities.Country

interface TaxService {
    fun applyTax(article: Article, countryOfSale: Country): Double
}