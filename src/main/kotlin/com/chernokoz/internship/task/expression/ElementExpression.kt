package com.chernokoz.internship.task.expression

class ElementExpression: Expression() {
    override fun apply(expression: Expression): Expression {
        return expression
    }

    override fun toString(): String {
        return "element"
    }
}