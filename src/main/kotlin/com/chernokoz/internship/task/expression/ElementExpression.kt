package com.chernokoz.internship.task.expression

class ElementExpression: Expression(true) {
    override val type = ExpressionType.ALGEBRAIC_FUNCTION

    override fun apply(expression: Expression): Expression = expression

    override fun simplify(): Expression = this

    override fun toString(): String {
        return "element"
    }
}