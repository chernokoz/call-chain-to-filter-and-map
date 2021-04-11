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
        val falseFilterExpression = BinaryExpression(
            ConstantExpression(1),
            '=',
            ConstantExpression(0))

        fun getLogicConstant(constant: Boolean): BinaryExpression {
            return when (constant) {
                true -> trueFilterExpression
                false -> falseFilterExpression
            }
        }
    }
    abstract val type: ExpressionType

    val isLogical: Boolean
        get() = type == ExpressionType.LOGICAL_CONSTANT || type == ExpressionType.LOGICAL_FUNCTION
    val isAlgebraic:Boolean
        get() = type == ExpressionType.ALGEBRAIC_CONSTANT || type == ExpressionType.ALGEBRAIC_FUNCTION
    val isTrue: Boolean
        get() = this == trueFilterExpression
    val isFalse: Boolean
        get() = this == falseFilterExpression

    abstract fun apply(expression: Expression): Expression

    abstract fun simplify(): Expression
}