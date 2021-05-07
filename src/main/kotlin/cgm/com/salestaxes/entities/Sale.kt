package cgm.com.salestaxes.entities

class Sale(val country: Country) {

    val salesList = mutableListOf<SaleArticle>()

    fun addSales(article: Article, quantity: Int) {
        salesList.add(SaleArticle(article, quantity))
    }
}