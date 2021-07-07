package ge.sweeft.spacex.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val baseUrl = "https://api.spacexdata.com/v3/"
    private val  okHttp= OkHttpClient.Builder()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
        .build()


    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}