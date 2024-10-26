package com.example.improveenglishvocabularyapp.vocabulary.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.improveenglishvocabularyapp.vocabulary.data.entity.WordDB
import com.example.improveenglishvocabularyapp.vocabulary.data.entity.WordToRemember

@Dao
interface WordDao {
    @Query("SELECT * FROM vocabulary ORDER BY Random() LIMIT (:limit)")
    suspend fun getRandomWords(limit: Int): List<WordDB>

    @Query("SELECT * FROM allWordsToRemember ORDER BY english ASC")
    suspend fun getAllWordsToRemember(): List<WordToRemember>

    @Query("SELECT * FROM allWordsToRemember where id=:id")
    suspend fun isWordInDatabase(id: Int): WordToRemember?

    @Insert(entity = WordToRemember::class )
    suspend fun insert(word: WordToRemember)

    @Delete(entity = WordToRemember::class )
    suspend fun delete(word: WordToRemember)
}