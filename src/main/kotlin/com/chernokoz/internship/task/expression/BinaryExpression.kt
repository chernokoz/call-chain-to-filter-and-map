package com.chernokoz.internship.task.expression

class BinaryExpression(
    val firstOperand: Expression,
    val operation: Char,
    val secondOperand: Expression): Expression() {
    override fun apply(expression: Expression): Expression {
        return BinaryExpression(
            firstOperand.apply(expression),
            operation,
            secondOperand.apply(expression)
        )
    }

    override fun toString(): String {
        return "($firstOperand$operation$secondOperand)"
    }
}