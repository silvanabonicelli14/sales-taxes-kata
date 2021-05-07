package cgm.com.salestaxes.entities

class SaleArticle(
    val article: Article,
    val quantity: Int
){
    var taxedPrice: Double = 0.0
}