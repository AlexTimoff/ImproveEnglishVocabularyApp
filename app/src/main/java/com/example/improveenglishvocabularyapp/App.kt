package com.example.improveenglishvocabularyapp

import android.app.Application
import androidx.room.Room
import com.example.improveenglishvocabularyapp.vocabulary.data.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "en-ru dictionary db"
        ).createFromAsset("databases/en_ru_words_database.db")
            .build()
    }

    companion object{
        lateinit var db: AppDatabase
    }
}