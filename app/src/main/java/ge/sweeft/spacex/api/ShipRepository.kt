package ge.sweeft.spacex.api

import javax.inject.Inject

class ShipRepository @Inject constructor(var shipDataSource: ShipDataSource) {
    suspend fun getAllShips() =
        shipDataSource.getAllShip().body()

}