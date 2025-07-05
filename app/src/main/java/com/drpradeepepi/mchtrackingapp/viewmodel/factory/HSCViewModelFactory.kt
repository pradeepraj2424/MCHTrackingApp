package com.drpradeepepi.mchtrackingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drpradeepepi.mchtrackingapp.data.repository.HSCRepository
import com.drpradeepepi.mchtrackingapp.viewmodel.HSCViewModel

class HSCViewModelFactory(private val repository: HSCRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HSCViewModel::class.java)) {
            return HSCViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}