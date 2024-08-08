package com.patrickr.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.patrickr.composetutorial.ui.theme.ComposeTutorialTheme
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import kotlin.random.Random
import kotlin.reflect.KProperty

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

//		setContent {
//			val painter = painterResource(id = R.drawable.sample)
//			ImageCard(painter = painter, contentDescription = "Some image", title = "Photo")
//		}

		setContent {
			val scaffoldState = rememberScaffoldState()
			var textFieldState by remember {
				mutableStateOf("")
			}
			val scope = rememberCoroutineScope()
			Scaffold(
				modifier = Modifier.fillMaxSize(),
				scaffoldState = scaffoldState
			) { paddingValues ->

				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center,
					modifier = Modifier
						.fillMaxSize()
						.padding(horizontal = 30.dp)
				) {
					TextField(
						value = textFieldState,
						label = { Text(text = "Enter your name.") },
						onValueChange = {
							textFieldState = it
						},
						singleLine = true,
						modifier = Modifier.fillMaxWidth()
					)
					
					Spacer(modifier = Modifier.height(16.dp))

					Box(modifier = Modifier.fillMaxWidth(),
					contentAlignment = Alignment.CenterEnd
					) {

						Button(onClick = { scope.launch {
							scaffoldState.snackbarHostState.showSnackbar("Hello $textFieldState")
						}}) {
							Text(text = "Please greet me", color = Color.White)

						}
					}
				}

			}

		}
	}
}

@Composable
fun textFieldsButtonsSnackBars() {

}


@Composable
fun ColorBoxes() {
	val color = remember { mutableStateOf(Color.Blue) }
	Column() {
		ColorBox(
			Modifier
				.weight(1f)
				.fillMaxSize()
		) { color.value = it }
		Box(
			modifier = Modifier
				.background(color.value)
				.weight(1f)
				.fillMaxSize()
		)
	}
}

@Composable
fun ColorBox(modifier: Modifier = Modifier, updateColor: (Color) -> Unit) {
//	val color = remember { mutableStateOf(Color.Red) }

	Box(modifier = modifier
		.background(Color.Red)
		.clickable {
			updateColor(
				Color(
					Random.nextFloat(),
					Random.nextFloat(),
					Random.nextFloat(),
					1f
				)
			)
		})
}

@Composable
fun jetpackCompose() {
	val fontFamily = FontFamily(
		Font(R.font.oswald_bold, FontWeight.Bold),
		Font(R.font.oswald_extralight, FontWeight.ExtraLight),
		Font(R.font.oswald_light, FontWeight.Light),
		Font(R.font.oswald_medium, FontWeight.Medium),
		Font(R.font.oswald_regular, FontWeight.Normal),
		Font(R.font.oswald_semibold, FontWeight.SemiBold)
	)
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(Color(0xFF101010))
	) {
		Text(
			text = buildAnnotatedString {
				withStyle(
					style = SpanStyle(
						color = Color.Green,
						fontSize = 55.sp
					)
				) {
					append("J")
				}
				append("etpack")
				withStyle(
					style = SpanStyle(
						color = Color.Green,
						fontSize = 55.sp
					)
				) {
					append("C")
				}
				append("ompose")
			},
			color = Color.White,
			fontSize = 30.sp,
			fontFamily = fontFamily,
			textDecoration = TextDecoration.LineThrough
		)
	}
}

@Composable
fun ImageCard(
	painter: Painter,
	contentDescription: String,
	title: String,
	modifier: Modifier = Modifier
) {
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

// .width of exceeds only fills, requiredWidth sets actual value
// regardless if it exceeds
