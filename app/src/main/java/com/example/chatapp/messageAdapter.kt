package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Data.message
import com.example.chatapp.databinding.ReceivedBinding
import com.example.chatapp.databinding.SentBinding
import com.google.firebase.auth.FirebaseAuth

class messageAdapter(val context: Context, val messageList: ArrayList<message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_SENT =1
    val ITEM_RECEIVED =2

    class sentViewHolder(binding: SentBinding):RecyclerView.ViewHolder(binding.root){
        val sentmessage = binding.sent

    }
    class receivedViewHolder(binding: ReceivedBinding):RecyclerView.ViewHolder(binding.root){
        val receivedmessage = binding.received
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val inflater = LayoutInflater.from(parent.context)
            val binding =ReceivedBinding.inflate(inflater,parent,false)
            return receivedViewHolder(binding)

        }else{
            val inflater = LayoutInflater.from(parent.context)
            val binding = SentBinding.inflate(inflater,parent,false)
            return sentViewHolder(binding)

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmessage = messageList[position]
        if (holder.javaClass==sentViewHolder::class.java){

            val viewHolder = holder as sentViewHolder
            holder.sentmessage.text = currentmessage.message
        }else{
            val  viewHolder = holder as receivedViewHolder
            holder.receivedmessage.text = currentmessage.message
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentmessage =messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderid)){
            return ITEM_SENT

        }else{

        }
        return ITEM_RECEIVED
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}