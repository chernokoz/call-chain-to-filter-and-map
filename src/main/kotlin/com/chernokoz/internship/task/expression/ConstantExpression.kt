package com.chernokoz.internship.task.expression

class ConstantExpression(val number: Long): Expression() {
    override fun apply(expression: Expression): Expression {
        return this
    }

    override fun toString(): String {
        return number.toString()
    }
}