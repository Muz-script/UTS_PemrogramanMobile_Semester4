package com.example.myapps

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class MainActivity : AppCompatActivity() {
    private lateinit var layoutNama: TextInputLayout
    private lateinit var layoutEmail: TextInputLayout
    private lateinit var layoutPhone: TextInputLayout
    
    private lateinit var editNama: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPhone: EditText
    private lateinit var radioGender: RadioGroup
    private lateinit var spinnerSeminar: AutoCompleteTextView
    private lateinit var checkPersetujuan: CheckBox
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding views
        layoutNama = findViewById(R.id.layoutNama)
        layoutEmail = findViewById(R.id.layoutEmail)
        layoutPhone = findViewById(R.id.layoutPhone)
        
        editNama = findViewById(R.id.editNama)
        editEmail = findViewById(R.id.editEmail)
        editPhone = findViewById(R.id.editPhone)
        radioGender = findViewById(R.id.radioGender)
        spinnerSeminar = findViewById(R.id.spinnerSeminar)
        checkPersetujuan = findViewById(R.id.checkPersetujuan)
        btnSubmit = findViewById(R.id.btnSubmit)

        setupSpinner()
        setupRealTimeValidation()

        btnSubmit.setOnClickListener {
            if (validateAll()) {
                showConfirmationDialog()
            }
        }
    }

    private fun setupSpinner() {
        val listSeminar = arrayOf(
            "Android Development with Kotlin",
            "Web Security Specialist",
            "Data Science for Beginner",
            "UI/UX Design Masterclass",
            "Artificial Intelligence Fundamentals"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listSeminar)
        spinnerSeminar.setAdapter(adapter)
    }

    private fun setupRealTimeValidation() {
        editNama.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { validateNama() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        editEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { validateEmail() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        editPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { validatePhone() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun validateNama(): Boolean {
        val value = editNama.text.toString().trim()
        return if (value.isEmpty()) {
            layoutNama.error = getString(R.string.error_empty)
            false
        } else {
            layoutNama.error = null
            true
        }
    }

    private fun validateEmail(): Boolean {
        val value = editEmail.text.toString().trim()
        return when {
            value.isEmpty() -> {
                layoutEmail.error = getString(R.string.error_empty)
                false
            }
            !value.contains("@") -> {
                layoutEmail.error = getString(R.string.error_email)
                false
            }
            else -> {
                layoutEmail.error = null
                true
            }
        }
    }

    private fun validatePhone(): Boolean {
        val value = editPhone.text.toString().trim()
        return when {
            value.isEmpty() -> {
                layoutPhone.error = getString(R.string.error_empty)
                false
            }
            !value.all { it.isDigit() } -> {
                layoutPhone.error = getString(R.string.error_phone_numeric)
                false
            }
            !value.startsWith("08") -> {
                layoutPhone.error = getString(R.string.error_phone_format)
                false
            }
            value.length < 10 || value.length > 13 -> {
                layoutPhone.error = getString(R.string.error_phone_format)
                false
            }
            else -> {
                layoutPhone.error = null
                true
            }
        }
    }

    private fun validateAll(): Boolean {
        val isNamaValid = validateNama()
        val isEmailValid = validateEmail()
        val isPhoneValid = validatePhone()
        
        val isGenderValid = radioGender.checkedRadioButtonId != -1
        if (!isGenderValid) {
            Toast.makeText(this, getString(R.string.error_gender), Toast.LENGTH_SHORT).show()
        }

        val selectedSeminar = spinnerSeminar.text.toString()
        val isSeminarValid = selectedSeminar.isNotEmpty() && selectedSeminar != getString(R.string.pilih_seminar)
        if (!isSeminarValid) {
            Toast.makeText(this, getString(R.string.error_seminar), Toast.LENGTH_SHORT).show()
        }

        val isChecked = checkPersetujuan.isChecked
        if (!isChecked) {
            Toast.makeText(this, getString(R.string.error_checkbox), Toast.LENGTH_SHORT).show()
        }

        return isNamaValid && isEmailValid && isPhoneValid && isGenderValid && isSeminarValid && isChecked
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.konfirmasi_judul))
            .setMessage(getString(R.string.konfirmasi_pesan))
            .setPositiveButton(getString(R.string.ya)) { _, _ ->
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("NAMA", editNama.text.toString())
                intent.putExtra("EMAIL", editEmail.text.toString())
                intent.putExtra("PHONE", editPhone.text.toString())
                
                val selectedGenderId = radioGender.checkedRadioButtonId
                val gender = findViewById<RadioButton>(selectedGenderId).text.toString()
                intent.putExtra("GENDER", gender)
                
                intent.putExtra("SEMINAR", spinnerSeminar.text.toString())
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.tidak), null)
            .show()
    }
}