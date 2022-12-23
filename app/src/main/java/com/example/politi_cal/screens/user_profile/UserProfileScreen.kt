package com.example.politi_cal.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.politi_cal.models.User
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserProfileScreen(
    navController: NavController, auth: FirebaseAuth, user: User
) {
    LazyColumn(content = {

        item {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                    )
            ) {
                val context = LocalContext.current
                Text(
                    text = "User Profile Screen", style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                ) {
                    // show all user info
                    Text(
                        text = "User Email: ${user.email}", style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "User Age: ${user.userAge}",
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        text = "${user.userPref}", style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "User Party: ${user.favoritePartyID}",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "User register Date: ${user.registerDate}",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "User Gender: ${user.userGender}",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "User role ID: ${user.roleID}",
                        style = MaterialTheme.typography.body1
                    )

                    TextButton(
                        onClick = {
                            /*TODO*/
//                            var DBobj = UserVoteDBObj()
//                            DBobj.deleteAllVotesByUserID()








                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary
                        )


                    ) {
                        Text(text = "Delete User")

                    }
                }
            }
        }
    })
}



//fun deleteUser(
//    context: Context,
//    auth: FirebaseAuth,
//    navController: NavController,
//    callback : CallBack<Boolean,Boolean>
//) =
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            // make a list of { celebName, voteDirection }
//            var userVotesToDelete = mutableListOf<Pair<String, String>>()
//            // delete all user votes from userVotesCollectionRef that UserEmail == user.email
////            var userEmail = auth.currentUser?.email
////            userVotesCollectionRef.whereEqualTo("userEmail", userEmail).get().addOnSuccessListener {
////                for (document in it) {
////                    println(document.data["celebName"].toString())
////                    println(document.data["voteDirection"].toString())
////                    userVotesToDelete.add(
////                        Pair(
////                            document.data["celebName"].toString(),
////                            document.data["voteDirection"].toString()
////                        )
////                    )
////                    userVotesCollectionRef.document(document.id).delete()
////                }
////            }
//
//
//
//
//
//            // decrement celeb vote according the the list of { celebName, voteDirection } that we made
//            // the property inside celeb document is leftVotes or rightVotes depending on the voteDirection
//            // and we decrement it by 1 and update the document
//            celebCollectionRef.get().addOnSuccessListener {
//                for (document in it) {
//                    for (vote in userVotesToDelete) {
//                        if (vote.first == document.getString("celebName")) {
//                            if (vote.second == "left") {
//                                celebCollectionRef.document(document.id).update(
//                                    "leftVotes",
//                                    document.getLong("leftVotes")?.minus(1)
//                                )
//                            } else {
//                                celebCollectionRef.document(document.id).update(
//                                    "rightVotes",
//                                    document.getLong("rightVotes")?.minus(1)
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
////            deleteVotesUserProfile(auth)
//            // delete user from userCollectionRef
//            userVotesCollectionRef.document(auth.currentUser?.uid.toString()).delete().await()
//
//
////            auth.currentUser?.delete()
//
//            withContext(Dispatchers.Main) {
//                Toast.makeText(context, "User Deleted", Toast.LENGTH_SHORT).show()
//                navController.navigate(Screen.AdminUserManagementScreen.route)
//            }
//
//
//        } catch (e: Exception) {
//            Log.d(TAG, "Error updated user to database: $e")
//            withContext(Dispatchers.Main) {
//                Toast.makeText(
//                    context, "Error updated user to database: $e", Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
//
//
//fun deleteVotesUserProfile(auth: FirebaseAuth) = CoroutineScope(Dispatchers.IO).launch {
//    try {
//        // make a list of { celebName, voteDirection }
//        var userVotesToDelete = mutableListOf<Pair<String, String>>()
//        // delete all user votes from userVotesCollectionRef that UserEmail == user.email
//        var userEmail = auth.currentUser?.email
//        userVotesCollectionRef.whereEqualTo("userEmail", userEmail).get().addOnSuccessListener {
//            for (document in it) {
//                userVotesToDelete.add(
//                    Pair(
//                        document.getString("celebName").toString(), document.getString(
//                            "voteDirection"
//                        ).toString()
//                    )
//                )
//                userVotesCollectionRef.document(document.id).delete()
//            }
//        }
//
//        // decrement celeb vote according the the list of { celebName, voteDirection } that we made
//        // the property inside celeb document is leftVotes or rightVotes depending on the voteDirection
//        // and we decrement it by 1 and update the document
//        celebCollectionRef.get().addOnSuccessListener {
//            for (document in it) {
//                for (vote in userVotesToDelete) {
//                    if (vote.first == document.getString("celebName")) {
//                        if (vote.second == "left") {
//                            celebCollectionRef.document(document.id).update(
//                                "leftVotes",
//                                document.getLong("leftVotes")?.minus(1)
//                            )
//                        } else {
//                            celebCollectionRef.document(document.id).update(
//                                "rightVotes",
//                                document.getLong("rightVotes")?.minus(1)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    } catch (e: Exception) {
//        Log.d(TAG, "Error updated user to database: $e")
//    }
//}























