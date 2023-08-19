package com.gumu.bookwormapp.di

import com.gumu.bookwormapp.domain.usecase.auth.CheckUserSessionUseCase
import com.gumu.bookwormapp.domain.usecase.auth.CheckUserSessionUseCaseImpl
import com.gumu.bookwormapp.domain.usecase.auth.SaveNewUserDataUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SaveNewUserDataUseCaseImpl
import com.gumu.bookwormapp.domain.usecase.auth.SignInUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SignInUseCaseImpl
import com.gumu.bookwormapp.domain.usecase.auth.SignOutUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SignOutUseCaseImpl
import com.gumu.bookwormapp.domain.usecase.auth.SignUpUseCase
import com.gumu.bookwormapp.domain.usecase.auth.SignUpUseCaseImpl
import com.gumu.bookwormapp.domain.usecase.bookstats.AddBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.AddBookStatsUseCaseImpl
import com.gumu.bookwormapp.domain.usecase.bookstats.FindBooksUseCaseImpl
import com.gumu.bookwormapp.domain.usecase.bookstats.GetAllBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.GetAllBookStatsUseCaseImpl
import com.gumu.bookwormapp.domain.usecase.bookstats.GetBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.GetBookStatsUseCaseImpl
import com.gumu.bookwormapp.domain.usecase.bookstats.UpdateBookStatsUseCase
import com.gumu.bookwormapp.domain.usecase.bookstats.UpdateBookStatsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCasesModule {
    @Binds
    @ViewModelScoped
    abstract fun bindSignInUseCase(
        signInUseCaseImpl: SignInUseCaseImpl
    ): SignInUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSignOutUseCase(
        signOutUseCaseImpl: SignOutUseCaseImpl
    ): SignOutUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSignUpUseCase(
        signUpUseCaseImpl: SignUpUseCaseImpl
    ): SignUpUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSaveNewUserDataUseCase(
        saveNewUserDataUseCaseImpl: SaveNewUserDataUseCaseImpl
    ): SaveNewUserDataUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindCheckUserSessionUseCase(
        checkUserSessionUseCaseImpl: CheckUserSessionUseCaseImpl
    ): CheckUserSessionUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindFindBooksUseCase(
        findBooksUseCaseImpl: FindBooksUseCaseImpl
    ): FindBooksUseCaseImpl

    @Binds
    @ViewModelScoped
    abstract fun bindAddBookStatsUseCase(
        addBookStatsUseCaseImpl: AddBookStatsUseCaseImpl
    ): AddBookStatsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetBookStatsUseCase(
        getBookStatsUseCaseImpl: GetBookStatsUseCaseImpl
    ): GetBookStatsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindUpdateBookStatsUseCase(
        updateBookStatsUseCaseImpl: UpdateBookStatsUseCaseImpl
    ): UpdateBookStatsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetAllBookStatsUseCase(
        getAllBookStatsUseCaseImpl: GetAllBookStatsUseCaseImpl
    ): GetAllBookStatsUseCase
}
