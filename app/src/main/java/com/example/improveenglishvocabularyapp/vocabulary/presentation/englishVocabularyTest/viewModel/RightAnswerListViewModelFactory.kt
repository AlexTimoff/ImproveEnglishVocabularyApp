package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.improveenglishvocabularyapp.vocabulary.data.dao.WordDao
import javax.inject.Inject


class RightAnswerListViewModelFactory @Inject constructor(
    private val wordDao: WordDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RightAnswersListViewModel(wordDao) as T
    }
}