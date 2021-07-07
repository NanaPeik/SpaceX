package ge.sweeft.spacex.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    const val baseUrl = "https://api.spacexdata.com/v3/"
    private val  okHttp= OkHttpClient.Builder()


    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
        .build()


    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}