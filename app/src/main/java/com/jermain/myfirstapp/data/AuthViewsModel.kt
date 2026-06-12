package com.jermain.myfirstapp.data

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jermain.myfirstapp.models.users
import com.jermain.myfirstapp.navigation.ROUTE_DASHBOARD
import com.jermain.myfirstapp.navigation.ROUTE_LOGIN

class AuthViewsModel(var navController: NavHostController, var context: Context) {
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signup(name: String, email: String, pass: String, confirm: String) {
        if (name.isBlank() || email.isBlank() || pass.isBlank() || confirm.isBlank()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
            return
        } else if (pass != confirm) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
            return
        } else if (pass.length < 6) {
            Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_LONG).show()
            return
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = mAuth.currentUser?.uid ?: ""
                    val userdata = users(name, email, pass, confirm, userId)
                    val regRef = FirebaseDatabase.getInstance().getReference()
                        .child("Users/$userId")
                    
                    regRef.setValue(userdata).addOnCompleteListener { dbTask ->
                        if (dbTask.isSuccessful) {
                            Toast.makeText(context, "Saved successfully", Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_LOGIN)
                        } else {
                            Toast.makeText(context, "Database Error: ${dbTask.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Auth Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun login(email: String,pass: String){
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context,"Successfully logged in",
                    Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_DASHBOARD)
            }else{
                Toast.makeText(context,"${it.exception!!.message}",
                    Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_LOGIN)
            }
        }
    }
}
