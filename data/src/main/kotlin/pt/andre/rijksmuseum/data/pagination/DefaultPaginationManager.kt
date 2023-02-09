package pt.andre.rijksmuseum.data.pagination

import pt.andre.rijksmuseum.domain.pagination.ITEMS_PER_PAGE
import pt.andre.rijksmuseum.domain.pagination.PaginationManager
import javax.inject.Inject

internal class DefaultPaginationManager @Inject constructor() : PaginationManager {

    override var isProcessingRequest: Boolean = false

    override fun shouldRequestNextPage(index: Int, total: Int): Boolean {
        if (isProcessingRequest) return false

        return index >= (total - (ITEMS_PER_PAGE / 2))
    }

    override fun nextPageOffset(index: Int, itemsPerPage: Int): Int =
        itemsPerPage + (itemsPerPage * (index / itemsPerPage))
}
