package com.chernokoz.internship.task.expression

class ConstantExpression(val number: Long): Expression(false) {
    override val type = ExpressionType.ALGEBRAIC_CONSTANT

    override fun apply(expression: Expression): Expression {
        return this
    }

    override fun toString(): String {
        return number.toString()
    }
}