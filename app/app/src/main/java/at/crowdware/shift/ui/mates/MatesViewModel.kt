package at.crowdware.shift.ui.mates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MatesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Mates Fragment"
    }
    val text: LiveData<String> = _text
}