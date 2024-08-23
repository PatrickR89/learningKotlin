package com.patrickr.roomapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.patrickr.roomapp.activities.AddEditActivity
import com.patrickr.roomapp.adaptors.NoteAdaptor
import com.patrickr.roomapp.adaptors.NoteOnClickListener

class MainActivity : AppCompatActivity(), NoteOnClickListener {

	private lateinit var noteViewModel: NoteViewModel
	private lateinit var recyclerView: RecyclerView
	private lateinit var noteAdaptor: NoteAdaptor
	private lateinit var addNoteButton: FloatingActionButton
	private lateinit var getResult: ActivityResultLauncher<Intent>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setupViewAndAdaptor()
		setupNoteViewModel()
		setupButton()
		addDeleteOption()
	}

	private fun setupNoteViewModel() {
		noteViewModel = ViewModelProvider(
			this,
			ViewModelProvider.AndroidViewModelFactory(application)
		)[NoteViewModel::class.java]

		noteViewModel.allNotes.observe(this) { list ->
			noteAdaptor.setNotes(list)
		}
	}

	private fun setupViewAndAdaptor() {
		addNoteButton = findViewById(R.id.add_note_btn)
		recyclerView = findViewById(R.id.recycler_view_notes)
		noteAdaptor = NoteAdaptor(this)
		recyclerView.adapter = noteAdaptor
		recyclerView.layoutManager = LinearLayoutManager(this)


	}

	private fun setupButton() {
		getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
			val note: Note = it.data?.getSerializableExtra(KeyConstants.note.name, Note::class.java ) ?: return@registerForActivityResult
			when (it.resultCode) {
				CodeConstants.addRequestCode.code -> {
					noteViewModel.addNote(note)
				}

				CodeConstants.editRequestCode.code -> {
					noteViewModel.updateNote(note)
				}
			}
		}
		addNoteButton.setOnClickListener {
			val intent = Intent(this@MainActivity, AddEditActivity::class.java)
			getResult.launch(intent)
		}
	}

	fun addDeleteOption() {
		ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
			override fun onMove(
				recyclerView: RecyclerView,
				viewHolder: RecyclerView.ViewHolder,
				target: RecyclerView.ViewHolder
			): Boolean {
				return false
			}

			override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
				noteViewModel.deleteNote(noteAdaptor.getNoteAt(viewHolder.adapterPosition))
			}

		}).attachToRecyclerView(recyclerView)
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.main_menu, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when(item.itemId) {
			R.id.delete_all_notes_menu -> {
				noteViewModel.deletAll()
			}
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onItemClick(note: Note) {
		val intent = Intent(this@MainActivity, AddEditActivity::class.java)
		intent.putExtra(KeyConstants.note.name, note)
		getResult.launch(intent)
	}
}