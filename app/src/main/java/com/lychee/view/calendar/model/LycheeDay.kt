package com.lychee.view.calendar.model

import com.lychee.data.model.Expenditure

data class LycheeDay (
        var day : Int = 0,
        var today : Boolean = false,
        var holiday : Boolean = false,
        var currentMonth : Boolean = true,
        var selected : Boolean = false,
        var expenditure : List<Expenditure>? = null
)