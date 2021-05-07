package cgm.com.salestaxes.services

import cgm.com.salestaxes.entities.*
import cgm.com.salestaxes.helpers.roundedDouble
import cgm.com.salestaxes.interfaces.TaxService

class TaxCalculator: TaxService {
    override fun applyTax(article: Article, countryOfSale: Country): Double {
        var tax = 0.0
        tax.apply{
            tax += UseTax().getTax(TaxCategory(article,countryOfSale))
            tax += UseTax().getTax(TaxImported(article,countryOfSale))
        }
        return tax.roundedDouble
    }
}