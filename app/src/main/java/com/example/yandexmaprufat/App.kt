package com.example.yandexmaprufat

import android.app.Application
import android.util.Log
import com.example.yandexmaprufat.db.AppDatabase
import com.example.yandexmaprufat.db.AppDatabase2
import com.example.yandexmaprufat.db.MainRepository
import com.example.yandexmaprufat.remote.MyRetrofit
import com.example.yandexmaprufat.remote.RemoteApi
import com.yandex.mapkit.MapKitFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer subescheator")
            .build()
        chain.proceed(newRequest)
    }).build()

    private val retrofit by lazy {
        Retrofit.Builder().client(client).baseUrl("https://simplifiedcoding.net/demos/")
            .addConverterFactory(GsonConverterFactory.create()).build()}

    val mainRepository by lazy{
        MainRepository(retrofit.create(RemoteApi::class.java))
    }.also {  Log.d("TAG", " again!") }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)



//        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "Sample.db")
//            //.createFromAsset("my_bd_rufat2.db")
//            //.fallbackToDestructiveMigration()
//            .allowMainThreadQueries()
//            .build()
//
//        db2 = Room.databaseBuilder(applicationContext, AppDatabase2::class.java, "Sample2.db")
//            .allowMainThreadQueries()
//            .build()

    }

    companion object {
        var db: AppDatabase? = null
        var db2: AppDatabase2? = null
    }
}