package com.patrickr.recycleview.Adaptors

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.patrickr.recycleview.Model.ExampleItem
import com.patrickr.recycleview.R
import com.patrickr.recycleview.SecondActivity
import com.patrickr.recycleview.Utils.Constants

class ExampleAdaptor(val context: Context, val elements: MutableList<ExampleItem>):
	RecyclerView.Adapter<ExampleAdaptor.ExampleViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
		val view = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.recycler_view_item, parent, false)

		return ExampleViewHolder(view)
	}

	override fun getItemCount(): Int {
		return elements.size
	}

	override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
		val currentItem = elements[position]
		holder.title.text = currentItem.title
		holder.description.text = currentItem.description
	}

	inner class ExampleViewHolder(view: View): RecyclerView.ViewHolder(view) {
		val title: TextView = view.findViewById(R.id.title)
		val description: TextView = view.findViewById(R.id.description)

		init {
			view.setOnClickListener {
				val position = adapterPosition
				val item = elements[position]
//				Toast.makeText(context, "Tapped: ${item.title}", Toast.LENGTH_SHORT).show()
				val intent = Intent(context, SecondActivity::class.java)
				intent.putExtra(Constants.title.key, item.title)
				intent.putExtra(Constants.description.key, item.description)

				context.startActivity(intent)
			}
		}
	}
}