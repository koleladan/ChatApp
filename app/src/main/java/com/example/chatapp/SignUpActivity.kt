package com.example.chatapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
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

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    startActivity(intent)

                }else{
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "some error occurred", Toast.LENGTH_SHORT).show()

                }

            }

    }
}