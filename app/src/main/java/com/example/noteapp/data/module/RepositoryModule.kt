package com.example.noteapp.data.module

import com.example.noteapp.data.AuthDefaultRepository
import com.example.noteapp.data.AuthRepository
import com.example.noteapp.data.NoteDefaultRepository
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.data.UserLocationDefaultRepository
import com.example.noteapp.data.UserLocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNoteRepository(
        noteRepository : NoteDefaultRepository
    ): NoteRepository

    @Binds
    abstract fun bindUserLocationRepository(
        userLocationRepository : UserLocationDefaultRepository
    ): UserLocationRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepository : AuthDefaultRepository
    ): AuthRepository
}