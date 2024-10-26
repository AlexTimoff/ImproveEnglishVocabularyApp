package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.improveenglishvocabularyapp.R
import com.example.improveenglishvocabularyapp.vocabulary.domain.dataunits.WordItem
import com.example.improveenglishvocabularyapp.databinding.FragmentRightAnswersListBinding
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.adapter.RightAnswersAdapter
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.state.RightAnswersState
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel.QuestionViewModel
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel.QuestionViewModelFactory
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel.RightAnswerListViewModelFactory
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel.RightAnswersListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RightAnswersListFragment : Fragment() {

    private var _binding: FragmentRightAnswersListBinding? = null
    private val binding get() = _binding!!
    private var allRightAnswers: List<WordItem> = listOf()

    @Inject
    lateinit var questionViewModelFactory: QuestionViewModelFactory
    private val viewModel: QuestionViewModel by activityViewModels {
        questionViewModelFactory
    }

    @Inject
    lateinit var rightAnswerListViewModelFactory: RightAnswerListViewModelFactory
    private val rightAnswersListViewModel: RightAnswersListViewModel by viewModels {
        rightAnswerListViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRightAnswersListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rightAnswersListViewModel.checkRightAnswersIsFavourite(viewModel.allQuestions.value!!)
        binding.recyclerView.adapter = RightAnswersAdapter(
            onAddOrRemoveWordsToRememberDatabase = { position ->
                onClick(position)
            }
        )

        binding.recyclerView.layoutManager =
            GridLayoutManager(
                requireContext(), 1,
                RecyclerView.VERTICAL,
                false
            )

        observeState()

        binding.buttonIntoDatabase.setOnClickListener {
            val action =
                RightAnswersListFragmentDirections.actionFragmentRightAnswersListToFragmentWordsToRemember()
            findNavController().navigate(action)
        }

        binding.testAgain.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentRegistration,false)
        }
    }

    private fun onClick(position: Int) {
            if (!allRightAnswers[position].isFavourite) {
                rightAnswersListViewModel.addWordToDatabase(allRightAnswers[position])
                allRightAnswers[position].isFavourite = true
                Log.d("Word_item", "$position|${allRightAnswers[position]}")
            } else {
                rightAnswersListViewModel.removeWordFromDatabase(allRightAnswers[position])
                allRightAnswers[position].isFavourite = false
                Log.d("Word_item", "$position|${allRightAnswers[position]}")
            }
        showAnswers()
    }

    private fun observeState() {
        lifecycleScope.launch {
            rightAnswersListViewModel.state.collect { state ->
                when (state) {

                    is RightAnswersState.Loading -> {
                    }

                    is RightAnswersState.AllCorrectAnswers -> {
                            allRightAnswers = state.allCorrectAnswers
                            if (allRightAnswers.isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "List of right answers is empty",
                                    Toast.LENGTH_SHORT
                                ).show()
                                showAnswers()
                            } else {
                                showAnswers()
                            }
                    }

                    is RightAnswersState.Error -> {
                        Toast.makeText(requireContext(), "Something is wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun showAnswers() {
            Log.d("all_right_answers", "$allRightAnswers")
            (binding.recyclerView.adapter as RightAnswersAdapter).setData(allRightAnswers)
    }
}