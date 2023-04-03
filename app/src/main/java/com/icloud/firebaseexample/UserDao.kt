package com.icloud.firebaseexample

import android.content.ContentValues.TAG
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.nfc.Tag
import android.util.Log
import com.google.android.material.tabs.TabLayout.TabGravity
import com.google.android.material.tabs.TabLayout.VIEW_LOG_TAG
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class UserDao {

    val ID_KEY = "id"
    val NAME_KEY = "name"
    val SCORE_KEY  = "score"
    val ISHUMAN_KEY = "is_human"

    fun addUser(user:UserModel?){
    if (user == null) return
        val dataToStore = HashMap<String, Object>()

        dataToStore.put(ID_KEY, user.id as Object)
        dataToStore.put(NAME_KEY, user.name as Object)
        dataToStore.put(SCORE_KEY, user.score as Object)
        dataToStore.put(ISHUMAN_KEY, user.isHuman as Object)

       // FirebaseFirestore.getInstance().collection("users").document("users/${user.id}")

        FirebaseFirestore
            .getInstance()
            .document("users/${user.id}")
            .set(dataToStore)
            .addOnSuccessListener { log -> Log.i(TAG, "user added to Firestore with id: $user") }
            .addOnSuccessListener { log -> Log.e(TAG,"failed add user") }
    }

    fun getUsers(activity: MainActivity){
        val userList = ArrayList<UserModel>()

        FirebaseFirestore
            .getInstance()
            .collection("users")
            .get()
            .addOnSuccessListener { users ->
                for (document in users ){
                    val id = document.getString(ID_KEY)
                    val name = document.getString(NAME_KEY)
                    val score = document.getLong(SCORE_KEY)
                    val isHuman = document.getBoolean(ISHUMAN_KEY)

                    val user = UserModel(id, name, score, isHuman)
                    userList.add(user)
                }
                activity.showUsers(userList)
            }
            .addOnFailureListener { log -> Log.e(TAG,"failed FETCH user")}
    }

    fun deleteUser(user: String?) {

        FirebaseFirestore
            .getInstance()
            .document("users/${user}")
            .delete()
            .addOnSuccessListener {
                    log -> Log.i(TAG,"user deleted sucessfully ")

            }
            .addOnFailureListener { log -> Log.e(TAG,"failed FETCH user") }
    }

}