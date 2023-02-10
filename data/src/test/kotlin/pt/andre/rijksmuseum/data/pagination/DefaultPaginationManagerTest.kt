package pt.andre.rijksmuseum.data.pagination

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

private const val MAX_PAGES = 20

internal class DefaultPaginationManagerTest {

    @Test
    fun `when is processing request shouldRequestNextPage() returns false`() {
        val manager = DefaultPaginationManager().apply {
            isProcessingRequest = true
        }

        // When index has not reached next page threshold
        assertFalse(manager.shouldRequestNextPage(index = 0, total = MAX_PAGES))

        // When index has reached next page threshold
        assertFalse(manager.shouldRequestNextPage(index = 19, total = MAX_PAGES))
    }

    @Test
    fun `when is not processing request shouldRequestNextPage() returns false when index hasnt reached the threshold`() {
        val manager = DefaultPaginationManager()

        (0 until MAX_PAGES / 2).forEach {
            assertFalse(manager.shouldRequestNextPage(index = it, total = MAX_PAGES))
        }
    }

    @Test
    fun `when is not processing request shouldRequestNextPage() returns true when index has reached the threshold`() {
        val manager = DefaultPaginationManager()
        (MAX_PAGES / 2..MAX_PAGES).forEach {
            assertTrue(manager.shouldRequestNextPage(index = it, total = MAX_PAGES))
        }
    }

    @Test
    fun `nextPageOffset returns correctly`() {
        val manager = DefaultPaginationManager()

        assertEquals(MAX_PAGES, manager.nextPageOffset(index = 0, itemsPerPage = MAX_PAGES))
        assertEquals(MAX_PAGES * 2, manager.nextPageOffset(index = MAX_PAGES, itemsPerPage = MAX_PAGES))
    }
}
