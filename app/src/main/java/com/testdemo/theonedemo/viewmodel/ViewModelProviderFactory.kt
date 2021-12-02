package com.demoprac.grubbrrcafe.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelProviderFactory(
    val app: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommanViewModel::class.java)) {
            return CommanViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}