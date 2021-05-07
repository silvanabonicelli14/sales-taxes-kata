package cgm.com.salestaxes.entities

sealed class Article {
    data class ArticleByPiece(
        val code: String,
        val price: Double,
        val category: Category,
        val country: Country
    ) : Article()

    data class ArticleByShelf(
        val code: String,
        val price: Double,
        val category: Category,
        val country: Country
    ) : Article()
}