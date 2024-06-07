package dev.taleroangel.timetonic.infrastructure.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.taleroangel.timetonic.BuildConfig
import dev.taleroangel.timetonic.infrastructure.https.TimetonicHttpProperties

/**
 * Provide the properties for the timetonic API services
 */
@Module
@InstallIn(SingletonComponent::class)
object TimetonicModule {
    @Provides
    fun provideTimetonicHttpProperties(): TimetonicHttpProperties =
        TimetonicHttpProperties(BuildConfig.TIMETONIC_API_URL, BuildConfig.TIMETONIC_API_VER)
}