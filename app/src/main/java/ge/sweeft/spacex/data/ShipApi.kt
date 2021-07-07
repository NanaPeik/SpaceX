package ge.sweeft.spacex.data


import ge.narogava.test.data.ShipView
import retrofit2.Call
import retrofit2.http.GET

interface ShipApi {
    @GET("ships")
    fun getAllShips(): Call<List<ShipView>>

    @GET("missions")
    fun getAllMissions(): Call<List<MissionDetails>>
}