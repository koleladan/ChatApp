package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Data.message
import com.example.chatapp.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private lateinit var adapter: messageAdapter
    private lateinit var  binding: ActivityChatBinding
    private lateinit var messageList:ArrayList<message>
    var receiverRoom:String? =null
    var senderRoom:String? =null
    private lateinit var mdata:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra("name")
        val receiverUid =intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid
        messageList =ArrayList()
        mdata = FirebaseDatabase.getInstance().getReference()

        mdata.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(message::class.java)
                        messageList.add(message!!)
                    }
                    adapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        binding.send.setOnClickListener {
            val message = binding.send.text.toString()
            val messageObject = message(message,senderUid.toString())


            mdata.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mdata.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)

                }
            binding.send.setText("")
        }




        supportActionBar?.title =name
        adapter = messageAdapter(this, messageList)

        binding.recyclerview.layoutManager = LinearLayoutManager(this@ChatActivity)
        binding.recyclerview.adapter= adapter
    }
}