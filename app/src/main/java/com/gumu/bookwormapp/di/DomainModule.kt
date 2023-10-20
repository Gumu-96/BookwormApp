package com.gumu.bookwormapp.di

import com.gumu.bookwormapp.domain.usecase.ValidateEmail
import com.gumu.bookwormapp.domain.usecase.ValidateName
import com.gumu.bookwormapp.domain.usecase.ValidatePassword
import com.gumu.bookwormapp.domain.usecase.ValidateRepeatedPassword
import com.gumu.bookwormapp.domain.usecase.ValidateSignUp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @ViewModelScoped
    @Provides
    fun provideValidateEmailUseCase() = ValidateEmail()

    @ViewModelScoped
    @Provides
    fun provideValidatePasswordUseCase() = ValidatePassword()

    @ViewModelScoped
    @Provides
    fun provideValidateNameUseCase() = ValidateName()

    @ViewModelScoped
    @Provides
    fun provideValidateRepeatedPasswordUseCase() = ValidateRepeatedPassword()

    @ViewModelScoped
    @Provides
    fun provideValidateSignUpUseCase() = ValidateSignUp()
}
