package ge.sweeft.spacex.api

import ge.narogava.test.data.ShipView
import retrofit2.Response
import javax.inject.Inject

class ShipDataSource @Inject constructor(var api: ShipApi) {
    suspend fun getAllShip(): Response<List<ShipView>> {
        return api.getAllShips()
    }
}