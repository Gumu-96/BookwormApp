package com.gumu.bookwormapp.di

import com.gumu.bookwormapp.domain.repository.AuthRepository
import com.gumu.bookwormapp.domain.repository.BookStatsRepository
import com.gumu.bookwormapp.domain.repository.BooksRepository
import com.gumu.bookwormapp.domain.usecase.ValidateEmail
import com.gumu.bookwormapp.domain.usecase.ValidatePassword
import com.gumu.bookwormapp.domain.usecase.ValidateSignUp
import com.gumu.bookwormapp.domain.usecase.auth.CheckUserSessionUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SaveNewUserDataUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SignInUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SignOutUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SignUpUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.AddBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.DeleteBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.FindBooksUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.GetAllBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.GetBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.UpdateBookStatsUseCase
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

    // Books use cases
    @ViewModelScoped
    @Provides
    fun provideFindBooksUseCase(repository: BooksRepository) = FindBooksUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideAddBookStatsUseCase(repository: BookStatsRepository) = AddBookStatsUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideUpdateBookStatsUseCase(repository: BookStatsRepository) = UpdateBookStatsUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideDeleteBookStatsUseCase(repository: BookStatsRepository) = DeleteBookStatsUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetAllBookStatsUseCase(repository: BookStatsRepository) = GetAllBookStatsUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetBookStatsUseCase(repository: BookStatsRepository) = GetBookStatsUseCase(repository)
}
