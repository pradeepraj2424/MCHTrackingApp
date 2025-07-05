package com.drpradeepepi.mchtrackingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drpradeepepi.mchtrackingapp.data.entity.PHCEntity
import com.drpradeepepi.mchtrackingapp.data.repository.PHCRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PHCViewModel(private val repository: PHCRepository) : ViewModel() {
    private val _phcList = MutableStateFlow<List<PHCEntity>>(emptyList())
    val phcList: StateFlow<List<PHCEntity>> = _phcList.asStateFlow()

    // Add selectedPHC state for edit functionality
    private val _selectedPHC = MutableStateFlow<PHCEntity?>(null)
    val selectedPHC: StateFlow<PHCEntity?> = _selectedPHC.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllPHCs().collect {
                _phcList.value = it
            }
        }
    }

    // Method to add PHC with individual parameters (used in AddPHCScreen)
    fun addPHC(name: String, location: String, contactNumber: String, doctorName: String) {
        viewModelScope.launch {
            val phcEntity = PHCEntity(
                name = name,
                location = location,
                contactNumber = contactNumber,
                doctorName = doctorName
            )
            repository.insertPHC(phcEntity)
        }
    }

    // Method to update PHC with individual parameters (used in EditPHCScreen)
    fun updatePHC(originalName: String, name: String, location: String, contactNumber: String, doctorName: String) {
        viewModelScope.launch {
            // Find the existing PHC entity by original name
            val existingPHC = _phcList.value.find { it.name == originalName }
            existingPHC?.let {
                val updatedPHC = it.copy(
                    name = name,
                    location = location,
                    contactNumber = contactNumber,
                    doctorName = doctorName
                )
                repository.updatePHC(updatedPHC)
            }
        }
    }

    // Method to load PHC by name (used in EditPHCScreen)
    fun loadPHCByName(phcName: String) {
        viewModelScope.launch {
            val phc = _phcList.value.find { it.name == phcName }
            _selectedPHC.value = phc
        }
    }

    // Original methods for direct entity manipulation
    fun insertPHC(phc: PHCEntity) {
        viewModelScope.launch {
            repository.insertPHC(phc)
        }
    }

    fun updatePHC(phc: PHCEntity) {
        viewModelScope.launch {
            repository.updatePHC(phc)
        }
    }

    fun deletePHC(phc: PHCEntity) {
        viewModelScope.launch {
            repository.deletePHC(phc)
        }
    }
}