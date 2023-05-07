package com.gumu.bookwormapp.di

import com.gumu.bookwormapp.domain.usecase.ValidateEmail
import com.gumu.bookwormapp.domain.usecase.ValidateName
import com.gumu.bookwormapp.domain.usecase.ValidatePassword
import com.gumu.bookwormapp.domain.usecase.ValidateRepeatedPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Singleton
    @Provides
    fun provideValidateEmailUseCase() = ValidateEmail()

    @Singleton
    @Provides
    fun provideValidatePasswordUseCase() = ValidatePassword()

    @Singleton
    @Provides
    fun provideValidateNameUseCase() = ValidateName()

    @Singleton
    @Provides
    fun provideValidateRepeatedPasswordUseCase() = ValidateRepeatedPassword()
}
