package com.gumu.bookwormapp.di

import com.gumu.bookwormapp.domain.repository.AuthRepository
import com.gumu.bookwormapp.domain.usecase.ValidateEmail
import com.gumu.bookwormapp.domain.usecase.ValidatePassword
import com.gumu.bookwormapp.domain.usecase.ValidateSignUp
import com.gumu.bookwormapp.domain.usecase.auth.CheckUserSessionUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SaveNewUserDataUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SignInUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SignOutUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    // Validation use cases
    @ViewModelScoped
    @Provides
    fun provideValidateEmailUseCase() = ValidateEmail()

    @ViewModelScoped
    @Provides
    fun provideValidatePasswordUseCase() = ValidatePassword()

    @ViewModelScoped
    @Provides
    fun provideValidateSignUpUseCase() = ValidateSignUp()

    // Auth use cases
    @ViewModelScoped
    @Provides
    fun provideSignInUseCase(repository: AuthRepository) = SignInUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSignUpUseCase(repository: AuthRepository) = SignUpUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSignOutUseCase(repository: AuthRepository) = SignOutUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideCheckUserSessionUseCase(repository: AuthRepository) = CheckUserSessionUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSaveNewUserDataUseCase(repository: AuthRepository) = SaveNewUserDataUseCase(repository)
}
