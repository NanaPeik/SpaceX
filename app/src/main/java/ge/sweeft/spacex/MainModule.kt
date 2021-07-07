package ge.sweeft.spacex

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.sweeft.spacex.api.RetrofitService
import ge.sweeft.spacex.api.ShipApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun provideDataSource():ShipApi{
        return Retrofit.Builder()
            .baseUrl(RetrofitService.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create()
    }
}