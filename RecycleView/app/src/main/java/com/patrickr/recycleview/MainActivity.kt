package com.patrickr.recycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.patrickr.recycleview.Adaptors.ExampleAdaptor
import com.patrickr.recycleview.Model.ExampleItem

class MainActivity : AppCompatActivity() {

	private lateinit var recyclerView: RecyclerView
	private lateinit var adaptor: ExampleAdaptor
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val exampleList = generateList(50)
		recyclerView = findViewById(R.id.recycler_view)
		adaptor = ExampleAdaptor(this, exampleList)
		recyclerView.adapter = adaptor
		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.setHasFixedSize(true)
	}

	private fun generateList(size: Int): MutableList<ExampleItem> {
		val list = mutableListOf<ExampleItem>()

		for (index in 0 until size) {
			list.add(ExampleItem("Title $index", "Description $index-${index * 10 }-${index * 2.736}"))
		}

			return list
	}
}