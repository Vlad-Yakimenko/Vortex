package com.e.vortex.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.e.vortex.data.database.VortexDao

class ViewModelFactory(private val dao: VortexDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ViewModel(vortexDao = dao) as T
}