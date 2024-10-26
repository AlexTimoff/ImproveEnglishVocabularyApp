package com.example.improveenglishvocabularyapp.vocabulary.presentation.wordsToRemember.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.improveenglishvocabularyapp.vocabulary.data.dao.WordDao
import javax.inject.Inject

class WordsToRememberViewModelFactory @Inject constructor(
    private val wordDao: WordDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WordsToRememberViewModel(wordDao) as T
    }
}