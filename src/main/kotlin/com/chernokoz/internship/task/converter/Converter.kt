package com.chernokoz.internship.task.converter

import com.chernokoz.internship.task.expression.BinaryExpression
import com.chernokoz.internship.task.expression.Expression
import com.chernokoz.internship.task.token.Call
import com.chernokoz.internship.task.token.FilterCall
import com.chernokoz.internship.task.token.MapCall

class Converter {

    fun convert(callChain: List<Call>): ConvertationResult {
        var mapExpression: Expression = Expression.idMapExpression
        var filterExpression: Expression = Expression.trueFilterExpression
        for (call in callChain) {
            when (call) {
                is FilterCall -> {
                    filterExpression = BinaryExpression(
                        filterExpression,
                        '&',
                        call.expression.apply(mapExpression))
                }
                is MapCall -> {
                    println(call.expression)
                    mapExpression = call.expression.apply(mapExpression)
                    println("updated mapExpression: $mapExpression")
                }
            }
        }
        return ConvertationResult(filterExpression, mapExpression)
    }
}