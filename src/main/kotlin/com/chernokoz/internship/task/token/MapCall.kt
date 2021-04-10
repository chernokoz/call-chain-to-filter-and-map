package com.chernokoz.internship.task.token

import com.chernokoz.internship.task.expression.Expression

class MapCall(val expression: Expression): Call {
    override fun toString(): String {
        return "map{$expression}"
    }
}