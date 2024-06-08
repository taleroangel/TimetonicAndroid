package dev.taleroangel.timetonic.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.taleroangel.timetonic.domain.repository.IAuthRepository
import dev.taleroangel.timetonic.infrastructure.persistance.AuthDataStore

/**
 * Dependency injection for repositories
 */
@Module
@InstallIn(SingletonComponent::class)
interface PersistenceModule {
    /**
     * Provide the [IAuthRepository] implementation
     */
    @Binds
    fun bindAuthRepository(
        authRepositoryImpl: AuthDataStore
    ): IAuthRepository
}