package com.example.myapps

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var layoutUsername: TextInputLayout
    private lateinit var layoutPasswordLogin: TextInputLayout
    private lateinit var editUsername: EditText
    private lateinit var editPasswordLogin: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        layoutUsername = findViewById(R.id.layoutUsername)
        layoutPasswordLogin = findViewById(R.id.layoutPasswordLogin)
        editUsername = findViewById(R.id.editUsername)
        editPasswordLogin = findViewById(R.id.editPasswordLogin)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = editUsername.text.toString()
            val password = editPasswordLogin.text.toString()

            if (username == "admin" && password == "admin123") {
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                if (username != "admin") {
                    layoutUsername.error = "Username salah"
                } else {
                    layoutUsername.error = null
                }
                
                if (password != "admin123") {
                    layoutPasswordLogin.error = "Password salah"
                } else {
                    layoutPasswordLogin.error = null
                }
                
                Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}