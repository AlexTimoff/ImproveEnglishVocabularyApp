package com.example.improveenglishvocabularyapp.vocabulary.presentation.wordsToRemember.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.improveenglishvocabularyapp.databinding.FragmentWordsToRememberBinding
import com.example.improveenglishvocabularyapp.vocabulary.data.entity.WordToRemember
import com.example.improveenglishvocabularyapp.vocabulary.presentation.wordsToRemember.state.WordsToRememberState
import com.example.improveenglishvocabularyapp.vocabulary.presentation.wordsToRemember.adapter.WordsToRememberAdapter
import com.example.improveenglishvocabularyapp.vocabulary.presentation.wordsToRemember.viewModels.WordsToRememberViewModel
import com.example.improveenglishvocabularyapp.vocabulary.presentation.wordsToRemember.viewModels.WordsToRememberViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WordsToRememberFragment : Fragment() {

    private var _binding: FragmentWordsToRememberBinding? = null
    private val binding get() = _binding!!
    private var allWords: List<WordToRemember> = arrayListOf()

    @Inject
    lateinit var wordsToRememberViewModelFactory: WordsToRememberViewModelFactory
    private val wordsToRememberViewModel: WordsToRememberViewModel by viewModels {
        wordsToRememberViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordsToRememberBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wordsToRememberViewModel.getAllWordsToRemember()
        binding.recyclerWordsToRememberView.adapter = WordsToRememberAdapter() {
            position: Int -> onClick(position)
        }
        binding.recyclerWordsToRememberView.layoutManager =
            GridLayoutManager(
                requireContext(), 1,
                RecyclerView.VERTICAL,
                false
            )
        observeState()
    }

    private fun onClick(position: Int) {
        val item = allWords[position]
        Log.d("Word_item", "$position|${item.english}")
        wordsToRememberViewModel.removeWordFromDatabase(item)
        wordsToRememberViewModel.getAllWordsToRemember()
    }

    private fun observeState() {
        lifecycleScope.launch {
            wordsToRememberViewModel.state.collect { state ->
                when (state) {

                    is WordsToRememberState.Loading -> {
                        with(binding) {
                            loader.visibility = View.VISIBLE
                        }
                    }

                    is WordsToRememberState.AllWordsToRemember -> {
                        with(binding) {
                            loader.visibility = View.GONE
                            allWords = state.allWordsToRemember
                            if (allWords.isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "Database is empty",
                                    Toast.LENGTH_SHORT
                                ).show()
                                showAllWords(allWords)
                            } else {
                                showAllWords(allWords)
                            }
                        }
                    }

                    is WordsToRememberState.Error -> {
                        Toast.makeText(requireContext(), "Something is wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun showAllWords(allWords: List<WordToRemember>) {
        (binding.recyclerWordsToRememberView.adapter as WordsToRememberAdapter).setData(allWords)
    }
}