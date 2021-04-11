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
        val expression = ExpressionParser().parse("((1=1)&(5>7))")
        assertEquals("(1=0)", expression.simplify().toString())
    }
}