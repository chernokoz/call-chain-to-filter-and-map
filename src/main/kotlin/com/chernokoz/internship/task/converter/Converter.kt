package com.chernokoz.internship.task.converter

import com.chernokoz.internship.task.expression.BinaryExpression
import com.chernokoz.internship.task.expression.Expression
import com.chernokoz.internship.task.parser.CallChainParser
import com.chernokoz.internship.task.token.Call
import com.chernokoz.internship.task.token.FilterCall
import com.chernokoz.internship.task.token.MapCall

class Converter {

    fun convert(input: String): String {
        val callChainParser = CallChainParser()
        val callsList = callChainParser.parse(input)
        return convert(callsList).toString()
    }

    fun convert(callChain: List<Call>): ConvertationResult {
        var mapExpression: Expression = Expression.idMapExpression
        var filterExpression: Expression = Expression.trueFilterExpression
        for (call in callChain) {
            when (call) {
                is FilterCall -> {
                    filterExpression = BinaryExpression(
                        filterExpression,
                        '&',
                        call.expression.applyAndSimplify(mapExpression)).simplify()
                }
                is MapCall -> {
                    mapExpression = call.expression.applyAndSimplify(mapExpression)
                }
            }
        }
        return ConvertationResult(filterExpression, mapExpression)
    }
}