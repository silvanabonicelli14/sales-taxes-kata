package cgm.com.salestaxes.entities

class Sale(val country: Country) {

    val salesList = mutableListOf<SaleArticle>()

    fun addSales(article: Article, quantity: Int, soldByShelf: Boolean) {
        salesList.add(SaleArticle(article, quantity,soldByShelf))
    }
}