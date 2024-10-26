package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.improveenglishvocabularyapp.databinding.FragmentRegistrationBinding
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel.QuestionViewModel
import com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.viewModel.QuestionViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var questionViewModelFactory: QuestionViewModelFactory
    private val viewModel: QuestionViewModel by activityViewModels {
        questionViewModelFactory
    }

    private var progressValue: Int = 0
    private var editName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editName.addTextChangedListener { text ->
            if (text != null) {
                editName = text.toString()
            }
            checkBTnContinue()
        }

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress != 0) {
                    progressValue = progress
                }
                checkBTnContinue()
                binding.numberQuestions.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.btnContinue.setOnClickListener {
            setDefaultValues()
            val action =
                RegistrationFragmentDirections.actionFragmentRegistrationToFragmentQuestion()
            findNavController().navigate(action)
        }
    }

    private fun checkBTnContinue() {
        if (binding.editName.text.isNotEmpty() && binding.seekbar.progress != 0) {
            binding.btnContinue.isEnabled = true
        } else {
            binding.btnContinue.isEnabled = false
        }
    }

    private fun setDefaultValues() {
        viewModel.rememberName(editName!!)
        viewModel.rememberNumberOfQuestions(progressValue)
        viewModel.getAllQuestions(progressValue)
        viewModel.showCurrentQuestion(0)
        viewModel.isEnableSubmit(false)
        viewModel.isEnableContinue(false)
        viewModel.countCorrectAnswers(0)
    }
}