package com.drpradeepepi.mchtrackingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drpradeepepi.mchtrackingapp.data.entity.HSCEntity
import com.drpradeepepi.mchtrackingapp.data.repository.HSCRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HSCViewModel(private val repository: HSCRepository) : ViewModel() {
    private val _hscList = MutableStateFlow<List<HSCEntity>>(emptyList())
    val hscList: StateFlow<List<HSCEntity>> = _hscList.asStateFlow()

    private val _selectedHSC = MutableStateFlow<HSCEntity?>(null)
    val selectedHSC: StateFlow<HSCEntity?> = _selectedHSC.asStateFlow()

    fun loadHSCsByPHC(phcName: String) {
        viewModelScope.launch {
            repository.getHSCsByPHC(phcName).collect {
                _hscList.value = it
            }
        }
    }

    fun loadHSCByName(hscName: String) {
        viewModelScope.launch {
            _selectedHSC.value = repository.getHSCByName(hscName)
        }
    }

    fun addHSC(
        name: String,
        location: String,
        contactNumber: String,
        anmName: String,
        phcName: String
    ) {
        viewModelScope.launch {
            val hsc = HSCEntity(
                name = name,
                phcName = phcName,
                location = location,
                contactNumber = contactNumber,
                anmName = anmName
            )
            repository.insertHSC(hsc)
        }
    }

    fun updateHSC(
        originalName: String,
        name: String,
        location: String,
        contactNumber: String,
        anmName: String,
        phcName: String
    ) {
        viewModelScope.launch {
            val hsc = HSCEntity(
                name = name,
                phcName = phcName,
                location = location,
                contactNumber = contactNumber,
                anmName = anmName
            )
            repository.updateHSC(hsc)
        }
    }

    fun insertHSC(hsc: HSCEntity) {
        viewModelScope.launch {
            repository.insertHSC(hsc)
        }
    }

    fun updateHSC(hsc: HSCEntity) {
        viewModelScope.launch {
            repository.updateHSC(hsc)
        }
    }

    fun deleteHSC(hsc: HSCEntity) {
        viewModelScope.launch {
            repository.deleteHSC(hsc)
        }
    }
}