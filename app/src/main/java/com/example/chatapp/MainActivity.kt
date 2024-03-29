package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Data.User
import com.example.chatapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter:userAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var userList: ArrayList<User>
    private lateinit var  binding: ActivityMainBinding
    private lateinit var mdata: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userList = ArrayList()
        adapter = userAdapter(this, userList)
        mAuth= FirebaseAuth.getInstance()
        mdata = FirebaseDatabase.getInstance().getReference()

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter =adapter
        mdata.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if (mAuth.currentUser?.uid!=currentUser?.uid){
                        userList.add(currentUser!!)

                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout){
            mAuth.signOut()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            finish()
            startActivity(intent)
            return true

        }
        return true
    }

}