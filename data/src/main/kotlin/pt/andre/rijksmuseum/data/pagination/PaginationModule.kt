package pt.andre.rijksmuseum.data.pagination

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pt.andre.rijksmuseum.domain.pagination.PaginationManager

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class PaginationModule {

    @Binds
    abstract fun bindPaginationManager(default: DefaultPaginationManager): PaginationManager
}
