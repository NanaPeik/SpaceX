package ge.sweeft.spacex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ge.sweeft.spacex.data.MissionDetails
import ge.sweeft.spacex.data.RetrofitService
import ge.sweeft.spacex.data.ShipApi
import javax.security.auth.callback.Callback

class MissionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_missions)
    }

//    private fun setMissionsRecycler() {
//        val destinationService = RetrofitService.createService(ShipApi::class.java)
//        val requestCall = destinationService.getAllMissions()
//        requestCall.enqueue(object :Callback<List<MissionDetails>>{
//
//        })
//    }
}