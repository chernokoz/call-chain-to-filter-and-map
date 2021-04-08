package com.chernokoz.internship.task

import com.chernokoz.internship.task.parser.CallParseException
import com.chernokoz.internship.task.parser.Parser
import com.chernokoz.internship.task.tokens.FilterCall
import com.chernokoz.internship.task.tokens.MapCall
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions

class ParserTest {

    @Test
    fun basicTest() {
        val parser = Parser()
        val tokens = parser.parse("filter{(element>10)}%>%filter{(element<20)}")
        assertEquals(2, tokens.size)
        assert(tokens[0] is FilterCall)
        assert(tokens[1] is FilterCall)
    }

    @Test
    fun oneCallTest() {
        val parser = Parser()
        val tokens = parser.parse("filter{(element>10)}")
        assertEquals(1, tokens.size)
        assert(tokens[0] is FilterCall)
    }

    @Test
    fun unknownCallTest() {
        val parser = Parser()
        Assertions.assertThrows(CallParseException::class.java) {
            parser.parse("flatMap{it+5}")
        }
    }

    @Test
    fun emptyStringParsingTest() {
        val parser = Parser()
        Assertions.assertThrows(CallParseException::class.java) {
            parser.parse("")
        }
    }

    @Test
    fun mapFilterMapTest() {
        val parser = Parser()
        val tokens = parser.parse("map{(element+239)}%>%filter{(element>10)}%>%map{(element-239)}")
        assertEquals(3, tokens.size)
        assert(tokens[0] is MapCall)
        assert(tokens[1] is FilterCall)
        assert(tokens[2] is MapCall)
    }

}