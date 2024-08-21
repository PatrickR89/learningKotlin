package com.patrickr.bottomviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.patrickr.bottomviewapp.dialog.BottomSheetListener
import com.patrickr.bottomviewapp.dialog.MyButtonSheetDialog
import com.patrickr.bottomviewapp.fragments.FavoritesFragment
import com.patrickr.bottomviewapp.fragments.HomeFragment
import com.patrickr.bottomviewapp.fragments.SearchFragment

class MainActivity : AppCompatActivity() {
	/// With navigation
//	private val bottomNavigationView: BottomNavigationView? by lazy {
//		 findViewById<BottomNavigationView>(R.id.nav_view)
//	}

	/// With Bottom sheet
//	private lateinit var openDialogButton: Button
//	private lateinit var textView: TextView
	/// Bottom sheet
//	private lateinit var bottomSheetBehaviour: BottomSheetBehavior<View>
//	private lateinit var textViewState: TextView

	/*
	/// EditText watcher
	private lateinit var nameText: EditText
	private lateinit var passwordText: EditText
	private lateinit var loginButton: AppCompatButton
	private val textWatcher: TextWatcher by lazy {
		object: TextWatcher {
			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

			}

			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
				loginButton.isEnabled = nameText.text.toString().trim().isNotEmpty() &&
						passwordText.text.toString().trim().isNotEmpty()
			}

			override fun afterTextChanged(p0: Editable?) {

			}

		}
	}
	 */

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		collapsingToolbar()
	}
	/// Collapsing toolbar
	private fun collapsingToolbar() {
			val toolbar: Toolbar = findViewById(R.id.collapsing_toolbar)
			setSupportActionBar(toolbar)
		}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.navigation_view_menu, menu)
		return true
	}
	/*
	/// TextWatcher
	private fun createTextWatcher() {

		nameText = findViewById(R.id.editTextName)
		passwordText = findViewById(R.id.editTextPassword)
		loginButton = findViewById(R.id.loginButton)
		loginButton.isEnabled = false
		nameText.addTextChangedListener(textWatcher)
		passwordText.addTextChangedListener(textWatcher)
	}
	 */

	/*
	/// Bottom sheet
	private fun createBottomSheet() {
		textViewState = findViewById(R.id.text_state)
		val view = findViewById<View>(R.id.bottom_sheet)
		bottomSheetBehaviour = BottomSheetBehavior.from(view)
		val buttonOpen: AppCompatButton = findViewById(R.id.button_open)
		val buttonClose: AppCompatButton = findViewById(R.id.button_close)

		buttonOpen.setOnClickListener {
			bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
		}
		buttonClose.setOnClickListener {
			bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
		}

		bottomSheetBehaviour.addBottomSheetCallback(object: BottomSheetCallback() {
			override fun onStateChanged(bottomSheet: View, newState: Int) {
				when(newState) {
					BottomSheetBehavior.STATE_EXPANDED -> {
						textViewState.text = "Expanded"
					}
					BottomSheetBehavior.STATE_COLLAPSED -> {
						textViewState.text = "Collapsed"
					}

					BottomSheetBehavior.STATE_DRAGGING -> {
						textViewState.text = "Dragging"
					}
				}
			}

			override fun onSlide(bottomSheet: View, slideOffset: Float) {
				// do nothing
			}

		})
	}

	 */
/*
	Used for bottom sheet
	**** Add MainActivity conformance to BottomSheetListener
	override fun onButtonClicked(input: String) {
		textView.text = input
	}

	private fun createBottomLayout() {
		openDialogButton = findViewById(R.id.open_dialog)
		textView = findViewById(R.id.text_view)

		openDialogButton.setOnClickListener {
			val myBottomSheetDialog = MyButtonSheetDialog()
			myBottomSheetDialog.show(supportFragmentManager, "Dialog")
		}
	}
	*/
	/// On main_activity layout relativeLayout
//	private fun createBottomNavigation() {
//		bottomNavigationView?.setOnItemSelectedListener {
//			var fragment: Fragment? = null
//			when (it.itemId) {
//				R.id.nav_home -> {
//					fragment = HomeFragment()
//				}
//
//				R.id.nav_search -> {
//					fragment = SearchFragment()
//				}
//
//				R.id.nav_favorites -> {
//					fragment = FavoritesFragment()
//				}
//			}
//
//			fragment?: return@setOnItemSelectedListener false
//			supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
//			return@setOnItemSelectedListener true
//		}
//	}
}