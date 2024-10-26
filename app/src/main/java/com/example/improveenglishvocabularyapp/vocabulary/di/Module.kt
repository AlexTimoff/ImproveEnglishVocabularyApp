package com.example.improveenglishvocabularyapp.vocabulary.di

import com.example.improveenglishvocabularyapp.App
import com.example.improveenglishvocabularyapp.vocabulary.data.dao.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideWordDao(): WordDao{
        return App.db.wordDao()
    }
}