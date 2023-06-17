package at.crowdware.shift.logic

import java.util.Stack
import kotlin.math.pow
import java.util.*

private fun performOperation(operand1: Double, operand2: Double, operator: Char): Double {
    return when (operator) {
        '+' -> operand1 + operand2
        '-' -> operand1 - operand2
        '*' -> operand1 * operand2
        '/' -> operand1 / operand2
        '^' -> operand1.pow(operand2)
        else -> throw IllegalArgumentException("Invalid operator: $operator")
    }
}


private fun isNumber(token: String): Boolean {
    return token.toDoubleOrNull() != null
}

private fun isOperator(token: String): Boolean {
    return token.length == 1 && token[0] in "+-*/^"
}

private fun hasHigherPrecedence(op1: Char, op2: Char): Boolean {
    val precedenceMap = mapOf(
        '^' to 3,
        '*' to 2,
        '/' to 2,
        '+' to 1,
        '-' to 1
    )
    return precedenceMap.getOrDefault(op1, 0) >= precedenceMap.getOrDefault(op2, 0)
}

private fun tokenize(expression: String): List<String> {
    val tokens = mutableListOf<String>()
    var currentNumber = StringBuilder()

    for (char in expression) {
        when {
            char.isDigit() || char == '.' -> currentNumber.append(char)
            char.isWhitespace() -> {
                if (currentNumber.isNotEmpty()) {
                    tokens.add(currentNumber.toString())
                    currentNumber.clear()
                }
            }
            else -> {
                if (currentNumber.isNotEmpty()) {
                    tokens.add(currentNumber.toString())
                    currentNumber.clear()
                }
                tokens.add(char.toString())
            }
        }
    }

    if (currentNumber.isNotEmpty()) {
        tokens.add(currentNumber.toString())
    }

    return tokens
}

fun evaluateExpression(expression: String): Long {
    val operatorStack = Stack<Char>()
    val operandStack = Stack<Double>()

    val tokens = tokenize(expression)
    try {
        for (token in tokens) {
            when {
                isNumber(token) -> operandStack.push(token.toDouble())
                isOperator(token) -> {
                    while (operatorStack.isNotEmpty() && hasHigherPrecedence(operatorStack.peek(), token[0])) {
                        val operator = operatorStack.pop()
                        val operand2 = operandStack.pop()
                        val operand1 = operandStack.pop()
                        val result = performOperation(operand1, operand2, operator)
                        operandStack.push(result)
                    }
                    operatorStack.push(token[0])
                }
                token == "(" -> operatorStack.push(token[0])
                token == ")" -> {
                    while (operatorStack.isNotEmpty() && operatorStack.peek() != '(') {
                        val operator = operatorStack.pop()
                        val operand2 = operandStack.pop()
                        val operand1 = operandStack.pop()
                        val result = performOperation(operand1, operand2, operator)
                        operandStack.push(result)
                    }
                    if (operatorStack.isNotEmpty() && operatorStack.peek() == '(') {
                        operatorStack.pop() // Discard the opening parenthesis
                    } else {
                        throw IllegalArgumentException("Mismatched parentheses")
                    }
                }
            }
        }

        while (operatorStack.isNotEmpty()) {
            val operator = operatorStack.pop()
            val operand2 = operandStack.pop()
            val operand1 = operandStack.pop()
            val result = performOperation(operand1, operand2, operator)
            operandStack.push(result)
        }

        return if (operandStack.size == 1) operandStack.pop().toLong() else 0L
    } catch (e: EmptyStackException) {
        return 0L
    } catch(e: IllegalArgumentException) {
        return 0L
    }
}
