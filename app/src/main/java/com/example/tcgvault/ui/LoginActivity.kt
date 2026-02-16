package com.example.tcgvault.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tcgvault.R
import com.example.tcgvault.auth.SessionManager
import com.example.tcgvault.data.AppDatabase
import com.example.tcgvault.data.UserEntity
import com.example.tcgvault.util.SecurityUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If already logged in, skip to Home
        val session = SessionManager(this)
        if (session.getLoggedInUserId() > 0) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        val txtUsername = findViewById<EditText>(R.id.txtUsername)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnLogin)
        val txtStatus = findViewById<TextView>(R.id.txtStatus)

        val db = AppDatabase.getInstance(this)
        val userDao = db.userDao()

        btnRegister.setOnClickListener {
            val username = txtUsername.text.toString().trim()
            val password = txtPassword.text.toString()

            // Basic validation
            if (username.length < 3) {
                txtStatus.text = getString(R.string.error_username_short)
                return@setOnClickListener
            }
            if (!SecurityUtils.passwordMeetsRules(password)) {
                txtStatus.text = getString(R.string.error_password_rules)
                return@setOnClickListener
            }

            uiScope.launch {
                val result = withContext(Dispatchers.IO) {
                    try {
                        val newUser = UserEntity(
                            username = username,
                            passwordHash = SecurityUtils.sha256(password),
                            createdAtEpochMs = System.currentTimeMillis()
                        )
                        userDao.insertUser(newUser)
                    } catch (e: Exception) {
                        -1L
                    }
                }

                txtStatus.text = if (result > 0) {
                    getString(R.string.register_success)
                } else {
                    getString(R.string.register_failed)
                }
            }
        }

        btnLogin.setOnClickListener {
            val username = txtUsername.text.toString().trim()
            val password = txtPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                txtStatus.text = getString(R.string.error_enter_credentials)
                return@setOnClickListener
            }

            uiScope.launch {
                val user = withContext(Dispatchers.IO) { userDao.findByUsername(username) }

                if (user == null) {
                    txtStatus.text = getString(R.string.login_user_not_found)
                    return@launch
                }

                val incomingHash = SecurityUtils.sha256(password)
                if (incomingHash == user.passwordHash) {
                    SessionManager(this@LoginActivity).setLoggedInUserId(user.userId)
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } else {
                    txtStatus.text = getString(R.string.login_incorrect_password)
                }
            }
        }
    }
}
