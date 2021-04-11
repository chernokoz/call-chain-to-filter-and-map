package com.chernokoz.internship.task.expression

import com.chernokoz.internship.task.parser.ExpressionParseException

class BinaryExpression(
    val firstOperand: Expression,
    val operation: Char,
    val secondOperand: Expression): Expression(
    firstOperand.containsElement || secondOperand.containsElement) {

    override fun apply(expression: Expression): Expression {
        return BinaryExpression(
            firstOperand.apply(expression),
            operation,
            secondOperand.apply(expression)
        )
    }

    override val type = resolveType()

    private fun resolveType(): ExpressionType {
        return when {
            algebraicOperations.contains(operation)
                    && firstOperand.isAlgebraic
                    && secondOperand.isAlgebraic -> {
                if (containsElement) ExpressionType.ALGEBRAIC_FUNCTION
                    else ExpressionType.ALGEBRAIC_CONSTANT
                    }
            logicalOperations.contains(operation)
                    && firstOperand.isLogical
                    && secondOperand.isLogical -> {
                if (containsElement) ExpressionType.LOGICAL_FUNCTION
                    else ExpressionType.LOGICAL_CONSTANT
            }
            comparingOperations.contains(operation)
                    && firstOperand.isAlgebraic
                    && secondOperand.isAlgebraic -> {
                if (containsElement) ExpressionType.LOGICAL_FUNCTION
                    else ExpressionType.LOGICAL_CONSTANT
            }
            else -> throw ExpressionParseException()
        }
    }

    override fun toString(): String {
        return "($firstOperand$operation$secondOperand)"
    }
}