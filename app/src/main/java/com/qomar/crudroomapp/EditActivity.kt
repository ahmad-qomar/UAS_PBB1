package com.qomar.crudroomapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.qomar.crudroomapp.databinding.ActivityEditBinding
import com.qomar.crudroomapp.room.Constant
import com.qomar.crudroomapp.room.Note
import com.qomar.crudroomapp.room.NoteDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private infix fun <ConstraintLayout> ViewBinding.setContentView(root: ConstraintLayout) {
}

class EditActivity : AppCompatActivity() {
    val db by lazy { NoteDB(this)}
    private var noteId: Int = 0
    private lateinit var binding : ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListener()
    }
    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE -> {
                binding.buttonUpdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                binding.buttonSave.visibility = View.GONE
                binding.buttonUpdate.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                binding.buttonSave.visibility = View.GONE
                getNote()
            }
        }
    }
    private fun setupListener() {
        binding.buttonSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addNote(
                    Note(0, binding.editTitle.text.toString(),
                        binding.editNote.text.toString(),
                        binding.editPenulis.text.toString(),
                        binding.editPenerbit.text.toString(),
                        binding.editTerbit.text.toString())
                )
                finish()
            }
        }
        binding.buttonUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().updateNote(
                    Note(noteId, binding.editTitle.text.toString(),
                        binding.editNote.text.toString(),
                        binding.editPenulis.text.toString(),
                        binding.editPenerbit.text.toString(),
                        binding.editTerbit.text.toString())
                )
                finish()
            }
        }
    }
    fun getNote(){
        noteId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNote(noteId)[0]
            binding.editTitle.setText(notes.title)
            binding.editNote.setText(notes.note)
            binding.editPenulis.setText(notes.penulis)
            binding.editPenerbit.setText(notes.penerbit)
            binding.editTerbit.setText(notes.terbit)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
