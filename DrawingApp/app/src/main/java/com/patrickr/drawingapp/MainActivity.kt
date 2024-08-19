package com.patrickr.drawingapp

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.util.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {
	private lateinit var drawingView: DrawingView
	private lateinit var brushButton: ImageButton
	private lateinit var undoButton: ImageButton
	private lateinit var purpleButton: ImageButton
	private lateinit var redButton: ImageButton
	private lateinit var orangeButton: ImageButton
	private lateinit var greenButton: ImageButton
	private lateinit var blueButton: ImageButton
	private lateinit var colorPickerButton: ImageButton
	private lateinit var galleryButton: ImageButton
	private lateinit var saveButton: ImageButton

	private val openGalleryLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
		findViewById<ImageView>(R.id.gallery_image).setImageURI(result.data?.data)
	}

	private val requestPermission: ActivityResultLauncher<Array<String>> =
		registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
			permissions.entries.forEach {
				val permissionName = it.key
				val isGranted = it.value

				if (isGranted && permissionName == MainActivity.readPermission) {
					openGallery()
					println("Permission for external storage granted!")
				} else if (isGranted && permissionName == MainActivity.writePermission) {
					createImageForSaving()
				} else if (permissionName == MainActivity.readPermission) {
					println("Read permission denied.")
				} else if (permissionName == MainActivity.writePermission) {
					println("Write permission denied.")
				}
			}
		}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		getDrawingView()
		getBrushButton()
		getUndoButton()
		getColorButtons()
		getColorPickerButton()
		getGalleryButton()
		getSaveButton()
	}

	private fun getSaveButton() {
		saveButton = findViewById(R.id.save_btn)
		saveButton.setOnClickListener(this)
	}

	private fun getDrawingView() {
		drawingView = findViewById(R.id.drawing_view)
		drawingView.changeBrushSize(23f)
	}

	private fun getBrushButton() {
		brushButton = findViewById(R.id.brush_width)
		brushButton.setOnClickListener {
			showBrushDialog()
		}
	}

	private fun getGalleryButton() {
		galleryButton = findViewById(R.id.gallery_btn)
		galleryButton.setOnClickListener(this)
	}

	private fun getColorPickerButton() {
		colorPickerButton = findViewById(R.id.color_btn)
		colorPickerButton.setOnClickListener(this)
	}

	private fun getUndoButton() {
		undoButton = findViewById(R.id.undo_btn)
		undoButton.setOnClickListener(this)
	}

	private fun showBrushDialog() {
		val brushDialog = Dialog(this@MainActivity)
		brushDialog.setContentView(R.layout.dialog_brush)
		val seekBarProgress = brushDialog.findViewById<SeekBar>(R.id.dialog_seek_bar)
		val textView = brushDialog.findViewById<TextView>(R.id.seeker_progress)

		seekBarProgress.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
			override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
				val seeker = p0?: return
				drawingView.changeBrushSize(seeker.progress.toFloat())
				textView.text = seeker.progress.toString() + "/100"

			}

			override fun onStartTrackingTouch(p0: SeekBar?) {
			}

			override fun onStopTrackingTouch(p0: SeekBar?) {
			}
		})
		brushDialog.show()
	}

	private fun getColorButtons() {
		purpleButton = findViewById(R.id.purple_btn)
		orangeButton = findViewById(R.id.orange_btn)
		redButton = findViewById(R.id.red_btn)
		blueButton = findViewById(R.id.blue_btn)
		greenButton = findViewById(R.id.green_btn)

		purpleButton.setOnClickListener(this)
		orangeButton.setOnClickListener(this)
		redButton.setOnClickListener(this)
		greenButton.setOnClickListener(this)
		blueButton.setOnClickListener(this)
	}

	override fun onClick(p0: View?) {
		val view = p0 ?: return
		println("${view.id}")
		when(view.id) {
			R.id.purple_btn -> {
				drawingView.setColor("#D14EF6")
			}
			R.id.red_btn -> {
				drawingView.setColor("#FA5B68")
			}
			R.id.orange_btn -> {
				drawingView.setColor("#BD7A06")
			}
			R.id.blue_btn -> {
				drawingView.setColor("#031DC0")
			}
			R.id.green_btn -> {
				drawingView.setColor("#179500")
			}
			R.id.undo_btn -> {
				drawingView.undo()
			}
			R.id.color_btn -> {
				showColorPickerDialog()
			}
			R.id.gallery_btn -> {
				if (ActivityCompat.checkSelfPermission(this, MainActivity.readPermission) != PackageManager.PERMISSION_GRANTED) {
					requestStoragePermission()
				} else {
					openGallery()
				}
			}
			R.id.save_btn -> {
				if (ActivityCompat.checkSelfPermission(
						this,
						MainActivity.writePermission ?: android.Manifest.permission.WRITE_EXTERNAL_STORAGE
					) != PackageManager.PERMISSION_GRANTED) {
					requestStoragePermission()
				} else {
					createImageForSaving()
				}
			}
			else -> return
		}
	}

	private fun createImageForSaving() {
		val layout = findViewById<ConstraintLayout>(R.id.main_layout)
		val bitmap = getBitmapFromView(layout)
		CoroutineScope(IO).launch {
			saveImage(bitmap)
		}
	}

	private fun getBitmapFromView(view: View): Bitmap {
		val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
		val canvas = Canvas(bitmap)
		view.draw(canvas)
		return bitmap
	}

	private suspend fun saveImage(bitmap: Bitmap) {

		val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
		val myDir = File("$root/saved_images")
		myDir.mkdir()
		val generator = Random()
		var n = 1000
		n = generator.nextInt(n)
		val outputFile = File(myDir, "Image-$n.jpeg")
		if (outputFile.exists()) {
			outputFile.delete()
		} else {
			try {
				val out = FileOutputStream(outputFile)
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
				out.flush()
				out.close()
				withContext(Main) {
					Toast.makeText(this@MainActivity, "${outputFile.absolutePath} saved", Toast.LENGTH_SHORT).show()
				}
			} catch (e: Exception) {
				e.stackTrace
				withContext(Main) {
					Toast.makeText(this@MainActivity, "Error saving photo!", Toast.LENGTH_SHORT).show()
				}
			}


		}
	}
//	@TargetApi(30)
//	fun storeImage(bitmap: Bitmap) {
//		val uri = Uri.parse()
//		applicationContext.contentResolver.openOutputStream("some") {
//
//		}
//	}

	private fun requestStoragePermission() {
		if (ActivityCompat.shouldShowRequestPermissionRationale(this, MainActivity.readPermission)) {
			showRationaleDialog()
		} else {
			val permissions = mutableListOf(MainActivity.readPermission)
			MainActivity.writePermission?.let {
				permissions.add(it)
			}
			requestPermission.launch(
				permissions.toTypedArray()
			)
		}
	}

	private fun showRationaleDialog() {
		val builder = AlertDialog.Builder(this)
		builder
			.setTitle("Storage permission.")
			.setMessage("We need this permission in order to access internal storage.")
			.setPositiveButton("Accept") { dialog, _ ->
				val permissions = mutableListOf(MainActivity.readPermission)
				MainActivity.writePermission?.let {
					permissions.add(it)
				}
				requestPermission.launch(
					permissions.toTypedArray()
				)
				dialog.dismiss()
			}
		builder.create().show()
	}

	private fun showColorPickerDialog() {
		val dialog = AmbilWarnaDialog(this, Color.GREEN, object: OnAmbilWarnaListener {
			override fun onCancel(dialog: AmbilWarnaDialog?) {
				return
			}

			override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
				drawingView.setColor(color)
			}

		})
		dialog.show()
	}

	private fun openGallery() {
		val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
		openGalleryLauncher.launch(pickIntent)
	}

	companion object {
		val readPermission: String by lazy<String> {
			if (android.os.Build.VERSION.SDK_INT <= 30) {
				android.Manifest.permission.READ_EXTERNAL_STORAGE
			} else {
				android.Manifest.permission.READ_MEDIA_IMAGES
			}
		}

		val writePermission: String? by lazy<String?> {
			if (android.os.Build.VERSION.SDK_INT <= 30) {
				android.Manifest.permission.WRITE_EXTERNAL_STORAGE
			} else {
				null
			}
		}
	}
}