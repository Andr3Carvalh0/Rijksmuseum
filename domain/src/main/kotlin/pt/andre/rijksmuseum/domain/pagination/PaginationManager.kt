package pt.andre.rijksmuseum.domain.pagination

const val ITEMS_PER_PAGE = 20

interface PaginationManager {
    var isProcessingRequest: Boolean

    fun shouldRequestNextPage(index: Int, total: Int = ITEMS_PER_PAGE): Boolean

    fun nextPageOffset(index: Int, itemsPerPage: Int = ITEMS_PER_PAGE): Int
}
