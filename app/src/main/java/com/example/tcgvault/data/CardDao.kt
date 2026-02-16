package com.example.tcgvault.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardDao {

    @Insert
    suspend fun insertCard(card: CardEntity): Long

    // Wildcard search: partial name match for the logged-in user
    @Query("""
        SELECT * FROM cards
        WHERE ownerUserId = :ownerUserId
          AND cardName LIKE '%' || :searchTerm || '%'
        ORDER BY cardName ASC
    """)
    suspend fun searchCards(ownerUserId: Long, searchTerm: String): List<CardEntity>

    // Show all cards for a user (when search is empty)
    @Query("""
        SELECT * FROM cards
        WHERE ownerUserId = :ownerUserId
        ORDER BY cardName ASC
    """)
    suspend fun getAllCards(ownerUserId: Long): List<CardEntity>
}
