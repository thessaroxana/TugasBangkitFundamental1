package com.dicoding.tugasbangkitfundamental1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.tugasbangkitfundamental1.data.SettingPreference
import kotlinx.coroutines.launch

class SettingViewModel(private val preference: SettingPreference) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return preference.getThemeSetting().asLiveData()
    }
    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preference.saveThemeSetting(isDarkModeActive)
        }
    }
}