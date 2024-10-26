package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.improveenglishvocabularyapp.vocabulary.data.dao.WordDao
import javax.inject.Inject

class QuestionViewModelFactory @Inject constructor(
    private val wordDao: WordDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestionViewModel(wordDao) as T
    }
}