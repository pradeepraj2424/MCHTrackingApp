package com.drpradeepepi.mchtrackingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drpradeepepi.mchtrackingapp.data.repository.PHCRepository
import com.drpradeepepi.mchtrackingapp.viewmodel.PHCViewModel

class PHCViewModelFactory(private val repository: PHCRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PHCViewModel::class.java)) {
            return PHCViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}