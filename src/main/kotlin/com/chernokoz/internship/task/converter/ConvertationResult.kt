package com.chernokoz.internship.task.converter

import com.chernokoz.internship.task.expression.Expression
import com.chernokoz.internship.task.token.FilterCall
import com.chernokoz.internship.task.token.MapCall

data class ConvertationResult(val filterCall: FilterCall, val mapCall: MapCall) {
    constructor(filterExpression: Expression, mapExpression: Expression)
            : this(FilterCall(filterExpression), MapCall(mapExpression))

    override fun toString(): String {
        return "${filterCall}}%>%${mapCall}"
    }
}