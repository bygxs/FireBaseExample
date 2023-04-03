package com.icloud.firebaseexample

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.icloud.firebaseexample.databinding.ActivityMainBinding
import java.util.UUID

class MainActivity : AppCompatActivity() {

    lateinit var binder: ActivityMainBinding
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)


        userDao = UserDao(this)


        binder.btnAdd.setOnClickListener {
            val user = createUser()
            userDao.addUser(user)

        }

        binder.btnView.setOnClickListener {
            userDao.getUsers(this)
        }
        binder.listView.setOnItemLongClickListener { parent, view, position, id ->
            val selectedUser = parent.getItemAtPosition(position) as UserModel
            userDao.deleteUser(selectedUser.id)
            userDao.getUsers(this)
            //showUsers()
            true
        }
    }

    fun createUser(): UserModel? {

        try {
            val name = binder.etName.text.toString()
            val score = binder.etScore.text.toString().toLong()
            val isHuman = binder.swIsHuman.isChecked

            val user = UserModel(UUID.randomUUID().toString(), name, score, isHuman)
            return user
        } catch (e: java.lang.Exception) {
            return null
        }
    }

    fun showUsers(userList: ArrayList<UserModel>) {

        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, userList)
        binder.listView.adapter = adapter
    }
}