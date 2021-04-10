package com.chernokoz.internship.task.token

import com.chernokoz.internship.task.expression.Expression

class FilterCall(val expression: Expression): Call {
    override fun toString(): String {
        return "filter{$expression}"
    }
}