package com.example.droid.loan.di.module

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.room.Room
import com.example.droid.loan.data.converter.DataConverter
import com.example.droid.loan.data.datasource.auth.NetworkAuthDataSource
import com.example.droid.loan.data.datasource.auth.NetworkAuthDataSourceImpl
import com.example.droid.loan.data.datasource.info.SharedPreferencesDataSource
import com.example.droid.loan.data.datasource.info.SharedPreferencesDataSourceImpl
import com.example.droid.loan.data.datasource.loan.LocalLoanDataSource
import com.example.droid.loan.data.datasource.loan.LocalLoanDataSourceImpl
import com.example.droid.loan.data.datasource.loan.NetworkLoanDataSource
import com.example.droid.loan.data.datasource.loan.NetworkLoanDataSourceImpl
import com.example.droid.loan.data.db.AppDatabase
import com.example.droid.loan.data.db.LoansDao
import com.example.droid.loan.data.network.ValueStore
import com.example.droid.loan.data.network.api.AuthApi
import com.example.droid.loan.data.network.api.LoanApi
import com.example.droid.loan.data.repository.auth.AuthRepositoryImpl
import com.example.droid.loan.data.repository.info.InfoRepositoryImpl
import com.example.droid.loan.data.repository.loan.LoanRepositoryImpl
import com.example.droid.loan.domain.repository.AuthRepository
import com.example.droid.loan.domain.repository.InfoRepository
import com.example.droid.loan.domain.repository.LoanRepository
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
    fun provideAuthRepository(
        networkAuthDataSource: NetworkAuthDataSource,
        dataConverter: DataConverter
    ): AuthRepository =
        AuthRepositoryImpl(
            networkAuthDataSource,
            dataConverter
        )

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
    fun provideNetworkAuthDataSource(authApiClient: AuthApi): NetworkAuthDataSource =
        NetworkAuthDataSourceImpl(authApiClient)

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
    fun provideAuthApiClient(): AuthApi {
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

        return retrofit.create(AuthApi::class.java)
    }

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