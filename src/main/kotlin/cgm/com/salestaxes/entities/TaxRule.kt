package cgm.com.salestaxes.entities

val listOfExemptions  = mutableListOf(Category.Book, Category.Food, Category.Medical)
const val localTax = 0.10
const val importationTax = 0.05

interface  Tax{
    fun applyTaxFor(taxRule: TaxRule)
}

interface TaxRule{
    fun taxForImported(tax : TaxImported)
    fun taxForCategory(tax: TaxCategory)
}


data class TaxImported(val article: Article, val countryForTax: Country) : Tax {
    override fun applyTaxFor(taxRule: TaxRule)  {
        taxRule.taxForImported(this)
    }
}

data class TaxCategory(val article: Article, val countryForTax: Country) :Tax {
    override fun applyTaxFor(taxRule: TaxRule) {
        taxRule.taxForCategory(this)
    }
 }

class UseTax {
    fun getTax(tax:Tax): Double {
        var taxVal = 0.0

        tax.applyTaxFor( object: TaxRule{
            override fun taxForImported(tax: TaxImported) {
                taxVal += if (tax.article.country.name != tax.countryForTax.name) { importationTax} else 0.0
            }
            override fun taxForCategory(tax: TaxCategory) {
                taxVal += if (listOfExemptions.contains(tax.article.category)) { 0.0} else localTax
            }
        })
        return taxVal
    }
}