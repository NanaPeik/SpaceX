package ge.sweeft.spacex.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.sweeft.spacex.api.ShipRepository
import ge.sweeft.spacex.data.MissionDetails
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject internal constructor(var repository: ShipRepository) : ViewModel() {

    var missions = MutableLiveData<MissionDetails>()

    var response:LiveData<List<MissionDetails>?> = missions.switchMap {
        liveData(Dispatchers.IO) {
            val res = repository.getMissions()
            emit(res)
        }
    }
}