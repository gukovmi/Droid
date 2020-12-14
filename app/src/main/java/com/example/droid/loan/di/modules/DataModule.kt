package com.example.droid.loan.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.room.Room
import com.example.droid.loan.data.converters.DataConverter
import com.example.droid.loan.data.data_sources.*
import com.example.droid.loan.data.db.AppDatabase
import com.example.droid.loan.data.db.LoansDao
import com.example.droid.loan.data.network.LoanApi
import com.example.droid.loan.data.network.ValueStore
import com.example.droid.loan.data.repositories.InfoRepositoryImpl
import com.example.droid.loan.data.repositories.LoanRepositoryImpl
import com.example.droid.loan.domain.repositories.InfoRepository
import com.example.droid.loan.domain.repositories.LoanRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideLoanRepository(
        networkLoanDataSource: NetworkLoanDataSource,
        localLoanDataSource: LocalLoanDataSource,
        dataConverter: DataConverter
    ): LoanRepository =
        LoanRepositoryImpl(
            networkLoanDataSource,
            localLoanDataSource,
            dataConverter
        )

    @Singleton
    @Provides
    fun provideInfoRepository(sharedPreferencesDataSource: SharedPreferencesDataSource): InfoRepository =
        InfoRepositoryImpl(sharedPreferencesDataSource)

    @Singleton
    @Provides
    fun provideSharedPreferencesDataSource(sharedPreferences: SharedPreferences): SharedPreferencesDataSource =
        SharedPreferencesDataSourceImpl(sharedPreferences)

    @Singleton
    @Provides
    fun provideNetworkLoanDataSource(loanApiClient: LoanApi): NetworkLoanDataSource =
        NetworkLoanDataSourceImpl(loanApiClient)

    @Singleton
    @Provides
    fun provideLocalLoanDataSource(loansDao: LoansDao): LocalLoanDataSource =
        LocalLoanDataSourceImpl(loansDao)

    @Singleton
    @Provides
    fun provideLoansDao(context: Context): LoansDao =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "loans"
        ).build().loansDao()

    @Singleton
    @Provides
    fun provideLoanApiClient(): LoanApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(ValueStore.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        return retrofit.create(LoanApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            "com.example.droid.prefs",
            Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideResources(context: Context): Resources =
        context.resources
}