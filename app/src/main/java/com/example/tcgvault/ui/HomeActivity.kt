package com.example.tcgvault.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tcgvault.R
import com.example.tcgvault.auth.SessionManager

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SessionManager(this).getLoggedInUserId() <= 0) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.btnAccount).setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }

        findViewById<Button>(R.id.btnVault).setOnClickListener {
            startActivity(Intent(this, VaultActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnPokemon).setOnClickListener { }
        findViewById<ImageButton>(R.id.btnYugioh).setOnClickListener { }
        findViewById<ImageButton>(R.id.btnMagic).setOnClickListener { }
    }
}
