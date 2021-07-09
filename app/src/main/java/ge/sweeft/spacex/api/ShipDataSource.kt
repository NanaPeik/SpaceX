package ge.sweeft.spacex.api

import ge.sweeft.spacex.data.MissionDetails
import ge.sweeft.spacex.data.ShipView
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShipDataSource @Inject constructor(var api: JsonApi) {
    suspend fun getAllShip(): Response<List<ShipView>> {
        return api.getAllShips()
    }
    suspend fun getMissions():Response<List<MissionDetails>>{
        return api.getAllMissions()
    }
}