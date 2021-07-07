package ge.sweeft.spacex.data

data class MissionDetails(
    val description: String,
    val manufacturers: List<String>,
    val mission_id: String,
    val mission_name: String,
    val payload_ids: List<String>,
    val twitter: String,
    val website: String,
    val wikipedia: String
)