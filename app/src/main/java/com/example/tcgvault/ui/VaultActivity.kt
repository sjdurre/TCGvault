package com.example.tcgvault.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tcgvault.R
import com.example.tcgvault.auth.SessionManager
import com.example.tcgvault.data.AppDatabase
import com.example.tcgvault.data.CardEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VaultActivity : AppCompatActivity() {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    private lateinit var adapter: CardAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var txtEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)

        val userId = SessionManager(this).getLoggedInUserId()
        if (userId <= 0) {
            finish()
            return
        }

        val db = AppDatabase.getInstance(this)
        val cardDao = db.cardDao()

        val txtSearch = findViewById<EditText>(R.id.txtSearch)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnAddSample = findViewById<Button>(R.id.btnAddSample)
        txtEmpty = findViewById(R.id.txtEmpty)

        recycler = findViewById(R.id.recyclerCards)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = CardAdapter(emptyList())
        recycler.adapter = adapter

        fun render(list: List<CardEntity>) {
            adapter.update(list)
            txtEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        // Load all cards initially
        uiScope.launch {
            val all = withContext(Dispatchers.IO) { cardDao.getAllCards(userId) }
            render(all)
        }

        btnSearch.setOnClickListener {
            val term = txtSearch.text.toString().trim()

            uiScope.launch {
                val results = withContext(Dispatchers.IO) {
                    if (term.isEmpty()) cardDao.getAllCards(userId)
                    else cardDao.searchCards(userId, term) // wildcard search
                }
                render(results)
            }
        }

        btnClear.setOnClickListener {
            txtSearch.setText("")
            uiScope.launch {
                val all = withContext(Dispatchers.IO) { cardDao.getAllCards(userId) }
                render(all)
            }
        }

        // Optional: add sample cards so you can demo search even if vault UI isn't done yet
        btnAddSample.setOnClickListener {
            uiScope.launch {
                withContext(Dispatchers.IO) {
                    cardDao.insertCard(CardEntity(ownerUserId = userId, cardName = "Blue-Eyes White Dragon", tcgType = "Yu-Gi-Oh!", quantity = 1))
                    cardDao.insertCard(CardEntity(ownerUserId = userId, cardName = "Charizard", tcgType = "Pokémon", quantity = 1))
                    cardDao.insertCard(CardEntity(ownerUserId = userId, cardName = "Black Lotus", tcgType = "Magic", quantity = 1))
                }
                val all = withContext(Dispatchers.IO) { cardDao.getAllCards(userId) }
                render(all)
            }
        }
    }
}
