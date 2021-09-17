package com.example.providertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val word = intent.getSerializableExtra("data") as Word
        val txtWord = findViewById<TextView>(R.id.wordSearchActivity)
        val txtMeaning = findViewById<TextView>(R.id.meaningSearchActivity)
        val txtSample = findViewById<TextView>(R.id.sampleSearchActivity)
        txtWord.text = word.word
        txtMeaning.text = word.meaning
        txtSample.text = word.sample
    }
}