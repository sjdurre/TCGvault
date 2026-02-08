package com.example.tcgvault.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tcgvault.R
import com.example.tcgvault.auth.SessionManager

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If not logged in, go back to Login
        if (SessionManager(this).getLoggedInUserId() <= 0) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.btnAccount).setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }

        // Phase #1: show purpose (buttons exist; actions can be added later)
        findViewById<Button>(R.id.btnPokemon).setOnClickListener { /* later */ }
        findViewById<Button>(R.id.btnYugioh).setOnClickListener { /* later */ }
        findViewById<Button>(R.id.btnMagic).setOnClickListener { /* later */ }
    }
}
