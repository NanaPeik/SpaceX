package ge.sweeft.spacex

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ge.sweeft.spacex.adapter.LinksAdapter
import ge.sweeft.spacex.adapter.MissionsAdapter
import ge.sweeft.spacex.data.MissionDetails
import ge.sweeft.spacex.databinding.ActivityMissionsBinding
import ge.sweeft.spacex.databinding.FragmentLinkBinding
import ge.sweeft.spacex.viewmodel.MissionViewModel


@AndroidEntryPoint
class MissionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMissionsBinding
    private val missionViewModel: MissionViewModel by viewModels()
    private lateinit var missionsAdapter: MissionsAdapter
    private var missionLinks = ArrayList<String>()

    private lateinit var linksAdapter: LinksAdapter
    private lateinit var linkBinding: FragmentLinkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        linkBinding = FragmentLinkBinding.inflate(layoutInflater)
        init()
        missionViewModel.response.observe(this, {
            setMissionsAdapter(it as MutableList<MissionDetails>)
        })

        manageSearchView()

        binding.openLinks.setOnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Links")

            builder.setView(R.layout.fragment_link)

            val dialog: AlertDialog = builder.create()
            dialog.show()

            linksAdapter = LinksAdapter(missionLinks)
            linkBinding.linkRecycler.layoutManager =
                LinearLayoutManager(this)
            linkBinding.linkRecycler.adapter = linksAdapter
        }
    }

    private fun manageSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE)
                as SearchManager
        binding.searchMission.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )
        binding.searchMission.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchMission.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                missionsAdapter.search(newText, binding.emptyMissionsPage)
                return true
            }

        })
    }

    private fun init() {
        missionViewModel.missions.value = MissionDetails(
            "", listOf(), "", "", listOf(), "", "", ""
        )
    }

    private fun setMissionsAdapter(missions: MutableList<MissionDetails>) {
        this.missionsAdapter = MissionsAdapter(missions)
        getMissionLinks(missions)

        binding.missionsRecycler.layoutManager =
            LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
        binding.missionsRecycler.adapter = missionsAdapter

    }

    private fun getMissionLinks(missions: MutableList<MissionDetails>) {

        for (mission in missions) {
            missionLinks.add(mission.wikipedia)
        }

    }
}

