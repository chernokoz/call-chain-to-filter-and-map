package com.chernokoz.internship.task.expression

import com.chernokoz.internship.task.parser.UnexpectedExpressionTypeException

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
            else -> throw UnexpectedExpressionTypeException("unexpected types combination in binary expression")
        }
    }

    override fun simplify(): BinaryExpression {
        val firstOperand = firstOperand.simplify()
        val secondOperand = secondOperand.simplify()
        var res = BinaryExpression(firstOperand, operation, secondOperand)
        res = res.simplifyComparing()
        res = res.simplifyWithTrue()
        res = res.simplifyWithFalse()
        return res
    }

    private fun simplifyComparing(): BinaryExpression {
        if (!comparingOperations.contains(operation)
            || firstOperand.type != ExpressionType.ALGEBRAIC_CONSTANT
            || secondOperand.type != ExpressionType.ALGEBRAIC_CONSTANT) return this
        val firstOperand = (firstOperand as ConstantExpression).number
        val secondOperand = (secondOperand as ConstantExpression).number
        when (operation) {
            '>' -> return getLogicConstant(firstOperand > secondOperand)
            '<' -> return getLogicConstant(firstOperand < secondOperand)
            '=' -> return getLogicConstant(firstOperand == secondOperand)
        }
        print(5)
        return this
    }

    private fun simplifyWithTrue(): BinaryExpression {
        if (logicalOperations.contains(operation) && (firstOperand.isTrue || secondOperand.isTrue)) {
            return when {
                operation == '|' -> trueFilterExpression
                firstOperand.isTrue -> secondOperand as BinaryExpression
                else -> firstOperand as BinaryExpression
            }
        }
        return this
    }

    private fun simplifyWithFalse(): BinaryExpression {
        if (logicalOperations.contains(operation) && (firstOperand.isFalse || secondOperand.isFalse)) {
            return when {
                operation == '&' -> falseFilterExpression
                firstOperand.isFalse -> secondOperand as BinaryExpression
                else -> firstOperand as BinaryExpression
            }
        }
        return this
    }

    override fun toString(): String {
        return "($firstOperand$operation$secondOperand)"
    }
}