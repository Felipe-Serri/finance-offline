package com.felipeserri.financeoffline.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Dashboard : Destination

    @Serializable
    data object Transactions : Destination

    @Serializable
    data class AddEditTransaction(val transactionId: String? = null) : Destination

    @Serializable
    data object Categories : Destination

    @Serializable
    data object Statistics : Destination

    @Serializable
    data object Settings : Destination
}