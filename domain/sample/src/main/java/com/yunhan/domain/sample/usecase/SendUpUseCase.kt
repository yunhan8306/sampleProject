package com.yunhan.domain.sample.usecase

import com.yunhan.domain.sample.repository.SampleRepository
import javax.inject.Inject

class SendUpUseCase @Inject constructor(
    private val sampleRepository: SampleRepository
) {
    suspend fun invoke() =
        sampleRepository.sendUp()
}