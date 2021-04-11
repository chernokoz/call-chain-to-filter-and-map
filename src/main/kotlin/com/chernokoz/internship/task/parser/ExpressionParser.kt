package com.chernokoz.internship.task.parser

import com.chernokoz.internship.task.expression.BinaryExpression
import com.chernokoz.internship.task.expression.ConstantExpression
import com.chernokoz.internship.task.expression.ElementExpression
import com.chernokoz.internship.task.expression.Expression
import java.lang.StringBuilder

class ExpressionParser {

    fun parse(expressionString: String): Expression {
        val context = ExpressionParserContext(expressionString)
        val res = parseExpression(context)
        if (context.notEnded) throw SyntaxErrorException("something was found after the end of expression")
        return res
    }

    private fun parseExpression(context: ExpressionParserContext): Expression {
        val symbol = context.expressionString[context.index]
        return when {
            symbol == '(' -> {
                parseBinaryExpression(context)
            }
            symbol == 'e' -> {
                parseElement(context)
            }
            symbol == '-' || symbol.isDigit() -> {
                parseConstantExpression(context)
            }
            else -> throw SyntaxErrorException("unexpected symbol $symbol")
        }
    }

    private fun parseElement(parserContext: ExpressionParserContext): ElementExpression {
        if (parserContext.expressionString.substring(parserContext.index, parserContext.index + 7) != "element") {
            throw SyntaxErrorException("element expected, but not found")
        }
        parserContext.index += 7
        return ElementExpression()
    }

    private fun parseBinaryExpression(context: ExpressionParserContext): BinaryExpression {
        context.index++
        val firstOperand = parseExpression(context)
        val operation = context.currentChar
        context.index++
        val secondOperand = parseExpression(context)
        if (context.currentChar != ')') throw SyntaxErrorException(") expected for close BinaryExpression")
        context.index++
        return BinaryExpression(firstOperand, operation, secondOperand)
    }

    private fun parseConstantExpression(context: ExpressionParserContext): ConstantExpression {
        val builder = StringBuilder()
        builder.append(context.currentChar)
        context.index++
        while (context.notEnded && context.currentChar.isDigit()) {
            builder.append(context.currentChar)
            context.index++
        }
        val number = builder.toString().toLong()
        return ConstantExpression(number)
    }
}