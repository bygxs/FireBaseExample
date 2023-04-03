package com.icloud.firebaseexample

data class UserModel (val id:String?, val name: String?, val score: Long?, val isHuman: Boolean?) {

    override fun toString(): String {
        return "UserModel(id='$id', name='$name', score=$score, isHuman=$isHuman)"
    }
}