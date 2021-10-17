package com.csobrino.pruebatecnica.modules.main.crud

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csobrino.pruebatecnica.data.Planet

class CrudDetailViewModel : ViewModel() {
    var planet = MutableLiveData<Planet>()
    var isEditMode = MutableLiveData(false)

    fun setEditMode() {
        isEditMode.value?.let {
            isEditMode.value = !it
        }
    }
}