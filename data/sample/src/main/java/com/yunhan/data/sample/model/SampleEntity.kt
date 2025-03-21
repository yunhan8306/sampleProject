package com.yunhan.data.sample.model

import com.yunhan.domain.sample.model.SampleModel

data class SampleEntity(
    val cnt: Int
)

fun SampleEntity.toModel() = SampleModel(
    cnt = cnt
)