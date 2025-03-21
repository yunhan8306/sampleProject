package com.yunhan.data.sample.repository

import com.yunhan.data.sample.datasource.SampleDataSource
import com.yunhan.data.sample.model.toModel
import com.yunhan.domain.sample.model.SampleModel
import com.yunhan.domain.sample.repository.SampleRepository
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(
    private val sampleDataSource: SampleDataSource
): SampleRepository {
    override suspend fun sendUp(): SampleModel =
        sampleDataSource.sendUp().toModel()
}