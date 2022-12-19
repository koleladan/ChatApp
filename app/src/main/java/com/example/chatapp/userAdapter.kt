package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Data.User
import com.example.chatapp.databinding.UserLayoutBinding

class userAdapter(val context: Context, val userlist: ArrayList<User>): RecyclerView.Adapter<userAdapter.userViewHolder>() {

    inner class userViewHolder(binding:UserLayoutBinding):RecyclerView.ViewHolder(binding.root){
        val textName = binding.listItem
        val txtName = binding.listItem2

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserLayoutBinding.inflate(inflater, parent, false)
        return userViewHolder(binding)
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val  currentUser = userlist[position]
        holder.textName.text = currentUser.name.toString()
        holder.txtName.text = currentUser.name.toString()
    }

    override fun getItemCount(): Int {
        return userlist.size
    }
}