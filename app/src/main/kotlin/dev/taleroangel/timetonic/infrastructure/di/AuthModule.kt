package dev.taleroangel.timetonic.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.taleroangel.timetonic.domain.repository.IAuthRepository
import dev.taleroangel.timetonic.domain.service.IAuthService
import dev.taleroangel.timetonic.infrastructure.https.HttpAuthServiceImpl
import dev.taleroangel.timetonic.infrastructure.persistance.AuthDataStore

/**
 * Dependency injection for the [IAuthService]
 */
@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    /**
     * Provide the [IAuthService] implementation
     */
    @Binds
    fun bindAuthService(
        authServiceImpl: HttpAuthServiceImpl
    ): IAuthService

    /**
     * Provide the [IAuthRepository] implementation
     */
    @Binds
    fun bindAuthRepository(
        authRepositoryImpl: AuthDataStore
    ): IAuthRepository
}