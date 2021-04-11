package com.chernokoz.internship.task

import com.chernokoz.internship.task.converter.Converter
import com.chernokoz.internship.task.expression.BinaryExpression
import com.chernokoz.internship.task.expression.ConstantExpression
import com.chernokoz.internship.task.expression.ElementExpression
import com.chernokoz.internship.task.token.FilterCall
import com.chernokoz.internship.task.token.MapCall
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConverterTest {

    @Test
    fun basicMapTest() {
        val callChain = listOf(
            MapCall(BinaryExpression(ConstantExpression(5), '+', ElementExpression())),
            MapCall(BinaryExpression(ConstantExpression(7), '+', ElementExpression())),
        )
        val res = Converter().convert(callChain)
        assertEquals("map{(7+(5+element))}", res.mapCall.toString())
        assertEquals("filter{(1=1)}", res.filterCall.toString())
    }

    @Test
    fun basicFilterTest() {
        val callChain = listOf(
            FilterCall(BinaryExpression(ConstantExpression(5), '<', ElementExpression())),
            FilterCall(BinaryExpression(ConstantExpression(7), '>', ElementExpression())),
        )
        val res = Converter().convert(callChain)
        assertEquals("map{element}", res.mapCall.toString())
        assertEquals("filter{((5<element)&(7>element))}", res.filterCall.toString())
    }

    @Test
    fun simpleFilterMapTest() {
        val callChain = listOf(
            FilterCall(BinaryExpression(ConstantExpression(3), '<', ElementExpression())),
            MapCall(BinaryExpression(ConstantExpression(8), '+', ElementExpression())),
        )
        val res = Converter().convert(callChain)
        assertEquals("map{(8+element)}", res.mapCall.toString())
        assertEquals("filter{(3<element)}", res.filterCall.toString())
    }

    @Test
    fun simpleMapFilterTest() {
        val callChain = listOf(
            MapCall(BinaryExpression(ConstantExpression(8), '+', ElementExpression())),
            FilterCall(BinaryExpression(ConstantExpression(3), '<', ElementExpression())),
        )
        val res = Converter().convert(callChain)
        assertEquals("map{(8+element)}", res.mapCall.toString())
        assertEquals("filter{(3<(8+element))}", res.filterCall.toString())
    }

    @Test
    fun mapFilterMapTest() {
        val callChain = listOf(
            MapCall(BinaryExpression(ElementExpression(), '+', ConstantExpression(10))),
            FilterCall(BinaryExpression(ElementExpression(), '>', ConstantExpression(10))),
            MapCall(BinaryExpression(ElementExpression(), '*', ElementExpression())),
        )
        val res = Converter().convert(callChain)
        assertEquals("map{((element+10)*(element+10))}", res.mapCall.toString())
        assertEquals("filter{((element+10)>10)}", res.filterCall.toString())
    }

    @Test
    fun filterMapFilterTest() {
        val callChain = listOf(
            FilterCall(BinaryExpression(ElementExpression(), '>', ConstantExpression(5))),
            MapCall(BinaryExpression(ElementExpression(), '*', ConstantExpression(2))),
            FilterCall(BinaryExpression(ElementExpression(), '<', ConstantExpression(50))),
        )
        val res = Converter().convert(callChain)
        assertEquals("map{(element*2)}", res.mapCall.toString())
        assertEquals("filter{((element>5)&((element*2)<50))}", res.filterCall.toString())
    }
}