package dev.taleroangel.timetonic.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.taleroangel.timetonic.domain.service.IAuthService
import dev.taleroangel.timetonic.domain.service.IBookService
import dev.taleroangel.timetonic.infrastructure.https.HttpAuthServiceImpl
import dev.taleroangel.timetonic.infrastructure.https.HttpBookServiceImpl

/**
 * Dependency injection for the services provided
 */
@Module
@InstallIn(SingletonComponent::class)
interface ServicesModule {
    /**
     * Provide the [IAuthService] implementation
     */
    @Binds
    fun bindAuthService(
        authServiceImpl: HttpAuthServiceImpl
    ): IAuthService

    /**
     * Provide the [IBookService] implementation
     */
    @Binds
    fun bindBookService(
        bookServiceImpl: HttpBookServiceImpl
    ): IBookService
}