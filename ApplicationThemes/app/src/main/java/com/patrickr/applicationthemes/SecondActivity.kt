package com.patrickr.applicationthemes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class SecondActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_second)
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when(item.itemId) {
			R.id.item_one -> {
//				Toast.makeText(this@SecondActivity, "Item 1 tapped.", Toast.LENGTH_SHORT).show()
			val intent = Intent(this@SecondActivity, RelativeLayoutActivity::class.java)
				startActivity(intent)
			}
			R.id.item_two -> {
				Toast.makeText(this@SecondActivity, "Item 2 tapped.", Toast.LENGTH_SHORT).show()
			}

			R.id.sub_item_one -> {
				Toast.makeText(this@SecondActivity, "Sub item 1 tapped.", Toast.LENGTH_SHORT).show()
			}

			R.id.sub_item_two -> {
//				Toast.makeText(this@SecondActivity, "Sub item 2 tapped.", Toast.LENGTH_SHORT).show()
				val intent = Intent(this@SecondActivity, ScrollViewActivity::class.java)
				startActivity(intent)
			}
		}
		return super.onOptionsItemSelected(item)
	}
}