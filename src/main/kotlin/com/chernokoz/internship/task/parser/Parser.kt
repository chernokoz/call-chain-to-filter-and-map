package com.chernokoz.internship.task.parser

import com.chernokoz.internship.task.tokens.Call
import com.chernokoz.internship.task.tokens.FilterCall
import com.chernokoz.internship.task.tokens.MapCall

class Parser {
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

    private fun parseCall(call: String) : Call {
        return when {
            validateFilterCall(call) -> parseFilter(call)
            validateMapCall(call)    -> parseMap(call)
            else                     -> throw CallParseException("Unknown call")
        }
    }

    private fun validateFilterCall(call: String) : Boolean {
        return call.length >= 8
                && call.startsWith("filter")
                && call[6] == '{'
                && call[call.lastIndex] == '}'
    }

    private fun validateMapCall(call: String) : Boolean {
        return call.length >= 5
                && call.startsWith("map")
                && call[3] == '{'
                && call[call.lastIndex] == '}'
    }

    private fun parseFilter(call: String) : FilterCall {
        val filterExpression = call.substring(7, call.lastIndex)
        print(filterExpression)
        return FilterCall()
    }

    private fun parseMap(call: String) : MapCall {
        val mapExpression = call.substring(4, call.lastIndex)
        print(mapExpression)
        return MapCall()
    }
}