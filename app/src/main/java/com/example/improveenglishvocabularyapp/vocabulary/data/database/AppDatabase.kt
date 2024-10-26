package com.example.improveenglishvocabularyapp.vocabulary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.improveenglishvocabularyapp.vocabulary.data.dao.WordDao
import com.example.improveenglishvocabularyapp.vocabulary.data.entity.WordDB
import com.example.improveenglishvocabularyapp.vocabulary.data.entity.WordToRemember

@Database(entities =[WordDB::class, WordToRemember::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao
}