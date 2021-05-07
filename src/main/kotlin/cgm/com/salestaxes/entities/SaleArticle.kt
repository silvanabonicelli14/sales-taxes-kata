package cgm.com.salestaxes.entities

class SaleArticle(
    val article: Article,
    val quantity: Int,
    val soldByShelf: Boolean
){
    var taxedPrice: Double = 0.0
}