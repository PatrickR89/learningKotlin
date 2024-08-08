package com.patrickr.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrickr.composetutorial.ui.theme.ComposeTutorialTheme
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
//		setContent {
//			Column(
//				modifier = Modifier.fillMaxSize().background(Color.Green),
//				horizontalAlignment = Alignment.CenterHorizontally,
//				verticalArrangement = Arrangement.SpaceAround
//			) {
//				Text(text = "Hello")
//				Text(text = "Second hello!")
//			}
//		}

//		setContent {
//			Column(
//				modifier = Modifier
//					.background(Color.Green)
//					.fillMaxHeight(0.5f)
////					.fillMaxWidth()
//					.border(5.dp, Color.Blue)
//					.width(300.dp)
//					.padding(16.dp) // can define each side separately
//					.border(15.dp, Color.Cyan)
//					.padding(15.dp)
//					.border(15.dp, Color.Red)
//					.padding(15.dp)
//			) {
//				Text(text = "Hello", modifier = Modifier.offset(50.dp, 25.dp))
//				Spacer(modifier = Modifier.height(100.dp))
//				Text("Someone")
//			}
//		}

		setContent {
			val painter = painterResource(id = R.drawable.sample)
			ImageCard(painter = painter, contentDescription = "Some image", title = "Photo")
		}
	}
}

@Composable
fun ImageCard(painter: Painter, contentDescription: String, title: String, modifier: Modifier = Modifier) {
	Card(
		modifier = modifier
			.fillMaxWidth(0.5f)
			.padding(16.dp),
		shape = RoundedCornerShape(16.dp),
		elevation = CardDefaults.cardElevation(
			defaultElevation = 16.dp
		)
	) {
		Box(modifier = Modifier.height(300.dp)) {
			Image(
				painter = painter,
				contentDescription = contentDescription,
				contentScale = ContentScale.Crop
			)
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(
						Brush.verticalGradient(
							colors = listOf(
								Color.Transparent,
								Color.Black
							),
							startY = 0.7f
						)
					)
			)

			Box(
				modifier = Modifier
					.fillMaxSize()
					.padding(12.dp),
				contentAlignment = Alignment.BottomStart
			) {
				Text(text = title, style = TextStyle(color = Color.White), fontSize = 15.sp)
			}

		}
	}
}

// .width of excceeds only fills, requiredWidth sets actual value
// regardless if it exceeds
