package com.yunhan.data.sample.datasource

import com.yunhan.data.sample.model.SampleEntity

interface SampleDataSource {
    suspend fun sendUp(): SampleEntity
}