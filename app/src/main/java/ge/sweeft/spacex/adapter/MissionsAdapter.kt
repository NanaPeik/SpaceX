package ge.sweeft.spacex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import ge.sweeft.spacex.data.MissionDetails
import ge.sweeft.spacex.databinding.MissionDetailesBinding

class MissionsAdapter(private var missionsList: MutableList<MissionDetails>) :
    RecyclerView.Adapter<MissionsAdapter.ListSelectionViewHolder>() {

    private var missions: ArrayList<MissionDetails> = missionsList as ArrayList<MissionDetails>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val binding =
            MissionDetailesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListSelectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.onBind(missions[position])
    }

    override fun getItemCount(): Int {
        return missions.size
    }

    fun search(newText: String?, linearLayout: LinearLayout) {
        missions = ArrayList()
        for (mission in missionsList) {
            if (newText?.let { mission.mission_name.contains(it) } == true) {
                missions.add(mission)
            }
        }
        if (missions.size == 0) {
            linearLayout.visibility = View.VISIBLE
        }else{
            linearLayout.visibility = View.GONE
        }
        notifyDataSetChanged()
    }

    class ListSelectionViewHolder(private val binding: MissionDetailesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(details: MissionDetails) {
            binding.missionName.text = details.mission_name
            if(details.description.length>50){
                binding.missionDescription.text = details.description.take(47)+"..."
            }else{
                binding.missionDescription.text = details.description
            }
        }
    }
}