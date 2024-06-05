package dev.taleroangel.timetonic.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dev.taleroangel.timetonic.domain.service.IAuthService
import dev.taleroangel.timetonic.infrastructure.mock.MockAuthServiceImpl

/**
 * Dependency injection for the [IAuthService]
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class AuthModule {

    /**
     * Provide the [IAuthService] implementation
     */
    @Binds
    abstract fun bindAuthService(
        authServiceImpl: MockAuthServiceImpl
    ): IAuthService
}