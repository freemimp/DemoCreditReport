package uk.co.freemimp.democreditreport.koin

import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.freemimp.democreditreport.BuildConfig
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 15L
private const val WRITE_TIMEOUT = 15L
private const val READ_TIMEOUT = 15L
val retrofitModule = module {
    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }
    single { Moshi.Builder().build() }
    single { retrofitHttpClient() }
    single { retrofitBuilder() }
}

private fun Scope.retrofitBuilder(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(get()))
        .client(get())
        .build()
}


private fun Scope.retrofitHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        cache(get())
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.HEADERS
            }
            else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
    }.build()
}