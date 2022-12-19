package com.example.chatapp.Data

class User {
    var name:Name? = null
    var email: String? = null
    var uid: String? = null

    constructor(){}

    constructor(firstname:String?, lastname:String?, email:String?, uid:String?){
        this.name = name
        this.email = email
        this.uid =uid
    }
}