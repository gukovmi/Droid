package com.example.droid.loan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.droid.loan.data.model.LoanModel

@Database(entities = [(LoanModel::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loansDao(): LoansDao
}