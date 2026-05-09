package com.example.myapps

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val nama = intent.getStringExtra("NAMA")
        val email = intent.getStringExtra("EMAIL")
        val phone = intent.getStringExtra("PHONE")
        val gender = intent.getStringExtra("GENDER")
        val seminar = intent.getStringExtra("SEMINAR")

        findViewById<TextView>(R.id.resNama).text = "Nama: $nama"
        findViewById<TextView>(R.id.resEmail).text = "Email: $email"
        findViewById<TextView>(R.id.resPhone).text = "Nomor HP: $phone"
        findViewById<TextView>(R.id.resGender).text = "Jenis Kelamin: $gender"
        findViewById<TextView>(R.id.resSeminar).text = "Seminar: $seminar"

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}