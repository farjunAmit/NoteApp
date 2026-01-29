package com.example.noteapp.data.module


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.firebase.auth.FirebaseAuth

@Module
@InstallIn(SingletonComponent::class)
object ObjectModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}