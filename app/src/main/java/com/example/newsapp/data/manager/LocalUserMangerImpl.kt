package com.example.newsapp.data.manager

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.newsapp.domain.manager.LocalUserManager
import com.example.newsapp.util.Constants
import com.example.newsapp.util.Constants.APP_ENTRY
import com.example.newsapp.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserMangerImpl(
    private val context : Context
)  : LocalUserManager{
    override suspend fun saveAppEntry() {
        context.dataStore.edit {settings ->
            settings[PreferencesKey.APP_ENTRY_KEY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
       return context.dataStore.data.map { preferences ->
            preferences[PreferencesKey.APP_ENTRY_KEY]?: false
       }
    }
}


private val Context.dataStore by preferencesDataStore(name = USER_SETTINGS)

private object PreferencesKey{
    val APP_ENTRY_KEY =  booleanPreferencesKey(name = APP_ENTRY)
}