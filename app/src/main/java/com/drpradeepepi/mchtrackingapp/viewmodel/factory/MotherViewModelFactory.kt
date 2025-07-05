package com.drpradeepepi.mchtrackingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drpradeepepi.mchtrackingapp.data.repository.MotherRepository
import com.drpradeepepi.mchtrackingapp.viewmodel.MotherViewModel

class MotherViewModelFactory(private val repository: MotherRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MotherViewModel::class.java)) {
            return MotherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}