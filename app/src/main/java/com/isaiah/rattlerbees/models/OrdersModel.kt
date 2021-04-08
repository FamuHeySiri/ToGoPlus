package com.isaiah.rattlerbees.models

import java.util.*

data class OrdersModel (

    val order_time: Date = Date(0),
    val restaurant_id: String = "",
    val user_id: String = "",
    val user_name: String = "",
    val order_id: String = "",
    val order_status: String = "",
    val order_items:  Array<String> = emptyArray()

)