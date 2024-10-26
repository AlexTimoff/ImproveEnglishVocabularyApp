package com.example.improveenglishvocabularyapp.vocabulary.presentation.englishVocabularyTest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.improveenglishvocabularyapp.R
import com.example.improveenglishvocabularyapp.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkYourself.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentStart_to_fragmentRegistration)
        }

        binding.rememberWords.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentStart_to_fragmentWordsToRemember)
        }
    }
}