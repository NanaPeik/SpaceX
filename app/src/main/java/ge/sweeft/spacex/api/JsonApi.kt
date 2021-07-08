package ge.sweeft.spacex.api


import ge.sweeft.spacex.data.MissionDetails
import ge.sweeft.spacex.data.ShipView
import retrofit2.Response
import retrofit2.http.GET

interface JsonApi {
    @GET("ships")
    suspend fun getAllShips(): Response<List<ShipView>>

    @GET("missions")
    suspend fun getAllMissions():Response<List<MissionDetails>>
}