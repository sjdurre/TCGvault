package com.example.tcgvault.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tcgvault.R
import com.example.tcgvault.auth.SessionManager
import com.example.tcgvault.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountActivity : AppCompatActivity() {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = SessionManager(this)
        val userId = session.getLoggedInUserId()

        if (userId <= 0) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_account)

        val txtLoggedInAs = findViewById<TextView>(R.id.txtLoggedInAs)
        val btnBackHome = findViewById<Button>(R.id.btnBackHome)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val db = AppDatabase.getInstance(this)

        uiScope.launch {
            val user = withContext(Dispatchers.IO) { db.userDao().findById(userId) }
            txtLoggedInAs.text = if (user != null) {
                getString(R.string.logged_in_as, user.username)
            } else {
                getString(R.string.logged_in_as_unknown)
            }
        }

        btnBackHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        btnLogout.setOnClickListener {
            session.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
}
