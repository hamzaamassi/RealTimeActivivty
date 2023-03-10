package com.example.realtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.example.realtime.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var userName = ""
    private var userAge = ""
    private var userId = ""
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val database = Firebase.database

        val myRef = database.reference

        binding.btnSaveData.setOnClickListener {
            userName = binding.etUserName.text.toString()
            userAge = binding.etUserAge.text.toString()
            userId = binding.etUserId.text.toString()
            val data = HashMap<String, String>()
            data["name"] = userName
            data["age"] = userAge
            data["id"] = userId
            myRef.child("Person").child("$count").setValue(data)
            Toast.makeText(this, "Added Successfully", Toast.LENGTH_LONG).show()
            count++
        }
        binding.btbGetData.setOnClickListener {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.value
                    binding.tvMultiLine.text = value.toString()

                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                    Log.e("hma","$value")
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Failure", Toast.LENGTH_LONG).show()
                }

            })
        }


    }
}