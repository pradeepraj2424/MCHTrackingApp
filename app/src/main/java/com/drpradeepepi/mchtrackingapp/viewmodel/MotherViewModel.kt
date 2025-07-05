package com.drpradeepepi.mchtrackingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drpradeepepi.mchtrackingapp.data.entity.MotherEntity
import com.drpradeepepi.mchtrackingapp.data.repository.MotherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MotherViewModel(private val repository: MotherRepository) : ViewModel() {
    private val _motherList = MutableStateFlow<List<MotherEntity>>(emptyList())
    val motherList: StateFlow<List<MotherEntity>> = _motherList.asStateFlow()

    private val _selectedMother = MutableStateFlow<MotherEntity?>(null)
    val selectedMother: StateFlow<MotherEntity?> = _selectedMother.asStateFlow()

    fun loadMothersByHSC(hscName: String) {
        viewModelScope.launch {
            repository.getMothersByHSC(hscName).collect {
                _motherList.value = it
            }
        }
    }

    fun loadMotherByName(name: String) {
        viewModelScope.launch {
            _selectedMother.value = repository.getMotherByName(name)
        }
    }

    fun addMother(
        name: String,
        age: Int,
        phone: String,
        address: String,
        husbandName: String,
        lmp: String,
        edd: String,
        gravida: Int,
        para: Int,
        hscName: String
    ) {
        viewModelScope.launch {
            val mother = MotherEntity(
                name = name,
                hscName = hscName,
                age = age,
                phone = phone,
                address = address,
                husbandName = husbandName,
                lmp = lmp,
                edd = edd,
                gravida = gravida,
                para = para
            )
            repository.insertMother(mother)
        }
    }

    fun updateMother(
        originalName: String,
        name: String,
        age: Int,
        phone: String,
        address: String,
        husbandName: String,
        lmp: String,
        edd: String,
        gravida: Int,
        para: Int,
        hscName: String
    ) {
        viewModelScope.launch {
            val updatedMother = MotherEntity(
                name = name,
                hscName = hscName,
                age = age,
                phone = phone,
                address = address,
                husbandName = husbandName,
                lmp = lmp,
                edd = edd,
                gravida = gravida,
                para = para
            )
            repository.updateMother(updatedMother)
        }
    }

    fun deleteMother(mother: MotherEntity) {
        viewModelScope.launch {
            repository.deleteMother(mother)
        }
    }

    // Keep the existing methods for backward compatibility
    fun insertMother(mother: MotherEntity) {
        viewModelScope.launch {
            repository.insertMother(mother)
        }
    }

    fun updateMother(mother: MotherEntity) {
        viewModelScope.launch {
            repository.updateMother(mother)
        }
    }

    suspend fun getMotherByName(name: String): MotherEntity? {
        return repository.getMotherByName(name)
    }
}