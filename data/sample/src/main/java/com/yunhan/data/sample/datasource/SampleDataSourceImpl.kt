package com.yunhan.data.sample.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.yunhan.data.sample.model.SampleEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SampleDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SampleDataSource {

    companion object {
        private val PREFERENCE_SAMPLE_UP = intPreferencesKey("preference_sample_up")
    }

    override suspend fun sendUp(): SampleEntity {
        val currentCnt = dataStore.data.first()[PREFERENCE_SAMPLE_UP] ?: 0
        dataStore.edit { preferences ->
            preferences[PREFERENCE_SAMPLE_UP] = currentCnt + 1 // 값 1 증가
        }
        return SampleEntity(cnt = currentCnt + 1)
    }
}