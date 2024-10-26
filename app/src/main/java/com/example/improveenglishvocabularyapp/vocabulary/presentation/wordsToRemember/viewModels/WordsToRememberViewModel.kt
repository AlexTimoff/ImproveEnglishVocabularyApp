package com.example.improveenglishvocabularyapp.vocabulary.presentation.wordsToRemember.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.improveenglishvocabularyapp.vocabulary.data.dao.WordDao
import com.example.improveenglishvocabularyapp.vocabulary.data.entity.WordToRemember
import com.example.improveenglishvocabularyapp.vocabulary.presentation.wordsToRemember.state.WordsToRememberState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WordsToRememberViewModel @Inject constructor(
    private val wordDao: WordDao
) : ViewModel() {

    private val _state = MutableStateFlow<WordsToRememberState>(WordsToRememberState.Loading)
    val state = _state.asStateFlow()

    fun getAllWordsToRemember() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = WordsToRememberState.Loading
            try {
                delay(1_000)
                val words=wordDao.getAllWordsToRemember()
                _state.value = WordsToRememberState.AllWordsToRemember(words)
            } catch (e: Throwable) {
                _state.value = WordsToRememberState.Error(e.message.toString())
            }
        }
    }

    fun removeWordFromDatabase(word: WordToRemember) {
        viewModelScope.launch(Dispatchers.IO){
            val isWordInDB=wordDao.isWordInDatabase(word.id)
            if (isWordInDB!=null){
                wordDao.delete(word)
            }
        }
    }
}