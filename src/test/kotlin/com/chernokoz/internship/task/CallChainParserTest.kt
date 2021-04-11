package com.chernokoz.internship.task

import com.chernokoz.internship.task.parser.CallParseException
import com.chernokoz.internship.task.parser.CallChainParser
import com.chernokoz.internship.task.parser.ExpressionParseException
import com.chernokoz.internship.task.token.FilterCall
import com.chernokoz.internship.task.token.MapCall
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

class CallChainParserTest {

    @Test
    fun basicTest() {
        val parser = CallChainParser()
        val tokens = parser.parse("filter{(element>10)}%>%filter{(element<20)}")
        assertEquals(2, tokens.size)
        assert(tokens[0] is FilterCall)
        assert(tokens[1] is FilterCall)
    }

    @Test
    fun oneCallTest() {
        val parser = CallChainParser()
        val tokens = parser.parse("filter{(element>10)}")
        assertEquals(1, tokens.size)
        assert(tokens[0] is FilterCall)
    }

    @Test
    fun unknownCallTest() {
        val parser = CallChainParser()
        Assertions.assertThrows(CallParseException::class.java) {
            parser.parse("flatMap{it+5}")
        }
    }

    @Test
    fun emptyStringParsingTest() {
        val parser = CallChainParser()
        Assertions.assertThrows(CallParseException::class.java) {
            parser.parse("")
        }
    }

    @Test
    fun mapFilterMapTest() {
        val parser = CallChainParser()
        val tokens = parser.parse("map{(element+239)}%>%filter{(element>10)}%>%map{(element-239)}")
        assertEquals(3, tokens.size)
        assert(tokens[0] is MapCall)
        assert(tokens[1] is FilterCall)
        assert(tokens[2] is MapCall)
    }

    @Test
    fun badMapExpressionTest() {
        val parser = CallChainParser()
        assertThrows<CallParseException> { parser.parse("map{(element>239)}") }
    }
}