package com.oneasad.googleloginjetpackcompose.myviewmodels

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.oneasad.googleloginjetpackcompose.models.AttendanceList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FireBaseViewModel:ViewModel() {

    private val database = Firebase.database
    private val myRef = database.getReference("message")
    //mark attendance function
    fun markAttendance(myCurrentEmail: String? ){

        val formattedEmail = myCurrentEmail?.replace(".", "_") // Replace dots for Firebase key

        if (formattedEmail != null) {
            // Get current date in "yyyy-MM-dd" format
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val todayDate = sdf.format(Date(System.currentTimeMillis()))

            // Check for existing attendance entry for today's date
            myRef.child(formattedEmail).get().addOnSuccessListener { dataSnapshot ->
                var alreadyMarked = false

                for (entry in dataSnapshot.children) {
                    val timestamp = entry.child("timestamp").value as? Long
                    if (timestamp != null) {
                        val entryDate = sdf.format(Date(timestamp))
                        if (entryDate == todayDate) {
                            alreadyMarked = true
                            break
                        }
                    }
                }

                if (!alreadyMarked) {
                    // Create attendance data
                    val attendanceData = mapOf(
                        "status" to "P",
                        "timestamp" to System.currentTimeMillis()
                    )
                    // Add the new attendance entry
                    myRef.child(formattedEmail).push().setValue(attendanceData)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("Attendance", "Attendance marked successfully for today.")
                            } else {
                                Log.e("Attendance", "Error: ${task.exception?.message}")
                            }
                        }
                } else {
                    Log.d("Attendance", "Attendance already marked for today.")
                }
            }.addOnFailureListener { exception ->
                Log.e("Attendance", "Error fetching data: ${exception.message}")
            }
        } else {
            Log.e("Firebase", "User email is null")
        }
    }

    fun fetchAttendance(myCurrentEmail: String?, attendanceList: SnapshotStateList<AttendanceList>) {
        val formattedEmail = myCurrentEmail?.replace(".", "_") // Replace dots for Firebase key

        if (formattedEmail != null) {
            myRef.child(formattedEmail).get().addOnSuccessListener { dataSnapshot ->
                attendanceList.clear() // Clear previous data
                for (entry in dataSnapshot.children) {
                    val status = entry.child("status").value as? String
                    val timestamp = entry.child("timestamp").value as? Long
                    if (status != null && timestamp != null) {
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val formattedDate = sdf.format(Date(timestamp))
                        attendanceList.add(AttendanceList(status, formattedDate))
                    }
                }
            }.addOnFailureListener { exception ->
                Log.e("Attendance", "Error fetching data: ${exception.message}")
            }
        }
    }

    // Popup to show attendance history
    @Composable
    fun AttendancePopup(attendanceList: SnapshotStateList<AttendanceList>, onDismiss: () -> Unit) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Attendance History",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // LazyColumn to show the attendance data in a scrollable list
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(attendanceList) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = item.date, style = MaterialTheme.typography.bodyLarge)
                                Text(text = item.status, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }

                    // Close Button
                    Button(onClick = { onDismiss() }) {
                        Text("Close")
                    }
                }
            }
        }
    }

    //Admin adding user function
    fun addStudent(text:String){
        val text2 = text.replace(".", "_")
        val attendanceData = mapOf(
            "status" to "P",
            "timestamp" to System.currentTimeMillis()
        )
        myRef.child(text2).setValue(attendanceData)
    }

    //Admin user delete function
    fun deleteStudent(text:String){
        val text2 = text.replace(".", "_")
        myRef.child(text2).removeValue()
    }

    fun markLeave(myCurrentEmail: String? ){

        val formattedEmail = myCurrentEmail?.replace(".", "_") // Replace dots for Firebase key

        if (formattedEmail != null) {
            // Get current date in "yyyy-MM-dd" format
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val todayDate = sdf.format(Date(System.currentTimeMillis()))

            // Check for existing attendance entry for today's date
            myRef.child(formattedEmail).get().addOnSuccessListener { dataSnapshot ->
                var alreadyMarked = false

                for (entry in dataSnapshot.children) {
                    val timestamp = entry.child("timestamp").value as? Long
                    if (timestamp != null) {
                        val entryDate = sdf.format(Date(timestamp))
                        if (entryDate == todayDate) {
                            alreadyMarked = true
                            break
                        }
                    }
                }

                if (!alreadyMarked) {
                    // Create attendance data
                    val attendanceData = mapOf(
                        "status" to "L",
                        "timestamp" to System.currentTimeMillis()
                    )
                    // Add the new attendance entry
                    myRef.child(formattedEmail).push().setValue(attendanceData)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("Attendance", "Attendance marked successfully for today.")
                            } else {
                                Log.e("Attendance", "Error: ${task.exception?.message}")
                            }
                        }
                } else {
                    Log.d("Attendance", "Attendance already marked for today.")
                }
            }.addOnFailureListener { exception ->
                Log.e("Attendance", "Error fetching data: ${exception.message}")
            }
        } else {
            Log.e("Firebase", "User email is null")
        }
    }

    fun editStudentStatus(email: String, status: String) {
        val formattedEmail = email.replace(".", "_") // Firebase key format

        if (formattedEmail.isNotEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val todayDate = sdf.format(Date(System.currentTimeMillis()))

            // Reference to the Firebase database for the student
            myRef.child(formattedEmail).get().addOnSuccessListener { dataSnapshot ->
                var statusUpdated = false
                for (entry in dataSnapshot.children) {
                    val timestamp = entry.child("timestamp").value as? Long
                    if (timestamp != null) {
                        val entryDate = sdf.format(Date(timestamp))
                        if (entryDate == todayDate) {
                            // Update the status for today
                            entry.ref.updateChildren(mapOf("status" to status))
                            statusUpdated = true
                            break
                        }
                    }
                }

                if (!statusUpdated) {
                    // If no status for today, create a new entry
                    val attendanceData = mapOf(
                        "status" to status,
                        "timestamp" to System.currentTimeMillis()
                    )
                    myRef.child(formattedEmail).push().setValue(attendanceData)
                }
            }.addOnFailureListener { exception ->
                Log.e("Firebase", "Error updating status: ${exception.message}")
            }
        }
    }

}