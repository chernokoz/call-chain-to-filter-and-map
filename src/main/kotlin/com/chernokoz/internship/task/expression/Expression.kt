package com.chernokoz.internship.task.expression

abstract class Expression {
    companion object {
        val idMapExpression = ElementExpression()
        val trueFilterExpression = BinaryExpression(
            ConstantExpression(1),
            '=',
            ConstantExpression(1))
    }

    abstract fun apply(expression: Expression): Expression
}