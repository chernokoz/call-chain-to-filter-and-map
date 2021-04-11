package com.chernokoz.internship.task

import com.chernokoz.internship.task.converter.Converter
import com.chernokoz.internship.task.parser.SyntaxErrorException
import com.chernokoz.internship.task.parser.UnexpectedExpressionTypeException

fun main() {
    val inputLine = readLine() ?: return
    try {
        val converter = Converter()
        val result = converter.convert(inputLine)
        println(result)
    } catch (e: SyntaxErrorException) {
        print("SYNTAX ERROR")
    } catch (e: UnexpectedExpressionTypeException) {
        print("TYPE ERROR")
    }
}