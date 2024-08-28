package com.patrickr.authapp
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.updateLayoutParams

class MainView(context: Context): ConstraintLayout(context) {
	private val loginButton: Button by lazy<Button> {
		val button = Button(context)
		val id = View.generateViewId()
		button.id = id
		button.text = "LOGIN"
		button
	}

	init {
		val id = View.generateViewId()
		this.id = id
		setupUI()
	}

	private fun setupUI() {
		this.background = Color.BLUE.toDrawable()
		this.addView(loginButton)

		loginButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
			this.startToStart = this@MainView.id
			this.endToEnd = this@MainView.id
			this.bottomToBottom = this@MainView.id
			this.width = this@MainView.width
			val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, this@MainView.context.resources.displayMetrics).toInt()
			this.setMargins(margin, margin, margin, margin)
		}
	}

	public fun addButtonAction(action: () -> Unit) {
		loginButton.setOnClickListener {
			action()
		}
	}
}