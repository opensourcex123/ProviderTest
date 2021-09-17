package com.example.providertest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.contentValuesOf

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addData = findViewById<Button>(R.id.addData)
        val deleteData = findViewById<Button>(R.id.deleteData)
        val updateData = findViewById<Button>(R.id.updateData)
        val searchData = findViewById<Button>(R.id.searchData)
        addData.setOnClickListener {
            InsertDialog()
        }

        deleteData.setOnClickListener {
            DeleteDialog()
        }

        updateData.setOnClickListener {
            UpdateDialog()
        }

        searchData.setOnClickListener {
            SearchDialog()
        }
    }

    private fun InsertDialog() {
        val tableLayout = layoutInflater.inflate(R.layout.insert, null) as TableLayout
        AlertDialog.Builder(this)
            .setTitle("新增单词")
            .setView(tableLayout)
            .setPositiveButton("确定") { dialogInterface, i ->
                val strWord =
                    (tableLayout.findViewById<View>(R.id.txtWord) as EditText).text.toString()
                val strMeaning =
                    (tableLayout.findViewById<View>(R.id.txtMeaning) as EditText).text.toString()
                val strSample =
                    (tableLayout.findViewById<View>(R.id.txtSample) as EditText).text.toString()
                val uri = Uri.parse("content://com.example.kotlinwordbook.provider/word")
                val values = contentValuesOf("word" to strWord, "meaning" to strMeaning, "sample" to strSample)
                contentResolver.insert(uri, values)
            }
            .setNegativeButton(
                "取消"
            ) { dialogInterface, i -> }
            .create()
            .show()
    }

    private fun DeleteDialog() {
        val tableLayout = layoutInflater.inflate(R.layout.delete, null) as TableLayout
        AlertDialog.Builder(this)
            .setTitle("删除单词")
            .setView(tableLayout)
            .setPositiveButton("确定") { dialogInterface, i ->
                val strWord =
                    (tableLayout.findViewById<View>(R.id.txtWordDelete) as EditText).text.toString()
                val uri = Uri.parse("content://com.example.kotlinwordbook.provider/word")
                contentResolver.delete(uri, "word=?", arrayOf(strWord))
            }
            .setNegativeButton(
                "取消"
            ) { dialogInterface, i -> }
            .create()
            .show()
    }

    private fun UpdateDialog() {
        val tableLayout = layoutInflater.inflate(R.layout.insert, null) as TableLayout
        AlertDialog.Builder(this)
            .setTitle("修改单词")
            .setView(tableLayout)
            .setPositiveButton("确定") { dialogInterface, i ->
                val strNewWord =
                    (tableLayout.findViewById<View>(R.id.txtWord) as EditText).text.toString()
                val strNewMeaning =
                    (tableLayout.findViewById<View>(R.id.txtMeaning) as EditText).text.toString()
                val strNewSample =
                    (tableLayout.findViewById<View>(R.id.txtSample) as EditText).text.toString()
                val uri = Uri.parse("content://com.example.kotlinwordbook.provider/word")
                val values = contentValuesOf("word" to strNewWord, "meaning" to strNewMeaning, "sample" to strNewSample)
                contentResolver.update(uri, values, "word=?", arrayOf(strNewWord))
            }
            .setNegativeButton(
                "取消"
            ) { dialogInterface, i -> }
            .create()
            .show()
    }

    private fun SearchDialog() {
        var items:Word ?= null
        val tableLayout = layoutInflater.inflate(R.layout.search, null) as TableLayout
        AlertDialog.Builder(this)
            .setTitle("查找单词")
            .setView(tableLayout)
            .setPositiveButton("确定") { dialogInterface, i ->
                val txtSearchWord =
                    (tableLayout.findViewById<View>(R.id.txtSearchWord) as EditText).text.toString()
                val uri = Uri.parse("content://com.example.kotlinwordbook.provider/word")
                val cursor = contentResolver.query(uri, null, null, null, null)
                if (cursor?.moveToNext() == true) {
                    val word = cursor.getString(cursor.getColumnIndex("word"))
                    val meaning = cursor.getString(cursor.getColumnIndex("meaning"))
                    val sample = cursor.getString(cursor.getColumnIndex("sample"))
                    items = Word(word, meaning, sample)
                }
                if (items != null) {
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("data", items)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "没有找到", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton(
                "取消"
            ) { dialogInterface, i -> }
            .create()
            .show()
    }
}