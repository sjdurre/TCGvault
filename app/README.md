TCGvault – Trading Card Collection Manager
Overview

TCGvault is an Android mobile application designed to help trading card collectors organize and manage their personal collections. The app allows users to securely register, log in, and maintain a searchable vault of trading cards across multiple TCG categories.

The goal of this project was to design and implement a secure, database-driven mobile application using modern Android development practices.

Technologies Used

Kotlin

Android Studio

Room Database

RecyclerView

Coroutines

SharedPreferences (Session Management)

Features

User Registration

Secure Login & Logout

Password Hashing (SHA-256)

Password Complexity Requirements

Personal Vault (User-specific data)

Wildcard Search Functionality

Add / Edit / Delete Cards

RecyclerView dynamic updates

Session Management

Screenshot blocking on sensitive screens

Session timeout protection

Database Design
Users Table

userId (Primary Key)

username

passwordHash

createdAtEpochMs

Cards Table

cardId (Primary Key)

ownerUserId (Foreign Key)

cardName

tcgType

quantity

Room DAO queries support:

Insert user

Find user by username

Insert card

Get all cards by user

Wildcard search using SQL LIKE

Security Implementation

Password hashing using SHA-256 before storage

Password complexity validation

Session management with auto-logout

Screenshot blocking using FLAG_SECURE

User-specific database queries (data isolation)


Future Improvements

Cloud synchronization

Real-time card pricing API integration

Improved filtering options

Advanced UI polish and animations

Encrypted database storage

Project Summary

TCGvault successfully demonstrates secure authentication, database integration, and user-specific data management within a mobile Android application. The project showcases practical security implementations and structured software design, forming a strong foundation for further expansion into a production-ready collector platform.
