package com.patrickr.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.patrickr.fragments.fragments.ExampleFragment
import com.patrickr.fragments.fragments.ExampleFragmentArguments
import com.patrickr.fragments.fragments.FragmentA
import com.patrickr.fragments.fragments.FragmentAListener
import com.patrickr.fragments.fragments.FragmentB
import com.patrickr.fragments.fragments.FragmentBListener

class MainActivity : AppCompatActivity(), FragmentAListener, FragmentBListener {
	private lateinit var fragmentA: FragmentA
	private lateinit var fragmentB: FragmentB

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

//		val fragment = ExampleFragment.newInstanceKotlin("John", 78)
//
//		supportFragmentManager
//			.beginTransaction()
//			.replace(R.id.container, fragment)
//			.commit()

		fragmentA = FragmentA()
		fragmentB = FragmentB()

		supportFragmentManager
			.beginTransaction()
			.replace(R.id.container_a, fragmentA)
			.replace(R.id.container_b, fragmentB)
			.commit()
	}

	override fun sendFromA(input: String) {
		fragmentB.updateText(input)
	}

	override fun sendFromB(input: String) {
		fragmentA.updateText(input)
	}
}