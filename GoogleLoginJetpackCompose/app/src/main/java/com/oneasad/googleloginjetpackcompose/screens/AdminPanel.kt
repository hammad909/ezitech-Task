package com.oneasad.googleloginjetpackcompose.screens

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.oneasad.googleloginjetpackcompose.models.User
import com.oneasad.googleloginjetpackcompose.myviewmodels.FireBaseViewModel
import com.oneasad.googleloginjetpackcompose.myviewmodels.GoogleSignInViewModel
import com.oneasad.googleloginjetpackcompose.ui.theme.Purple

@Composable
fun AdminPanel(googleSignInViewModel: GoogleSignInViewModel,
               fireBaseViewModel: FireBaseViewModel,
               navController: NavController){

    val admin by googleSignInViewModel.user.observeAsState()
    var text by remember { mutableStateOf("") }
    var optionSelectedAttendance by remember { mutableStateOf("") }
    var checkOptions by remember { mutableStateOf(false) }
    var isValidEmail by remember { mutableStateOf(false)
    }

    //adding column to ensure it stays on top of the screen
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier) {
            //background Image
            Image(painter = painterResource(R.drawable.sq1),
                contentDescription = "Background Image", Modifier.fillMaxSize()
                , contentScale = ContentScale.Crop
            )

            ProfilePic(admin)

            Text(
                text = "Admin",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Medium,
                color = Purple,
                modifier = Modifier
                    .padding(bottom = 40.dp,
                        top = 50.dp,
                        start = 130.dp)
            )

            OutlinedTextField(
                value = text,
                onValueChange = { text = it
                    isValidEmail = it.matches(Regex("^[a-zA-Z0-9._%+-]+@gmail\\.com\$"))
                },
                label = { Text("Enter Email to add/delete", color = Purple) },
                modifier = Modifier.align(Alignment.Center)
                    .padding(bottom = 240.dp)
            )

            //enter the present, absent and leave for student
            OutlinedTextField(
                value = optionSelectedAttendance,
                onValueChange = { optionSelectedAttendance = it
                    checkOptions = it.matches(Regex("^[PAL]$"))
                },
                label = { Text("Enter P,A or L", color = Purple) },
                modifier = Modifier.align(Alignment.Center).padding(bottom = 100.dp)
            )

            Spacer(Modifier.height(10.dp))
            Column(modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 10.dp, top = 140.dp)
            ) {
                //profile

                //action no 1 check attendance
                OutlinedButton(onClick = {
                    fireBaseViewModel.addStudent(text)
                },
                    Modifier.run { width(132.dp) })
                { Text("Add", color = Purple)
                     }
                Spacer(Modifier.height(10.dp))

                //part 2 request leave
                OutlinedButton(onClick = {
                  fireBaseViewModel.deleteStudent(text)
                } ,
                    Modifier.width(132.dp),
                     )
                { Text("Delete", color = Purple) }
                Spacer(Modifier.height(10.dp))

                //review leaves
                OutlinedButton(onClick = {
                     fireBaseViewModel.editStudentStatus(text,optionSelectedAttendance)
                },
                    Modifier.width(132.dp))
                { Text("Edit", color = Purple) }

            }

            Button(onClick = {
                googleSignInViewModel.signOut(navController = navController)
            }, modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 4.dp, end = 4.dp))
            { Text("Log Out", color = Purple) }
        }
        if (!isValidEmail) {
            Text(
                text = "Please enter a valid Gmail address",
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

}

//profile pic part
@Composable
fun ProfilePic(admin: User?){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 460.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = admin?.photoUrl,
            contentDescription = "Avatar",
            placeholder = painterResource(R.drawable._2),
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
fun AdminTheme(){
    AdminPanel(googleSignInViewModel = GoogleSignInViewModel(),
        fireBaseViewModel = FireBaseViewModel(),
        navController = rememberNavController()
    )
}