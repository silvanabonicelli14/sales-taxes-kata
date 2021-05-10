package cgm.com.salestaxes.entities

val listOfExemptions  = mutableListOf(Category.Book, Category.Food, Category.Medical)
const val localTax = 0.10
const val importationTax = 0.05

interface  Tax{
    fun applyTaxFor(taxRule: TaxRule)
}

interface TaxRule{
    fun tax(tax : TaxImported)
    fun tax(tax: TaxCategory)
}


data class TaxImported(val article: Article, val countryForTax: Country) : Tax {
    override fun applyTaxFor(taxRule: TaxRule)  {
        taxRule.tax(this)
    }
}

data class TaxCategory(val article: Article, val countryForTax: Country) :Tax {
    override fun applyTaxFor(taxRule: TaxRule) {
        taxRule.tax(this)
    }
 }

class UseTax {
    fun getTax(tax:Tax): Double {
        var taxVal = 0.0

        tax.applyTaxFor( object: TaxRule{
            override fun tax(tax: TaxImported) {
                taxVal += if (tax.article.country.name != tax.countryForTax.name) { importationTax} else 0.0
            }
            override fun tax(tax: TaxCategory) {
                taxVal += if (listOfExemptions.contains(tax.article.category)) { 0.0} else localTax
            }
        })
        return taxVal
    }
}