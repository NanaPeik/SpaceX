package ge.sweeft.spacex.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.narogava.test.data.ShipView
import ge.sweeft.spacex.api.ShipRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ShipViewModel @Inject internal constructor(var repository: ShipRepository) : ViewModel() {

    var ships = MutableLiveData<ShipView>()


    var response:LiveData<List<ShipView>?> = ships.switchMap {
        liveData(Dispatchers.IO) {
            val res = repository.getAllShips()
            emit(res)
        }
    }
}