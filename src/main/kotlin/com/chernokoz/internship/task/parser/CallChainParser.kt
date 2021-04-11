package com.chernokoz.internship.task.parser

import com.chernokoz.internship.task.expression.ExpressionType
import com.chernokoz.internship.task.token.Call
import com.chernokoz.internship.task.token.FilterCall
import com.chernokoz.internship.task.token.MapCall

class CallChainParser {
    private val chainPipe = "%>%"

    fun parse(line: String): List<Call> {
        val tokensList = mutableListOf<Call>()
        val callList = line.split(chainPipe)
        callList.forEach {
            val token = parseCall(it)
            tokensList.add(token)
        }
        return tokensList
    }

    private fun parseCall(call: String): Call {
        return when {
            validateFilterCall(call) -> parseFilter(call)
            validateMapCall(call)    -> parseMap(call)
            else                     -> throw SyntaxErrorException("unknown call")
        }
    }

    private fun validateFilterCall(call: String): Boolean {
        return call.length >= 8
                && call.startsWith("filter")
                && call[6] == '{'
                && call[call.lastIndex] == '}'
    }

    private fun validateMapCall(call: String): Boolean {
        return call.length >= 5
                && call.startsWith("map")
                && call[3] == '{'
                && call[call.lastIndex] == '}'
    }

    private fun parseFilter(call: String): FilterCall {
        val filterExpression = call.substring(7, call.lastIndex)
        val expression = ExpressionParser().parse(filterExpression)
        if (!expression.isLogical) throw UnexpectedExpressionTypeException("not logical expression in filter")
        return FilterCall(expression.simplify())
    }

    private fun parseMap(call: String): MapCall {
        val mapExpression = call.substring(4, call.lastIndex)
        val expression = ExpressionParser().parse(mapExpression)
        if (expression.type != ExpressionType.ALGEBRAIC_FUNCTION)
            throw UnexpectedExpressionTypeException("expression in map is not algebraic function")
        return MapCall(expression.simplify())
    }
}