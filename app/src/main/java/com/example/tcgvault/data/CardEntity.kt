package com.example.tcgvault.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cards",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["ownerUserId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ownerUserId")]
)
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val cardId: Long = 0,
    val ownerUserId: Long,
    val cardName: String,
    val tcgType: String,
    val quantity: Int
)
