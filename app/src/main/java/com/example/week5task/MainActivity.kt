package com.example.week5task

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.roomapp.data.UserDatabase
import com.example.roomapp.fragments.add.AddFragment
import com.example.roomapp.model.User
import com.example.roomapp.repository.UserRepository
import com.example.roomapp.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.fragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_items, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.logout) {
            val builder = android.app.AlertDialog.Builder(this@MainActivity)
            builder.setMessage("Do you want to LogOut?")
            builder.setCancelable(false)
            builder.setPositiveButton("No") { dialog, which -> dialog.cancel() }
            builder.setNegativeButton("yes") {dialog, which -> finishAffinity()}
            val alertDialog = builder.create()
            alertDialog.show()
        }
        return true
    }

    fun listView() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, ListFragment())
            .commit()
    }

    fun getUser(id: Int): User {
        var userDao = UserDatabase.getDatabase(application).userDao()
        val repository = UserRepository(userDao)
        println("getUser:"+id)
        return repository.getUser(Integer.parseInt(id.toString()))
    }

    fun deleteUser(id: String) {
        val userDao = UserDatabase.getDatabase(application).userDao()
        val repository = UserRepository(userDao)
        repository.deleteUser(Integer.parseInt(id))
        return repository.deleteUser(Integer.parseInt(id.toString()))
    }

}