package com.yunhan.domain.sample.repository

import com.yunhan.domain.sample.model.SampleModel

interface SampleRepository {
    suspend fun sendUp(): SampleModel
}