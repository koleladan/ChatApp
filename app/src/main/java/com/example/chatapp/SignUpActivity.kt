package com.example.chatapp

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatapp.Data.User
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        binding.signup.setOnClickListener {
            signup()
        }
    }

    private fun signup() {
        val  fname = binding.edtfname.text.toString()
        val lname = binding.edtlname.text.toString()
        val email = binding.email.text.toString()
        val password = binding.edtpassword.text.toString()

        if(fname.isEmpty()){
            binding.edtfname.error = "Enter first name"
        }
        else if (lname.isEmpty()){
            binding.edtlname.error = "Enter last name"
        }
        if(email.isEmpty()){
            binding.email.error = "Enter username"
        }
        else if(password.isEmpty()){
            binding.edtpassword.error = "Enter password"
        }
        val progressDialog =ProgressDialog(this@SignUpActivity)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    addUserToDatabase(fname,lname,email,mAuth.currentUser?.uid)
                    progressDialog.dismiss()
                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                }else{
                    progressDialog.dismiss()
                    Log.e(TAG, "signupWithCustomToken:failure", task.exception)

                    Toast.makeText(applicationContext, "some error occurred", Toast.LENGTH_SHORT).show()

                }

            }

    }

    private fun addUserToDatabase(fname: String, lname: String, email: String, uid: String?) {
        mData = FirebaseDatabase.getInstance().getReference()
        mData.child("user").child(uid!!).setValue(User(fname,lname,email,uid))


    }
}