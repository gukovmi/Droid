package com.example.droid.loan.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.droid.loan.data.model.LoanModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LoansDao {
    @Query("SELECT * FROM loans ORDER BY id DESC")
    fun getLoans(): Single<List<LoanModel>>

    @Query("SELECT * FROM loans WHERE id LIKE :id")
    fun getLoanById(id: Long): Single<LoanModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLoans(loansList: List<LoanModel>): Completable

    @Query("DELETE FROM loans")
    fun clearLoans(): Completable
}