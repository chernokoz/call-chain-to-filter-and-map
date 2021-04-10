package com.chernokoz.internship.task.parser

class ExpressionParserContext(val expressionString: String) {
    var index = 0

    val notEnded: Boolean
    get() = index < expressionString.length

    val currentChar: Char
    get() = expressionString[index]
}