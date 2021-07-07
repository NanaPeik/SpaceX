package ge.sweeft.spacex.api


import ge.narogava.test.data.ShipView
import retrofit2.Response
import retrofit2.http.GET

interface ShipApi {
    @GET("ships")
    suspend fun getAllShips(): Response<List<ShipView>>

}