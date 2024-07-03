import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.scareme.data.repository.iTindrRepository
import com.example.scareme.domain.Entities.RequestBodies.TopicsRequest
import com.example.scareme.domain.Entities.RequestBodies.UpdateProfRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class UserInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = iTindrRepository(getApplication<Application>().applicationContext)

    private val _topics = MutableStateFlow<List<TopicsRequest>>(emptyList())
    val topics: StateFlow<List<TopicsRequest>> = _topics

    init {
        fetchTopics()
    }

    private fun fetchTopics() {
        viewModelScope.launch {
            try {
                val repository = iTindrRepository(getApplication<Application>().applicationContext)
                val fetchedTopics = repository.getTopicList()
                _topics.value = fetchedTopics
                Log.d("UserInfoViewModel", "Fetched topics: $fetchedTopics")
            } catch (e: Exception) {
                Log.e("UserInfoViewModel", "Error fetching topics", e)
            }
        }
    }

    fun saveUserProfile(updateProfRequest: UpdateProfRequest, avatar: Bitmap?) {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val updateProfileDeferred = async { repository.updateUserProfile(updateProfRequest) }

                    if (avatar != null) {
                        val updateAvatarDeferred = async { repository.updateAvatar(avatar) }
                        updateProfileDeferred.await()
                        updateAvatarDeferred.await()
                    } else {
                        updateProfileDeferred.await()
                    }
                }
            } catch (e: Exception) {
                Log.e("UserInfoViewModel", "Error saving user profile", e)
            }
        }
    }
}
