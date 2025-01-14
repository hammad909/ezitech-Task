package com.oneasad.googleloginjetpackcompose.screens


import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.oneasad.googleloginjetpackcompose.R
import com.oneasad.googleloginjetpackcompose.models.AttendanceList
import com.oneasad.googleloginjetpackcompose.models.User
import com.oneasad.googleloginjetpackcompose.myviewmodels.FireBaseViewModel
import com.oneasad.googleloginjetpackcompose.myviewmodels.GoogleSignInViewModel
import com.oneasad.googleloginjetpackcompose.ui.theme.Purple


@Composable
fun Dashboard(googleSignInViewModel: GoogleSignInViewModel,
              fireBaseViewModel: FireBaseViewModel,
              navController: NavController){

     val student by googleSignInViewModel.user.observeAsState()
     val myCurrentEmail = student?.email
     Log.d("E1","my current email $myCurrentEmail")
     val showPopup = remember { mutableStateOf(false) }
     val attendanceList = remember { mutableStateListOf<AttendanceList>() }

    //adding column to ensure it stays on top of the screen
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier) {
            //background Image
            Image(painter = painterResource(R.drawable.sq1),
                contentDescription = "Background Image", Modifier.fillMaxSize()
                , contentScale = ContentScale.Crop
            )

            Profile(student = student)


            Box( modifier = Modifier.fillMaxSize()
                .padding(bottom = 32.dp,
                    top = 260.dp,
                    start = 100.dp)){
                student?.name?.let {fullName->
                    val names = fullName.split(" ")
                    val displayName = names[0]

                    Text(
                        text = displayName,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Medium,
                        color = Purple
                    )

                }
            }

            Column(modifier = Modifier
                .align(Center)
                .padding(start = 10.dp)
            ) {
                //profile
                //action no 1 check attendance
                OutlinedButton(onClick = {
                   fireBaseViewModel.markAttendance(myCurrentEmail = myCurrentEmail)
                } ,
                    modifier = Modifier.width(162.dp))
                { Text("Mark Attendance", color = Purple) }
                Spacer(Modifier.height(10.dp))



                //part 2 request leave
                OutlinedButton(onClick = {
                    fireBaseViewModel.markLeave(myCurrentEmail = myCurrentEmail)
                } ,
                    modifier = Modifier.width(162.dp))
                { Text("Mark Leave", color = Purple) }
                Spacer(Modifier.height(10.dp))


                //part 3 check list
                OutlinedButton(onClick = {
                showPopup.value = true
                    fireBaseViewModel.fetchAttendance(myCurrentEmail, attendanceList)  // Fetch attendance records
                },
                    modifier = Modifier.width(162.dp))
                { Text("Check Attendance", color = Purple) }

            }
            Button(onClick = {
                googleSignInViewModel.signOut(navController = navController)
            },
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 4.dp, end = 4.dp))
            { Text("Log Out", color = Purple)}
        }
    }
    if (showPopup.value) {
        fireBaseViewModel.AttendancePopup(attendanceList, onDismiss = { showPopup.value = false })
    }
}

//profile pic part
@Composable
fun Profile(student: User?){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 460.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = student?.photoUrl,
            contentDescription = "Avatar",
            placeholder = painterResource(R.drawable._1),
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop
        )

    }

}


@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun DashboardTheme(){
           Dashboard(googleSignInViewModel = GoogleSignInViewModel(),
               fireBaseViewModel = FireBaseViewModel(),
               navController = rememberNavController()
           )
}