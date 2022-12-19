package com.example.chatapp

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()


        binding.login.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.edtpassword.text.toString()

            if(email.isEmpty()) {
                binding.email.error = "Enter email"
            }
            else if (password.isEmpty()) {
                binding.edtpassword.error = "Enter password"
            }
            else{
                login(email, password)
            }

        }
        binding.signup.setOnClickListener {
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }

    private fun login(email: String, password: String) {

        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    val intent= Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(intent)

                }else{
                    progressDialog.dismiss()
                    Log.e(ContentValues.TAG, "signinWithCustomToken:failure", task.exception)
                    Toast.makeText(applicationContext, "User does not exist",Toast.LENGTH_SHORT).show()
                }
            }

    }
}