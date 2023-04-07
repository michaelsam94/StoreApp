package com.example.storeapp.di

import android.util.Log.VERBOSE
import androidx.room.Room
import com.example.storeapp.BuildConfig
import com.example.storeapp.data.ProductsRepository
import com.example.storeapp.data.local.OfflineDataSource
import com.example.storeapp.data.local.ProductDatabase
import com.example.storeapp.data.local.RoomOfflineDataSource
import com.example.storeapp.data.remote.*
import com.example.storeapp.ui.ProductsViewModel
import com.example.storeapp.utils.NetworkAwareHandler
import com.example.storeapp.utils.NetworkHandler
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val roomModule = module {
    single {
        Room.databaseBuilder(get(), ProductDatabase::class.java, "PRODUCTS_DB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<ProductDatabase>().getProductDao() }
}

val networkModule = module {
    single {
        Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .url(
                    chain.request()
                        .url
                        .newBuilder()
                        .build()
                )
                .build()
            return@Interceptor chain.proceed(request)   //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
        }
    }

    single {
        LoggingInterceptor.Builder()
            .setLevel(Level.BODY)
            .log(VERBOSE)
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .addInterceptor(get<LoggingInterceptor>())
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    single {
        GsonConverterFactory.create()
    }

    single {
        CoroutineCallAdapterFactory()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<CoroutineCallAdapterFactory>())
            .client(get<OkHttpClient>())
            .build()
    }
}

val apiServiceModule = module {
    factory {
        get<Retrofit>().create(ApiService::class.java)
    }
}

val repoModule = module {
    single { ProductsRepository(get() , get() , get() ) }

    factory  <OfflineDataSource>{ RoomOfflineDataSource(get()) }

    factory <OnlineDataSource> { RetrofitOnlineDataSource(get())  }

    single <NetworkAwareHandler> { NetworkHandler(get())  }

    factory <ApiHelper> { ApiHelperImpl(get())  }
}

val viewModelModule = module {
    viewModel { ProductsViewModel(get()) }
}