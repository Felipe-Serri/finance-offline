package com.felipeserri.financeoffline.di

import com.felipeserri.financeoffline.data.repository.CategoryRepositoryImpl
import com.felipeserri.financeoffline.data.repository.TransactionRepositoryImpl
import com.felipeserri.financeoffline.domain.repository.CategoryRepository
import com.felipeserri.financeoffline.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        impl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository
}