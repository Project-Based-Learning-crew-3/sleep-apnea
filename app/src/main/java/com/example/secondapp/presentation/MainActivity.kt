/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.secondapp.presentation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.secondapp.R
import com.example.secondapp.presentation.theme.SecondAppTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ButtonDefaults
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.compose.material.SwipeToDismissBoxDefaults
import androidx.wear.compose.material.SwipeableState
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.SwipeToDismissValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestPermissions(arrayOf(ComponentActivity.SENSOR_SERVICE), ComponentActivity.BIND_IMPORTANT)
        setContent {
            SensorPermissionScreen {

            }
        }
    }

}

@Composable
fun ShowPage(){
    var page by rememberSaveable { mutableStateOf(1) }

    when(page){
        1 -> GetStarted(click = {page = 2})
        2 -> StartPage()
    }

}

@Composable
fun GetStarted(click:() -> Unit) {
    SecondAppTheme {

        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(top = 45.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Take control of your sleep....", modifier = Modifier
                        .padding(start = 35.dp)
                        .padding(bottom = 50.dp),
                    fontSize = 13.sp
                )
            }

            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(top = 13.dp),
            ) {
                Button(onClick = { click() }, colors= ButtonDefaults.buttonColors(backgroundColor = Color.White),modifier = Modifier
                    .height(20.dp)
                    .padding(start = 80.dp)) {
                    Text(text = "Get started", fontSize = 8.sp, color = Color.Black,modifier=Modifier.padding(horizontal = 8.dp))
                }


            }
        }

    }

}

@Composable
fun StartPage() {
    SecondAppTheme {

        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(top = 40.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Monitor sleep apnea and get notified", modifier = Modifier
                        .padding(start = 45.dp, bottom = 50.dp)
                        .width(90.dp),
                    lineHeight = 16.sp,
                    fontSize = 13.sp
                )
            }


            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(top = 5.dp),
            ) {
                Button(onClick = {  }, colors= ButtonDefaults.buttonColors(backgroundColor = Color.White),modifier = Modifier
                    .height(20.dp)
                    .padding(start = 84.dp)) {
                    Text(text = "Start", fontSize = 8.sp, color = Color.Black)
                }


            }

        }

    }

}

@Composable
fun SensorPermissionScreen(
    onPermissionGranted: () -> Unit,
) {
    val context = LocalContext.current
    val permissionState = remember { mutableStateOf(false) }
    val snackbarVisibleState = remember { mutableStateOf(false) }

    Column {
        Text(
            text = "This app requires sensor permission.",
            style = MaterialTheme.typography.caption2,
            modifier = Modifier.padding(24.dp)
        )
        Button(
            onClick = {
                if (hasSensorPermission(context)) {
                    permissionState.value = true
                    onPermissionGranted()
                } else {
                    requestSensorPermission(context)
                }
            },
            enabled = !permissionState.value
        ) {
            Text(text = "Grant Permission")
        }
    }

//    if (snackbarVisibleState.value) {
//        SwipeableSurface(
//            state = rememberSwipeableState(initialValue = SwipeableState.Closed),
//            anchors = mapOf(0f to SwipeableState.Closed),
//            backgroundOpacity = 0f,
////            contentColor = .colors.snackbarContentColor
//        ) {
//            androidx.wear.compose.material.SwipeToDismissBoxDefaults(
//                onDismiss = { snackbarVisibleState.value = false },
//                modifier = Modifier.padding(bottom = 16.dp),
//                text = { Text("Permission granted!") }
//            )
//        }
//    }
}

private fun hasSensorPermission(context: Context): Boolean {
    val permission = Manifest.permission.BODY_SENSORS
    val result = ContextCompat.checkSelfPermission(context, permission)
    return result == PackageManager.PERMISSION_GRANTED
}

private fun requestSensorPermission(context: Context) {
    val permission = Manifest.permission.BODY_SENSORS
    ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), 0)
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true, showBackground = true)
@Composable
fun DefaultPreview() {
    GetStarted() {}
//    startPage()
//    SensorPermissionScreen {}

}