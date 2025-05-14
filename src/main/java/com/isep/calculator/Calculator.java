package com.isep.calculator;

public class Calculator {
    private String expr;
    private int index;

    public double evaluateMathExpression(String expression) {
        this.expr = expression.replaceAll("\\s+", "");
        this.index = 0;
        return parseExpression();
    }

    private double parseExpression() {
        double current = 0;
        double temp = 0;
        char lastOp = '+';

        while (index < expr.length()) {
            char c = expr.charAt(index);
            double num;

            if (c == '(') {
                index++; // sauter '('
                num = parseExpression(); // appel récursif
                index++; // sauter ')'
            } else {
                boolean negative = false;

                if (c == '-' && (index == 0 || "+-*(".indexOf(expr.charAt(index - 1)) != -1)) {
                    negative = true;
                    index++;
                }

                int start = index;
                while (index < expr.length() && (Character.isDigit(expr.charAt(index)) || expr.charAt(index) == '.')) {
                    index++;
                }

                if (start == index) break; // sécurité

                num = Double.parseDouble(expr.substring(start, index));
                if (negative) num = -num;
            }

            if (lastOp == '+') {
                current += temp;
                temp = num;
            } else if (lastOp == '-') {
                current += temp;
                temp = -num;
            } else if (lastOp == '*') {
                temp *= num;
            }

            if (index < expr.length()) {
                char nextOp = expr.charAt(index);
                if (nextOp == '+' || nextOp == '-' || nextOp == '*') {
                    lastOp = nextOp;
                    index++;
                } else if (nextOp == ')') {
                    break; // on termine cette sous-expression
                }
            }
        }

        return current + temp;
    }
}
