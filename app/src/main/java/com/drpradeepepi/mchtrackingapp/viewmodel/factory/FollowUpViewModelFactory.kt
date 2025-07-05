package com.drpradeepepi.mchtrackingapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drpradeepepi.mchtrackingapp.data.repository.FollowUpRepository
import com.drpradeepepi.mchtrackingapp.viewmodel.FollowUpViewModel

class FollowUpViewModelFactory(private val repository: FollowUpRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowUpViewModel::class.java)) {
            return FollowUpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}