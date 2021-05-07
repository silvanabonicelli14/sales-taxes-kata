package cgm.com.salestaxes.entities

data class Article(
    val code: String,
    val price: Double,
    val category: Category,
    val country: Country
)