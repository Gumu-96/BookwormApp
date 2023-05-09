package com.gumu.bookwormapp.di

import com.gumu.bookwormapp.data.remote.datasource.BooksRemoteDataSource
import com.gumu.bookwormapp.data.remote.datasource.BooksRemoteDataSourceImpl
import com.gumu.bookwormapp.data.repository.BooksRepositoryImpl
import com.gumu.bookwormapp.data.util.DispatchersProvider
import com.gumu.bookwormapp.data.util.DispatchersProviderImpl
import com.gumu.bookwormapp.domain.repository.BooksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindDispatchers(
        dispatchersProviderImpl: DispatchersProviderImpl
    ): DispatchersProvider

    @Singleton
    @Binds
    abstract fun bindBooksRemoteDataSource(
        booksRemoteDataSource: BooksRemoteDataSourceImpl
    ): BooksRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindBooksRepository(
        booksRepository: BooksRepositoryImpl
    ): BooksRepository
}
