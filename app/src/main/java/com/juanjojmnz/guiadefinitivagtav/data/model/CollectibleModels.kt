import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class SpaceshipPartItem(
    val id: Int,
    val name: String,
    val description: String,

    @SerialName("mapImageUrl")
    val mapImageUrl: String,

    @SerialName("locationImageUrl")
    val locationImageUrl: String,

    @Transient
    val isFound: Boolean = false
)

data class LetterScrapItem(
    val id: String,
    val name: String,
    val description: String,
    val mapImageUrl: String,
    val locationImageUrl: String,
    var isFound: Boolean = false,
)