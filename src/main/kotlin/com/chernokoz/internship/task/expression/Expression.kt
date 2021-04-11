package com.chernokoz.internship.task.expression

abstract class Expression(val containsElement: Boolean) {
    companion object {
        val algebraicOperations = listOf('+', '-', '*')
        val logicalOperations = listOf('&', '|')
        val comparingOperations = listOf('>', '<', '=')

        val idMapExpression = ElementExpression()
        val trueFilterExpression = BinaryExpression(
            ConstantExpression(1),
            '=',
            ConstantExpression(1))
    }
    abstract val type: ExpressionType

    val isLogical: Boolean
        get() {
            return type == ExpressionType.LOGICAL_CONSTANT || type == ExpressionType.LOGICAL_FUNCTION
        }
    val isAlgebraic: Boolean
        get() {
            return type == ExpressionType.ALGEBRAIC_CONSTANT || type == ExpressionType.ALGEBRAIC_FUNCTION
        }

    abstract fun apply(expression: Expression): Expression
}