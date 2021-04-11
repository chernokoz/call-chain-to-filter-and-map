package com.chernokoz.internship.task

import com.chernokoz.internship.task.expression.BinaryExpression
import com.chernokoz.internship.task.expression.ConstantExpression
import com.chernokoz.internship.task.expression.ElementExpression
import com.chernokoz.internship.task.parser.ExpressionParser
import com.chernokoz.internship.task.parser.SyntaxErrorException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ExpressionParserTest {

    @Test
    fun elementTest() {
        val expression = ExpressionParser().parse("element")
        assert(expression is ElementExpression)
    }

    @Test
    fun elementWithAfterSymbolsTest() {
        assertThrows<SyntaxErrorException> {
            ExpressionParser().parse("element-")
        }
        assertThrows<SyntaxErrorException> {
            ExpressionParser().parse("element)")
        }
    }

    @Test
    fun constantTest() {
        val expression = ExpressionParser().parse("-12312")
        assert(expression is ConstantExpression)
        val constantExpression = expression as ConstantExpression
        assertEquals(-12312, constantExpression.number)
    }

    @Test
    fun binaryExpressionSimpleTest() {
        val expression = ExpressionParser().parse("(1+2)")
        assert(expression is BinaryExpression)
        val binaryExpression = expression as BinaryExpression
        assert(binaryExpression.firstOperand is ConstantExpression)
        assertEquals('+', binaryExpression.operation)
        assert(binaryExpression.secondOperand is ConstantExpression)
    }

    @Test
    fun binaryExpressionWithoutBrackets() {
        assertThrows<SyntaxErrorException> {
            ExpressionParser().parse("5+2")
        }
    }

    @Test
    fun binaryExpressionWithElementTest() {
        val expression = ExpressionParser().parse("(1-element)")
        assert(expression is BinaryExpression)
        val binaryExpression = expression as BinaryExpression
        assert(binaryExpression.firstOperand is ConstantExpression)
        assertEquals('-', binaryExpression.operation)
        assert(binaryExpression.secondOperand is ElementExpression)
    }

    @Test
    fun symbolsAfterParsedExpression() {
        assertThrows<SyntaxErrorException> {
            ExpressionParser().parse("(1-element)123")
        }
    }

    @Test
    fun rightNestingBinaryExpressions() {
        val expression = ExpressionParser().parse("(1-(1+(1*(1-element))))")
        assert(expression is BinaryExpression)
        var binaryExpression = expression as BinaryExpression
        assert(binaryExpression.firstOperand is ConstantExpression)
        assertEquals('-', binaryExpression.operation)
        assert(binaryExpression.secondOperand is BinaryExpression)

        binaryExpression = binaryExpression.secondOperand as BinaryExpression
        assert(binaryExpression.firstOperand is ConstantExpression)
        assertEquals('+', binaryExpression.operation)
        assert(binaryExpression.secondOperand is BinaryExpression)

        binaryExpression = binaryExpression.secondOperand as BinaryExpression
        assert(binaryExpression.firstOperand is ConstantExpression)
        assertEquals('*', binaryExpression.operation)
        assert(binaryExpression.secondOperand is BinaryExpression)

        binaryExpression = binaryExpression.secondOperand as BinaryExpression
        assert(binaryExpression.firstOperand is ConstantExpression)
        assertEquals('-', binaryExpression.operation)
        assert(binaryExpression.secondOperand is ElementExpression)
    }

    @Test
    fun leftNestingBinaryExpressions() {
        val expression = ExpressionParser().parse("((((-5-element)-1)+2)*2)")
        assert(expression is BinaryExpression)
        var binaryExpression = expression as BinaryExpression
        assert(binaryExpression.firstOperand is BinaryExpression)
        assertEquals('*', binaryExpression.operation)
        assert(binaryExpression.secondOperand is ConstantExpression)

        binaryExpression = binaryExpression.firstOperand as BinaryExpression
        assert(binaryExpression.firstOperand is BinaryExpression)
        assertEquals('+', binaryExpression.operation)
        assert(binaryExpression.secondOperand is ConstantExpression)

        binaryExpression = binaryExpression.firstOperand as BinaryExpression
        assert(binaryExpression.firstOperand is BinaryExpression)
        assertEquals('-', binaryExpression.operation)
        assert(binaryExpression.secondOperand is ConstantExpression)

        binaryExpression = binaryExpression.firstOperand as BinaryExpression
        assert(binaryExpression.firstOperand is ConstantExpression)
        assertEquals('-', binaryExpression.operation)
        assert(binaryExpression.secondOperand is ElementExpression)
    }
}