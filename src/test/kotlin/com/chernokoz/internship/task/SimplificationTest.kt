package com.chernokoz.internship.task

import com.chernokoz.internship.task.parser.ExpressionParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SimplificationTest {

    @Test
    fun simplifyElementTest() {
        val expression = ExpressionParser().parse("element")
        assertEquals("element", expression.simplify().toString())
    }

    @Test
    fun simplifyConstantTest() {
        val expression = ExpressionParser().parse("-14")
        assertEquals("-14", expression.simplify().toString())
    }

    @Test
    fun simplifyComparingTest() {
        val parser = ExpressionParser()
        var expression = parser.parse("(5>7)")
        assertEquals("(1=0)", expression.simplify().toString())

        expression = parser.parse("(5<7)")
        assertEquals("(1=1)", expression.simplify().toString())

        expression = parser.parse("(8=8)")
        assertEquals("(1=1)", expression.simplify().toString())

        expression = parser.parse("(8=9)")
        assertEquals("(1=0)", expression.simplify().toString())
    }

    @Test
    fun simplifyWithTrueTest() {
        val expression = ExpressionParser().parse("((1=1)&(element>7))")
        assertEquals("(element>7)", expression.simplify().toString())
    }

    @Test
    fun simplifyWithFalseTest() {
        val expression = ExpressionParser().parse("((1=0)|(element>7))")
        assertEquals("(element>7)", expression.simplify().toString())
    }

    @Test
    fun simplifyWithTrueManyStepsTest() {
        val expression = ExpressionParser().parse("(((1=1)&(element>7))&(5=5))")
        assertEquals("(element>7)", expression.simplify().toString())
    }

    @Test
    fun simplifyWithFalseManyStepsTest() {
        var expression = ExpressionParser().parse("(((1=2)|(element>7))&(5>7))")
        assertEquals("(1=0)", expression.simplify().toString())

        expression = ExpressionParser().parse("(((1=2)|(element>7))|(5>7))")
        assertEquals("(element>7)", expression.simplify().toString())
    }

    @Test
    fun simplifyWithFalseAndTrueTest() {
        var expression = ExpressionParser().parse("(((1=2)|((1=0)&(element>7)))&(5>7))")
        assertEquals("(1=0)", expression.simplify().toString())
    }
}