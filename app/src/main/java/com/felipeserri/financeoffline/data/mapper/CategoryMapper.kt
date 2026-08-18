package com.felipeserri.financeoffline.data.mapper

import com.felipeserri.financeoffline.data.local.entity.CategoryEntity
import com.felipeserri.financeoffline.domain.model.Category

fun CategoryEntity.toDomain(): Category = Category(
    id = id,
    name = name,
    type = type,
    icon = icon,
    color = color
)

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    type = type,
    icon = icon,
    color = color
)