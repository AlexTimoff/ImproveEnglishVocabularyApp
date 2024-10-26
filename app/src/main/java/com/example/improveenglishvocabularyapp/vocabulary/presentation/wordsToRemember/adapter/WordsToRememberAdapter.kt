package com.example.improveenglishvocabularyapp.vocabulary.presentation.wordsToRemember.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.improveenglishvocabularyapp.databinding.WordToRememberBinding
import com.example.improveenglishvocabularyapp.vocabulary.data.entity.WordToRemember

class WordsToRememberAdapter(
    private val onRemoveWordsToRememberDatabase: (position:Int) -> Unit
): RecyclerView.Adapter<WordsToRememberViewHolder>() {

    private var data : List<WordToRemember> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsToRememberViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding = WordToRememberBinding.inflate(inflater,parent,false)
        return WordsToRememberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordsToRememberViewHolder, position: Int) {
        val word = data.getOrNull(position)
        with(holder.binding) {
            wordNumber.text = (position+1).toString()
            wordEnglish.text="${word?.english.toString()} - "
            wordRussian.text=word?.russian.toString()
           removeFromDatabaseBtn.setOnClickListener { onRemoveWordsToRememberDatabase(position) }
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<WordToRemember>){
        this.data = data
        notifyDataSetChanged()
    }
}