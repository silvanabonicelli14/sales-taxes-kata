package cgm.com.salestaxes.services

import cgm.com.salestaxes.entities.Article
import cgm.com.salestaxes.entities.Country
import cgm.com.salestaxes.interfaces.TaxService

class TaxCalculator: TaxService {
    override fun applyTax(article: Article, countryOfSale: Country): Double {
        return 0.05
    }
}